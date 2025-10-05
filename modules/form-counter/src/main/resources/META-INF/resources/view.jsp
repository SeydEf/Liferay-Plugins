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
                                <label><liferay-ui:message key="start.date"/></label>
                                <liferay-ui:input-date
                                        name="<%= FormCounterPortletKeys.PARAM_START_DATE %>"
                                        dayValue="<%= searchCriteria.getStartDate() != null ? Integer.parseInt(dateFormat.format(searchCriteria.getStartDate()).substring(8, 10)) : 0 %>"
                                        monthValue="<%= searchCriteria.getStartDate() != null ? Integer.parseInt(dateFormat.format(searchCriteria.getStartDate()).substring(5, 7)) - 1 : -1 %>"
                                        yearValue="<%= searchCriteria.getStartDate() != null ? Integer.parseInt(dateFormat.format(searchCriteria.getStartDate()).substring(0, 4)) : 0 %>"
                                        showDisableCheckbox="<%= false %>"
                                        nullable="<%= true %>"
                                />
                            </div>

                            <div class="col-md-2">
                                <label><liferay-ui:message key="end.date"/></label>
                                <liferay-ui:input-date
                                        name="<%= FormCounterPortletKeys.PARAM_END_DATE %>"
                                        dayValue="<%= searchCriteria.getEndDate() != null ? Integer.parseInt(dateFormat.format(searchCriteria.getEndDate()).substring(8, 10)) : 0 %>"
                                        monthValue="<%= searchCriteria.getEndDate() != null ? Integer.parseInt(dateFormat.format(searchCriteria.getEndDate()).substring(5, 7)) - 1 : -1 %>"
                                        yearValue="<%= searchCriteria.getEndDate() != null ? Integer.parseInt(dateFormat.format(searchCriteria.getEndDate()).substring(0, 4)) : 0 %>"
                                        showDisableCheckbox="<%= false %>"
                                        nullable="<%= true %>"
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
