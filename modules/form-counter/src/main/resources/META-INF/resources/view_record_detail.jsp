<%@ page import="com.liferay.dynamic.data.mapping.model.DDMFormInstance" %>
<%@ page import="com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord" %>
<%@ page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %>
<%@ page import="com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil" %>
<%@ page import="com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil" %>
<%@ page import="com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue" %>
<%@ page import="com.liferay.dynamic.data.mapping.storage.DDMFormValues" %>
<%@ page import="com.liferay.portal.kernel.json.JSONArray" %>
<%@ page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.json.JSONObject" %>
<%@ page import="com.liferay.portal.kernel.model.User" %>
<%@ page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="ir.seydef.plugin.formcounter.model.FormSubmissionStatus" %>
<%@ page import="ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil" %>
<%@ page import="ir.seydef.plugin.formcounter.serviceHelper.FormStatusSyncService" %>
<%@ page import="org.osgi.framework.BundleContext" %>
<%@ page import="org.osgi.framework.FrameworkUtil" %>
<%@ page import="org.osgi.framework.ServiceReference" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Objects" %>

<%@ include file="/init.jsp" %>

<%!
    private String getFieldLabel(DDMStructure structure, String fieldName, Locale locale) {
        try {
            String definition = structure.getDefinition();
            if (definition != null) {
                JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(definition);
                JSONArray fields = jsonDefinition.getJSONArray("fields");

                if (fields != null) {
                    for (int i = 0; i < fields.length(); i++) {
                        JSONObject field = fields.getJSONObject(i);
                        String currentFieldName = field.getString("name");

                        if (fieldName.equals(currentFieldName)) {
                            JSONObject label = field.getJSONObject("label");
                            if (label != null) {
                                String localeKey = locale.toString();
                                if (label.has(localeKey)) {
                                    return label.getString(localeKey);
                                } else if (label.has("en_US")) {
                                    return label.getString("en_US");
                                } else {
                                    Iterator<String> keys = label.keys();
                                    if (keys.hasNext()) {
                                        return label.getString(keys.next());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting field label: " + e.getMessage());
        }
        return fieldName;
    }

    private String getDisplayValue(DDMStructure structure, DDMFormFieldValue fieldValue, String rawValue) {
        try {
            String cleanValue = rawValue;
            if (rawValue.startsWith("[\"") && rawValue.endsWith("\"]")) {
                cleanValue = rawValue.substring(2, rawValue.length() - 2);
            } else if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
                cleanValue = rawValue.substring(1, rawValue.length() - 1).replaceAll("\"", "");
            }

            if (cleanValue.contains("Option")) {
                String definition = structure.getDefinition();
                if (definition != null) {
                    JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(definition);
                    JSONArray fields = jsonDefinition.getJSONArray("fields");

                    if (fields != null) {
                        for (int i = 0; i < fields.length(); i++) {
                            JSONObject field = fields.getJSONObject(i);
                            String currentFieldName = field.getString("name");

                            if (fieldValue.getName().equals(currentFieldName)) {
                                JSONArray options = field.getJSONArray("options");
                                if (options != null) {
                                    return findOptionLabel(options, cleanValue);
                                }
                            }
                        }
                    }
                }
            }

            return cleanValue;
        } catch (Exception e) {
            System.err.println("Error getting display value: " + e.getMessage());
            return rawValue;
        }
    }

    private String findOptionLabel(JSONArray options, String optionValue) {
        try {
            for (int i = 0; i < options.length(); i++) {
                JSONObject option = options.getJSONObject(i);
                String value = option.getString("value");

                if (optionValue.contains(value)) {
                    JSONObject label = option.getJSONObject("label");
                    if (label != null) {
                        if (label.has("en_US")) {
                            return label.getString("en_US");
                        } else if (label.has("en")) {
                            return label.getString("en");
                        } else if (label.has("fa_IR")) {
                            return label.getString("fa_IR");
                        } else {
                            Iterator<String> keys = label.keys();
                            if (keys.hasNext()) {
                                return label.getString(keys.next());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error finding option label: " + e.getMessage());
        }
        return optionValue;
    }
%>

<%
    long recordId = ParamUtil.getLong(renderRequest, "recordId");
    String redirect = ParamUtil.getString(renderRequest, "redirect");

    DDMFormInstanceRecord record = null;
    FormSubmissionStatus submissionStatus = null;
    String seenByUserName = null;
    String statusLabel = "unseen";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    errorMessage = null;

    try {
        record = DDMFormInstanceRecordLocalServiceUtil.fetchDDMFormInstanceRecord(recordId);
        if (record == null) {
            errorMessage = "Record not found";
        } else {
            try {
                long userId = themeDisplay.getUserId();

                BundleContext bundleContext = FrameworkUtil.getBundle(FormStatusSyncService.class).getBundleContext();
                ServiceReference<FormStatusSyncService> serviceRef = bundleContext.getServiceReference(FormStatusSyncService.class);

                if (serviceRef != null) {
                    FormStatusSyncService formStatusSyncService = bundleContext.getService(serviceRef);
                    if (formStatusSyncService != null) {
                        formStatusSyncService.markRecordAsSeen(recordId, userId);
                    }
                    bundleContext.ungetService(serviceRef);
                }
            } catch (Exception e) {
                System.err.println("Error marking record as seen: " + e.getMessage());
            }

            try {
                submissionStatus = FormSubmissionStatusLocalServiceUtil.getByFormInstanceRecordId(recordId);
                if (submissionStatus != null && submissionStatus.isSeen()) {
                    statusLabel = "seen";
                    try {
                        long seenByUserId = submissionStatus.getUserId();
                        if (seenByUserId > 0) {
                            User seenByUser = UserLocalServiceUtil.fetchUser(seenByUserId);
                            if (seenByUser != null) {
                                seenByUserName = seenByUser.getFullName();
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error getting seen by user: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.err.println("Error getting submission status: " + e.getMessage());
            }
        }
    } catch (Exception e) {
        errorMessage = "Error loading record";
    }
%>

<div class="ddm-record-detail-view">
    <div class="record-header">
        <h3><liferay-ui:message key="view.record"/> #<%= recordId %>
        </h3>

        <aui:button-row>
            <aui:button href="<%= redirect %>" type="button" value="back"/>
        </aui:button-row>
    </div>

    <c:choose>
        <c:when test="<%= errorMessage != null %>">
            <div class="alert alert-danger">
                <liferay-ui:message key="<%= errorMessage %>"/>
            </div>
        </c:when>

        <c:otherwise>
            <div class="record-details">
                <div class="row">
                    <div class="col-md-6">
                        <div class="detail-group">
                            <label><liferay-ui:message key="record.id"/>:</label>
                            <span class="detail-value"><%= Objects.requireNonNull(record).getFormInstanceRecordId() %></span>
                        </div>

                        <div class="detail-group">
                            <label><liferay-ui:message key="create.date"/>:</label>
                            <span class="detail-value"><%= record.getCreateDate() %></span>
                        </div>

                        <div class="detail-group">
                            <label><liferay-ui:message key="modified.date"/>:</label>
                            <span class="detail-value"><%= record.getModifiedDate() %></span>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="detail-group">
                            <label><liferay-ui:message key="submitted.by"/>:</label>
                            <span class="detail-value"><%= record.getUserName() %></span>
                        </div>

                        <div class="detail-group">
                            <label><liferay-ui:message key="view.status"/>:</label>
                            <span class="detail-value status-badge status-<%= statusLabel %>">
                                <liferay-ui:message key="<%= statusLabel %>"/>
                            </span>
                        </div>

                        <% if (submissionStatus != null && submissionStatus.isSeen()) { %>
                        <div class="detail-group">
                            <label><liferay-ui:message key="seen.date"/>:</label>
                            <span class="detail-value"><%= submissionStatus.getSeenDate() != null ? dateFormat.format(submissionStatus.getSeenDate()) : "N/A" %></span>
                        </div>

                        <% if (Validator.isNotNull(seenByUserName)) { %>
                        <div class="detail-group">
                            <label><liferay-ui:message key="seen.by"/>:</label>
                            <span class="detail-value"><%= seenByUserName %></span>
                        </div>
                        <% } %>
                        <% } %>
                    </div>
                </div>

                <div class="form-data-section">
                    <h4><liferay-ui:message key="form.data"/></h4>
                    <div class="form-data-container">
                        <%
                            try {
                                DDMFormValues formValues = record.getDDMFormValues();
                                if (formValues != null) {
                                    List<DDMFormFieldValue> formFieldValues = formValues.getDDMFormFieldValues();

                                    if (formFieldValues != null && !formFieldValues.isEmpty()) {
                                        DDMFormInstance formInstance = DDMFormInstanceLocalServiceUtil.fetchDDMFormInstance(record.getFormInstanceId());
                                        DDMStructure structure = null;
                                        if (formInstance != null) {
                                            structure = formInstance.getStructure();
                                        }

                                        Locale displayLocale = themeDisplay.getLocale();

                                        for (DDMFormFieldValue fieldValue : formFieldValues) {
                                            String fieldName = fieldValue.getName();
                                            String fieldLabel = fieldName;

                                            if (structure != null) {
                                                fieldLabel = getFieldLabel(structure, fieldName, displayLocale);
                                            }

                                            String value = "";
                                            boolean isMultiline = false;
                                            try {
                                                if (fieldValue.getValue() != null && fieldValue.getValue().getDefaultLocale() != null) {
                                                    String rawValue = GetterUtil.getString(
                                                            fieldValue.getValue().getString(fieldValue.getValue().getDefaultLocale())
                                                    );

                                                    if (structure != null) {
                                                        value = getDisplayValue(structure, fieldValue, rawValue);
                                                    } else {
                                                        value = rawValue;
                                                    }

                                                    isMultiline = value.contains("\n") || value.length() > 100;
                                                }
                                            } catch (Exception e) {
                                                value = "N/A";
                                            }
                        %>
                        <div class="form-field-group">
                            <label class="form-field-label"><%= fieldLabel %>
                            </label>
                            <% if (isMultiline) { %>
                            <textarea
                                    class="form-control form-field-input form-field-textarea"
                                    disabled
                                    readonly
                                    rows="4"><%= Validator.isNotNull(value) ? value : "N/A" %></textarea>
                            <% } else { %>
                            <input
                                    type="text"
                                    class="form-control form-field-input"
                                    value="<%= Validator.isNotNull(value) ? value : "N/A" %>"
                                    disabled
                                    readonly
                            />
                            <% } %>
                        </div>
                        <%
                            }
                        } else {
                        %>
                        <div class="alert alert-info">
                            <liferay-ui:message key="no.form.data.available"/>
                        </div>
                        <%
                            }
                        } else {
                        %>
                        <div class="alert alert-info">
                            <liferay-ui:message key="no.form.data.available"/>
                        </div>
                        <%
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        %>
                        <div class="alert alert-warning">
                            <liferay-ui:message key="error.loading.form.data"/>
                            <br/><small><%= e.getMessage() %>
                        </small>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
