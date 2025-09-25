<%@ include file="/init.jsp" %>

<%
    boolean hasValidBranchId = (Boolean) request.getAttribute("hasValidBranchId");
    String userBranchId = (String) request.getAttribute("userBranchId");
    long selectedFormInstanceId = (Long) request.getAttribute("selectedFormInstanceId");
    List<FormInstanceDisplayDTO> formInstances = (List<FormInstanceDisplayDTO>) request.getAttribute("formInstances");
    List<FormRecordDisplayDTO> formRecords = (List<FormRecordDisplayDTO>) request.getAttribute("formRecords");
    int totalCount = (Integer) request.getAttribute("totalCount");
%>

<portlet:renderURL var="filterURL">
    <portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<div class="ddm-form-records-portlet">
    <div class="portlet-header">
        <h2 class="portlet-title">
            <liferay-ui:message key="ddm.form.records.title" />
        </h2>
        
        <c:if test="<%= hasValidBranchId %>">
            <div class="branch-info">
                <span class="branch-label">
                    <liferay-ui:message key="user.branch.id" />:
                </span>
                <span class="branch-value"><%= userBranchId %></span>
            </div>
        </c:if>
    </div>

    <c:choose>
        <c:when test="<%= errorMessage != null %>">
            <div class="alert alert-danger">
                <liferay-ui:message key="<%= errorMessage %>" />
            </div>
        </c:when>
        
        <c:when test="<%= !hasValidBranchId %>">
            <div class="alert alert-warning">
                <liferay-ui:message key="no.branch.id.assigned" />
            </div>
        </c:when>
        
        <c:otherwise>
            <div class="form-filter-section">
                <aui:form action="<%= filterURL %>" method="get" name="filterForm">
                    <div class="row">
                        <div class="col-md-6">
                            <aui:select name="<%= FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID %>" 
                                       label="select.form.instance" 
                                       onChange="document.getElementById('<portlet:namespace />filterForm').submit();">
                                <aui:option value="0" selected="<%= selectedFormInstanceId == 0 %>">
                                    <liferay-ui:message key="all.forms" />
                                </aui:option>
                                
                                <c:forEach items="<%= formInstances %>" var="formInstance">
                                    <aui:option value="${formInstance.formInstanceId}" 
                                               selected='<%= selectedFormInstanceId == ((FormInstanceDisplayDTO)pageContext.getAttribute("formInstance")).getFormInstanceId() %>'>
                                        ${formInstance.displayName} (${formInstance.recordCount})
                                    </aui:option>
                                </c:forEach>
                            </aui:select>
                        </div>
                        
                        <div class="col-md-6">
                            <div class="form-stats">
                                <span class="stats-label">
                                    <liferay-ui:message key="total.records.found" />:
                                </span>
                                <span class="stats-value"><%= totalCount %></span>
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
                    delta="20"
                    deltaConfigurable="true"
                    emptyResultsMessage="no.form.records.found">

                    <liferay-ui:search-container-results
                        results="<%= formRecords %>"/>

                    <liferay-ui:search-container-row
                        className="ir.seydef.plugin.formcounter.model.FormRecordDisplayDTO"
                        keyProperty="recordId"
                        modelVar="formRecord">

                        <liferay-ui:search-container-column-text
                            name="record.id"
                            property="recordId"
                            orderable="false" />

                        <liferay-ui:search-container-column-text
                            name="form.name"
                            property="formInstanceName"
                            orderable="false" />

                        <liferay-ui:search-container-column-text
                            name="branch.id"
                            property="branchId"
                            orderable="false" />

                        <liferay-ui:search-container-column-text
                            name="submitted.by"
                            property="userName"
                            orderable="false" />

                        <liferay-ui:search-container-column-date
                            name="create.date"
                            property="createDate"
                            orderable="true"
                            orderableProperty="createDate" />

                        <liferay-ui:search-container-column-date
                            name="modified.date"
                            property="modifiedDate"
                            orderable="true"
                            orderableProperty="modifiedDate" />

                        <liferay-ui:search-container-column-text
                            name="status"
                            property="statusLabel"
                            orderable="false" />

                        <liferay-ui:search-container-column-jsp
                            align="right"
                            path="/record_actions.jsp" />

                    </liferay-ui:search-container-row>

                    <liferay-ui:search-iterator
                        displayStyle="list"
                        markupView="lexicon"
                        searchContainer="<%= searchContainer %>" />

                </liferay-ui:search-container>
            </div>

            <c:if test="<%= !formInstances.isEmpty() %>">
                <div class="form-summary-section">
                    <h4><liferay-ui:message key="forms.summary" /></h4>
                    <div class="summary-grid">
                        <c:forEach items="<%= formInstances %>" var="formInstance">
                            <div class="summary-item">
                                <div class="summary-name">${formInstance.displayName}</div>
                                <div class="summary-count">
                                    <liferay-ui:message key="records.count" />: ${formInstance.recordCount}
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const formSelect = document.getElementById('<portlet:namespace /><%= FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID %>');
        if (formSelect) {
            formSelect.addEventListener('change', function() {
                const filterSection = document.querySelector('.form-filter-section');
                if (filterSection) {
                    filterSection.classList.add('loading');
                }

                document.getElementById('<portlet:namespace />filterForm').submit();
            });
        }
    });
</script>