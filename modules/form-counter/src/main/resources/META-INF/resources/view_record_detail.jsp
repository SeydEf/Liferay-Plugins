<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord" %>
<%@ page import="com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil" %>
<%@ page import="java.util.Objects" %>

<%@ include file="/init.jsp" %>

<%
    long recordId = ParamUtil.getLong(renderRequest, "recordId");

    String redirect = ParamUtil.getString(renderRequest, "redirect");
    DDMFormInstanceRecord record = null;
    errorMessage = null;
    try {
        record = DDMFormInstanceRecordLocalServiceUtil.fetchDDMFormInstanceRecord(recordId);
        if
        (record == null) {
            errorMessage = "Record not found";
        }
    } catch (Exception e) {
        errorMessage = "Error loading record";
    }
%>

<div class="ddm-record-detail-view">
    <div class="record-header">
        <h3><liferay-ui:message key="view.record"/> #<%= recordId %></h3>

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
                            <label><liferay-ui:message key="status"/>:</label>
                            <span class="detail-value status-badge"><%= record.getStatus() == 0 ? "Approved" : "Not Approved" %></span>
                        </div>
                    </div>
                </div>

                <!-- Form Data Section -->
                <div class="form-data-section">
                    <h4><liferay-ui:message key="form.data"/></h4>
                    <div class="form-data-container">
                        <% try { // Here you would normally parse and display the form data
                            // This is a simplified version %>
                        <div class="alert alert-info">
                            <liferay-ui:message key="form.data.display.info"/>
                        </div>
                        <% } catch (Exception e) { %>
                        <div class="alert alert-warning">
                            <liferay-ui:message key="error.loading.form.data"/>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<style>
    .ddm-record-detail-view {
        padding: 1.5rem;
    }

    .record-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 2rem;
        padding-bottom: 1rem;
        border-bottom: 2px solid #e9ecef;
    }

    .record-details .detail-group {
        margin-bottom: 1rem;
    }

    .record-details .detail-group label {
        font-weight: 600;
        color: #495057;
        display: block;
        margin-bottom: 0.25rem;
    }

    .record-details .detail-value {
        color: #6c757d;
        padding: 0.5rem;
        background: #f8f9fa;
        border-radius: 4px;
        display: block;
    }

    .form-data-section {
        margin-top: 2rem;
        padding-top: 1.5rem;
        border-top: 1px solid #dee2e6;
    }

    .form-data-section h4 {
        margin-bottom: 1rem;
        color: #495057;
    }

    .status-badge {
        padding: 0.25rem 0.5rem !important;
        border-radius: 4px !important;
        background: #d4edda !important;
        color: #155724 !important;
        display: inline-block !important;
    }
</style>
