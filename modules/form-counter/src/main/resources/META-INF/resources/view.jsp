<%@ page import="ir.seydef.plugin.formcounter.model.SearchCriteria" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="/init.jsp" %>

<%
    boolean hasValidBranchId = (Boolean) request.getAttribute("hasValidBranchId");
    String userBranchId = (String) request.getAttribute("userBranchId");
    long selectedFormInstanceId = (Long) request.getAttribute("selectedFormInstanceId");
    List<FormInstanceDisplayDTO> formInstances = (List<FormInstanceDisplayDTO>) request.getAttribute("formInstances");
    List<FormRecordDisplayDTO> formRecords = (List<FormRecordDisplayDTO>) request.getAttribute("formRecords");
    int totalCount = (Integer) request.getAttribute("totalCount");
    Long unseenCountObj = (Long) request.getAttribute("unseenCount");
    long unseenCount = unseenCountObj != null ? unseenCountObj : 0;
    SearchCriteria searchCriteria = (SearchCriteria) request.getAttribute("searchCriteria");
    if (searchCriteria == null) {
        searchCriteria = new SearchCriteria();
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

%>
<portlet:actionURL
        name="<%= FormCounterPortletKeys.ACTION_SEARCH %>"
        var="searchURL"
>
</portlet:actionURL>

<div class="ddm-form-records-portlet">
    <div class="portlet-header">
        <h2 class="portlet-title">
            <liferay-ui:message key="ddm.form.records.title"/>
        </h2>

        <c:if test="<%= hasValidBranchId %>">
            <div class="branch-info">
                <span class="branch-label">
                  <liferay-ui:message key="branch"/>:
                </span>
                <span class="branch-value"><%= userBranchId %></span>
            </div>
        </c:if>
    </div>

    <c:choose>
        <c:when test="<%= errorMessage != null %>">
            <div class="alert alert-danger">
                <liferay-ui:message key="<%= errorMessage %>"/>
            </div>
        </c:when>

        <c:when test="<%= !hasValidBranchId %>">
            <div class="alert alert-warning">
                <liferay-ui:message key="no.branch.id.assigned"/>
            </div>
        </c:when>

        <c:otherwise>
            <div class="search-form-section">
                <aui:form
                        action="<%= searchURL %>"
                        method="post"
                        name="searchForm"
                >
                    <div class="search-container">
                        <div class="search-header-row">
                            <h4 class="search-title">
                                <liferay-ui:message key="search.form.records"/>
                            </h4>
                            <div class="form-stats-compact">
                        <span class="stats-badge">
                          <liferay-ui:message key="total.records.found"/>:
                          <strong><%= totalCount %></strong>
                        </span>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <aui:select
                                        name="<%= FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID %>"
                                        label="select.form.instance"
                                        value="<%= selectedFormInstanceId %>"
                                        onChange="this.form.submit()"
                                >
                                    <aui:option value="0">
                                        <liferay-ui:message key="all.forms"/>
                                    </aui:option>

                                    <c:forEach
                                            items="<%= formInstances %>"
                                            var="formInstance"
                                    >
                                        <aui:option value="${formInstance.formInstanceId}">
                                            ${formInstance.displayName}
                                            (${formInstance.recordCount})
                                        </aui:option>
                                    </c:forEach>
                                </aui:select>
                            </div>

                            <div class="col-md-6">
                                <aui:select
                                        name="<%= FormCounterPortletKeys.PARAM_STATUS %>"
                                        label="status"
                                        value='<%= searchCriteria.getStatus() != null ? searchCriteria.getStatus() : "all" %>'
                                        onChange="this.form.submit()"
                                >
                                    <aui:option value="all">
                                        <liferay-ui:message key="all.statuses"/>
                                    </aui:option>
                                    <aui:option value="unseen">
                                        <liferay-ui:message key="unseen"/>
                                    </aui:option>
                                    <aui:option value="seen">
                                        <liferay-ui:message key="seen"/>
                                    </aui:option>
                                </aui:select>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-2">
                                <aui:input
                                        name="<%= FormCounterPortletKeys.PARAM_REGISTRANT_NAME %>"
                                        type="text"
                                        label="registrant.name"
                                        value='<%= searchCriteria.getRegistrantName() != null ? searchCriteria.getRegistrantName() : "" %>'
                                        placeholder="search.by.registrant.name"
                                />
                            </div>

                            <div class="col-md-2">
                                <aui:input
                                        name="<%= FormCounterPortletKeys.PARAM_TRACKING_CODE %>"
                                        type="text"
                                        label="tracking.code"
                                        value='<%= searchCriteria.getTrackingCode() != null ? searchCriteria.getTrackingCode() : "" %>'
                                        placeholder="search.by.tracking.code"
                                />
                            </div>

                            <div class="col-md-2">
                                <aui:input
                                        name="<%= FormCounterPortletKeys.PARAM_START_DATE %>"
                                        type="date"
                                        label="start.date"
                                        value='<%= searchCriteria.getStartDate() != null ? dateFormat.format(searchCriteria.getStartDate()) : "" %>'
                                />
                            </div>

                            <div class="col-md-2">
                                <aui:input
                                        name="<%= FormCounterPortletKeys.PARAM_END_DATE %>"
                                        type="date"
                                        label="end.date"
                                        value='<%= searchCriteria.getEndDate() != null ? dateFormat.format(searchCriteria.getEndDate()) : "" %>'
                                />
                            </div>

                            <div class="col-md-4">
                                <div class="search-actions">
                                    <aui:button
                                            type="submit"
                                            value="search"
                                            cssClass="btn btn-primary btn-block"
                                            icon="icon-search"
                                    />
                                    <aui:button
                                            type="button"
                                            value="clear"
                                            cssClass="btn btn-secondary btn-block"
                                            onclick="clearSearchForm()"
                                            icon="icon-remove"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </aui:form>
            </div>

            <div class="form-records-container">
                <liferay-ui:search-container
                        id="formRecordsSearchContainer"
                        total="<%= totalCount %>"
                        var="searchContainer"
                        delta="60"
                        deltaConfigurable="true"
                        emptyResultsMessage="no.form.records.found"
                >
                    <liferay-ui:search-container-results
                            results="<%= formRecords %>"
                    />

                    <liferay-ui:search-container-row
                            className="ir.seydef.plugin.formcounter.model.FormRecordDisplayDTO"
                            keyProperty="recordId"
                            modelVar="formRecord"
                            indexVar="i"
                    >
                        <% row.setCssClass(formRecord.isSeen() ? "record-seen" :
                                "record-unseen"); %>

                        <liferay-ui:search-container-column-text
                                name="row"
                                value="<%= String.valueOf(i + 1) %>"
                                orderable="false"
                        />

                        <liferay-ui:search-container-column-text
                                name="form.name"
                                property="formInstanceName"
                                orderable="false"
                        />

                        <liferay-ui:search-container-column-text
                                name="submitted.by"
                                property="userName"
                                orderable="false"
                        />

                        <liferay-ui:search-container-column-date
                                name="create.date"
                                property="createDate"
                                orderable="true"
                                orderableProperty="createDate"
                        />

                        <liferay-ui:search-container-column-jsp
                                align="right"
                                path="/record_actions.jsp"
                        />
                    </liferay-ui:search-container-row>

                    <liferay-ui:search-iterator
                            displayStyle="list"
                            markupView="lexicon"
                            searchContainer="<%= searchContainer %>"
                    />
                </liferay-ui:search-container>
            </div>

            <c:if test="<%= !formInstances.isEmpty() %>">
                <div class="form-summary-section">
                    <h4><liferay-ui:message key="forms.summary"/></h4>
                    <div class="summary-grid">
                        <c:forEach items="<%= formInstances %>" var="formInstance">
                            <div class="summary-item">
                                <div class="summary-name">
                                        ${formInstance.displayName}
                                </div>
                                <div class="summary-count">
                                    <liferay-ui:message key="records.count"/>:
                                        ${formInstance.recordCount}
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>

<c:if test="<%= unseenCount > 0 %>">
    <div id="unseenNotification" class="unseen-notification">
        <div class="notification-content">
              <span class="notification-text">
                <liferay-ui:message key="you.have"/>
                <span class="notification-count"><%= unseenCount %></span>
                <liferay-ui:message key="unseen.records"/>
              </span>
        </div>
    </div>
</c:if>

<style>
    .search-form-section {
        background-color: #f8f9fa;
        border: 1px solid #dee2e6;
        border-radius: 0.375rem;
        padding: 1.5rem;
        margin-bottom: 1.5rem;
    }

    .search-header-row {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1rem;
        border-bottom: 2px solid #007bff;
        padding-bottom: 0.5rem;
    }

    .search-title {
        color: #495057;
        margin: 0;
    }

    .form-stats-compact {
        display: flex;
        align-items: center;
    }

    .stats-badge {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 0.5rem 1rem;
        border-radius: 20px;
        font-size: 0.9rem;
        box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
        display: inline-flex;
        align-items: center;
        gap: 0.3rem;
        animation: fadeInScale 0.5s ease-out;
    }

    .stats-badge strong {
        font-size: 1.1rem;
        font-weight: 700;
        margin-left: 0.2rem;
    }

    @keyframes fadeInScale {
        from {
            opacity: 0;
            transform: scale(0.8);
        }
        to {
            opacity: 1;
            transform: scale(1);
        }
    }

    .search-container .row {
        margin-bottom: 1rem;
    }

    .search-actions {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 0.5rem;
        align-items: end;
        padding-top: 1.5rem;
    }

    .search-actions .btn {
        width: 100%;
    }

    .form-stats {
        background-color: #e9ecef;
        padding: 0.75rem;
        border-radius: 0.375rem;
        margin-top: 1.5rem;
    }

    .stats-label {
        font-weight: 600;
        color: #495057;
    }

    .stats-value {
        font-size: 1.25rem;
        font-weight: bold;
        color: #007bff;
        margin-left: 0.5rem;
    }

    .branch-info {
        background-color: #d1ecf1;
        border: 1px solid #bee5eb;
        border-radius: 0.375rem;
        padding: 0.5rem 1rem;
        margin-bottom: 1rem;
    }

    .branch-label {
        font-weight: 600;
        color: #0c5460;
    }

    .branch-value {
        font-weight: bold;
        color: #155724;
        margin-left: 0.5rem;
    }

    .record-unseen td {
        font-weight: bold !important;
        color: #000 !important;
    }

    .record-seen td {
        background-color: #ededed !important;
    }

    .unseen-notification {
        position: fixed;
        bottom: 20px;
        right: 20px;
        background-color: #007bff;
        color: white;
        padding: 15px 25px;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
        z-index: 9999;
        display: none;
        animation: slideIn 0.3s ease-out;
    }

    .unseen-notification.show {
        display: block;
    }

    .unseen-notification .notification-content {
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .unseen-notification .notification-icon {
        font-size: 24px;
    }

    .unseen-notification .notification-text {
        font-size: 16px;
        font-weight: 600;
    }

    .unseen-notification .notification-count {
        background-color: #ffc107;
        color: #000;
        padding: 3px 10px;
        border-radius: 12px;
        font-weight: bold;
        margin-left: 5px;
    }

    @keyframes slideIn {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }

    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(400px);
            opacity: 0;
        }
    }

    .unseen-notification.hide {
        animation: slideOut 0.3s ease-out;
    }
</style>

<script>
    function clearSearchForm() {
        document.getElementById(
            "<portlet:namespace /><%= FormCounterPortletKeys.PARAM_REGISTRANT_NAME %>"
        ).value = "";
        document.getElementById(
            "<portlet:namespace /><%= FormCounterPortletKeys.PARAM_TRACKING_CODE %>"
        ).value = "";
        document.getElementById(
            "<portlet:namespace /><%= FormCounterPortletKeys.PARAM_START_DATE %>"
        ).value = "";
        document.getElementById(
            "<portlet:namespace /><%= FormCounterPortletKeys.PARAM_END_DATE %>"
        ).value = "";
        document.getElementById(
            "<portlet:namespace /><%= FormCounterPortletKeys.PARAM_STATUS %>"
        ).value = "all";
        document.getElementById(
            "<portlet:namespace /><%= FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID %>"
        ).value = "0";

        document.getElementById("<portlet:namespace />searchForm").submit();
    }

    document.addEventListener("DOMContentLoaded", function () {
        const searchForm = document.getElementById(
            "<portlet:namespace />searchForm"
        );
        if (searchForm) {
            searchForm.addEventListener("submit", function () {
                const submitButton = searchForm.querySelector(
                    'button[type="submit"]'
                );
                if (submitButton) {
                    submitButton.innerHTML =
                        '<span class="loading-animation"></span> <liferay-ui:message key="searching" />...';
                    submitButton.disabled = true;
                }
            });
        }

        const notification = document.getElementById("unseenNotification");
        if (notification) {
            notification.classList.add("show");

            setTimeout(function () {
                notification.classList.add("hide");
                notification.classList.remove("show");

                setTimeout(function () {
                    notification.style.display = "none";
                }, 300);
            }, 5000);
        }
    });
</script>
