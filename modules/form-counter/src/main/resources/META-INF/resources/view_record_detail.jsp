<%@ page import="com.liferay.dynamic.data.mapping.model.DDMFormInstance" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue" %><%@
page import="com.liferay.dynamic.data.mapping.storage.DDMFormValues" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="ir.seydef.plugin.formcounter.helper.DDMFormService" %><%@
page import="ir.seydef.plugin.formcounter.helper.FormStatusSyncService" %><%@
page import="ir.seydef.plugin.formcounter.model.FormSubmissionStatus" %><%@
page import="ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil" %><%@
page import="ir.seydef.plugin.formcounter.util.DateConverterImpl" %><%@
page import="ir.seydef.plugin.formcounter.util.FormFieldDisplayUtil" %>

<%@ page import="org.osgi.framework.BundleContext" %><%@
page import="org.osgi.framework.FrameworkUtil" %><%@
page import="org.osgi.framework.ServiceReference" %>

<%@ include file="/init.jsp" %>

<%
long recordId = ParamUtil.getLong(renderRequest, "recordId");
String redirect = ParamUtil.getString(renderRequest, "redirect");

DDMFormInstanceRecord record = null;
FormSubmissionStatus submissionStatus = null;
String seenByUserName = null;
String submitterName = null;
String statusLabel = "unseen";
DateConverterImpl dateConverter = new DateConverterImpl();
errorMessage = null;

try {
	record = DDMFormInstanceRecordLocalServiceUtil.fetchDDMFormInstanceRecord(recordId);

	if (record == null) {
		errorMessage = "Record not found";
	}
	else {
		submitterName = DDMFormService.extractSubmitterNameFromRecord(record);

		try {
			long userId = themeDisplay.getUserId();

			BundleContext bundleContext = FrameworkUtil.getBundle(
				FormStatusSyncService.class
			).getBundleContext();

			ServiceReference<FormStatusSyncService> serviceRef = bundleContext.getServiceReference(FormStatusSyncService.class);

			if (serviceRef != null) {
				FormStatusSyncService formStatusSyncService = bundleContext.getService(serviceRef);

				if (formStatusSyncService != null) {
					formStatusSyncService.markRecordAsSeen(recordId, userId);
				}

				bundleContext.ungetService(serviceRef);
			}
		}
		catch (Exception exception) {
			System.err.println("Error marking record as seen: " + exception.getMessage());
		}

		try {
			submissionStatus = FormSubmissionStatusLocalServiceUtil.getByFormInstanceRecordId(recordId);

			if ((submissionStatus != null) && submissionStatus.isSeen()) {
				statusLabel = "seen";

				try {
					long seenByUserId = submissionStatus.getUserId();

					if (seenByUserId > 0) {
						User seenByUser = UserLocalServiceUtil.fetchUser(seenByUserId);

						if (seenByUser != null) {
							seenByUserName = seenByUser.getFullName();
						}
					}
				}
				catch (Exception exception) {
					System.err.println("Error getting seen by user: " + exception.getMessage());
				}
			}
		}
		catch (Exception exception) {
			System.err.println("Error getting submission status: " + exception.getMessage());
		}
	}
}
catch (Exception exception) {
	errorMessage = "Error loading record";
}
%>

<div class="ddm-record-detail-view">
	<div class="portlet-header">
		<h2 class="portlet-title text-center">
			<liferay-ui:message key="view.record" /> #<%= recordId %>
		</h2>
	</div>

	<c:choose>
		<c:when test="<%= errorMessage != null %>">
			<div class="alert alert-danger">
				<liferay-ui:message key="<%= errorMessage %>" />
			</div>
		</c:when>
		<c:otherwise>
			<div class="record-details-section">
				<div class="details-header-row">
					<h4 class="section-title">
						<liferay-ui:message key="record.details" />
					</h4>

					<aui:button-row>
						<aui:button cssClass="btn btn-primary" icon="icon-print" onClick="window.print()" value="print" />
						<aui:button cssClass="btn btn-secondary" href="<%= redirect %>" value="back" />
					</aui:button-row>
				</div>

				<div class="record-info-container">
					<div class="row">
						<div class="col-md-6">
							<div class="detail-group">
								<label class="detail-label"><liferay-ui:message key="submitted.by" />:</label>

								<span class="detail-value"><%= Validator.isNotNull(submitterName) ? submitterName : "-" %></span>
							</div>

							<div class="detail-group">
								<label class="detail-label"><liferay-ui:message key="create.date" />:</label>

								<span class="detail-value"><%= dateConverter.getDateTimeFromGregorianToPersian(record.getCreateDate()) %></span>
							</div>

							<div class="detail-group">
								<label class="detail-label"><liferay-ui:message key="modified.date" />:</label>

								<span class="detail-value"><%= dateConverter.getDateTimeFromGregorianToPersian(record.getModifiedDate()) %></span>
							</div>
						</div>

						<div class="col-md-6">
							<div class="detail-group">
								<label class="detail-label"><liferay-ui:message key="view.status" />:</label>

								<span class="detail-value">
									<span class="status-badge status-<%= statusLabel %>">
										<liferay-ui:message key="<%= statusLabel %>" />
									</span>
								</span>
							</div>

							<%
							if ((submissionStatus != null) && submissionStatus.isSeen()) {
							%>

							<div class="detail-group">
								<label class="detail-label"><liferay-ui:message key="seen.date" />:</label>

								<span class="detail-value"><%= submissionStatus.getSeenDate() != null ? dateConverter.getDateTimeFromGregorianToPersian(submissionStatus.getSeenDate()) : "N/A" %></span>
							</div>

							<%
							if (Validator.isNotNull(seenByUserName)) {
							%>

							<div class="detail-group">
								<label class="detail-label"><liferay-ui:message key="seen.by" />:</label>

								<span class="detail-value"><%= seenByUserName %></span>
							</div>

							<%
								}
							}
							%>

						</div>
					</div>
				</div>

				<div class="form-data-section">
					<h4 class="section-title"><liferay-ui:message key="form.data" /></h4>

					<div class="form-fields-container">

						<%
						try {
							DDMFormValues formValues = record.getDDMFormValues();

							if (formValues != null) {
								List<DDMFormFieldValue> formFieldValues = formValues.getDDMFormFieldValues();

								if ((formFieldValues != null) && !formFieldValues.isEmpty()) {
									DDMFormInstance formInstance = DDMFormInstanceLocalServiceUtil.fetchDDMFormInstance(record.getFormInstanceId());
									DDMStructure structure = null;

									if (formInstance != null) {
										structure = formInstance.getStructure();
									}
						%>

						<%= FormFieldDisplayUtil.renderFormFieldsAsHtml(formFieldValues, structure, 0) %>

						<%
						}
						else {
						%>

						<div class="alert alert-info">
							<liferay-ui:message key="no.form.data.available" />
						</div>

						<%
							}
						}
						else {
						%>

						<div class="alert alert-info">
							<liferay-ui:message key="no.form.data.available" />
						</div>

						<%
							}
						} catch (Exception exception) {
							System.err.println(exception.getMessage());
						%>

						<div class="alert alert-warning">
							<liferay-ui:message key="error.loading.form.data" />

							<br /><small><%= exception.getMessage() %>
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