<%@ page import="ir.seydef.plugin.formcounter.model.SearchCriteria" %>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>

<%@ include file="/init.jsp" %>

<%
boolean hasValidCustomFields = (Boolean)request.getAttribute("hasValidCustomFields");
long selectedFormInstanceId = (Long)request.getAttribute("selectedFormInstanceId");

List<FormInstanceDisplayDTO> formInstances = (List<FormInstanceDisplayDTO>)request.getAttribute("formInstances");
List<FormRecordDisplayDTO> formRecords = (List<FormRecordDisplayDTO>)request.getAttribute("formRecords");
SearchCriteria searchCriteria = (SearchCriteria)request.getAttribute("searchCriteria");

int totalCount = (Integer)request.getAttribute("totalCount");

Long unseenCountObj = (Long)request.getAttribute("unseenCount");

long unseenCount = (unseenCountObj != null) ? unseenCountObj : 0;

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

<portlet:resourceURL id="getFormFields" var="getFormFieldsURL" />

<liferay-ui:error embed="false" key="noRecordsFound" message="no-records-found" />

<div class="ddm-form-records-portlet">
	<div class="portlet-header">
		<h2 class="portlet-title text-center">
			<liferay-ui:message key="ddm.form.records.title" />
		</h2>
	</div>

	<c:choose>
	<c:when test="<%= errorMessage != null %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="<%= errorMessage %>" />
	</div>
	</c:when>
	<c:when test="<%= !hasValidCustomFields %>">
	<div class="alert alert-warning">
		<liferay-ui:message key="no.custom.fields.assigned" />
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
					<liferay-ui:message key="search.form.records" />
				</h4>

				<div class="form-stats-compact">
						<span class="stats-badge">
						<liferay-ui:message key="total.records.found" />:
						<strong><%= totalCount %></strong>
						</span>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
<aui:select
	label="select.form.instance"
	name="<%= FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID %>"
	value="<%= selectedFormInstanceId %>"
	onChange="this.form.submit();"
>
						<aui:option value="0">
							<liferay-ui:message key="all.forms" />
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
	label="status"
	name="<%= FormCounterPortletKeys.PARAM_STATUS %>"
	value='<%= (searchCriteria.getStatus() != null) ? searchCriteria.getStatus() : "all" %>'
	onChange="this.form.submit();"
>
						<aui:option value="all">
							<liferay-ui:message key="all.statuses" />
						</aui:option>

						<aui:option value="unseen">
							<liferay-ui:message key="unseen" />
						</aui:option>

						<aui:option value="seen">
							<liferay-ui:message key="seen" />
						</aui:option>
					</aui:select>
				</div>
			</div>

			<div class="row">
				<div class="col-md-2">
<aui:input
	label="registrant.name"
	name="<%= FormCounterPortletKeys.PARAM_REGISTRANT_NAME %>"
	placeholder="search.by.registrant.name"
	type="text"
	value='<%= (searchCriteria.getRegistrantName() != null) ? searchCriteria.getRegistrantName() : "" %>'
/>
				</div>

				<div class="col-md-2">
<aui:input
	label="tracking.code"
	name="<%= FormCounterPortletKeys.PARAM_TRACKING_CODE %>"
	placeholder="search.by.tracking.code"
	type="text"
	value='<%= (searchCriteria.getTrackingCode() != null) ? searchCriteria.getTrackingCode() : "" %>'
/>
				</div>

				<div class="col-md-2">
					<label><liferay-ui:message key="start.date" /></label>
<liferay-ui:input-date
	dayValue="<%= (searchCriteria.getStartDate() != null) ? Integer.parseInt(dateFormat.format(searchCriteria.getStartDate()).substring(8, 10)) : 0 %>"
	monthValue="<%= (searchCriteria.getStartDate() != null) ? (Integer.parseInt(dateFormat.format(searchCriteria.getStartDate()).substring(5, 7)) - 1) : -1 %>"
	name="<%= FormCounterPortletKeys.PARAM_START_DATE %>"
	nullable="<%= true %>"
	showDisableCheckbox="<%= false %>"
	yearValue="<%= (searchCriteria.getStartDate() != null) ? Integer.parseInt(dateFormat.format(searchCriteria.getStartDate()).substring(0, 4)) : 0 %>"
/>
				</div>

				<div class="col-md-2">
					<label><liferay-ui:message key="end.date" /></label>
<liferay-ui:input-date
	dayValue="<%= (searchCriteria.getEndDate() != null) ? Integer.parseInt(dateFormat.format(searchCriteria.getEndDate()).substring(8, 10)) : 0 %>"
	monthValue="<%= (searchCriteria.getEndDate() != null) ? (Integer.parseInt(dateFormat.format(searchCriteria.getEndDate()).substring(5, 7)) - 1) : -1 %>"
	name="<%= FormCounterPortletKeys.PARAM_END_DATE %>"
	nullable="<%= true %>"
	showDisableCheckbox="<%= false %>"
	yearValue="<%= (searchCriteria.getEndDate() != null) ? Integer.parseInt(dateFormat.format(searchCriteria.getEndDate()).substring(0, 4)) : 0 %>"
/>
				</div>
				<div class="col-md-4">
					<div class="search-actions">
<aui:button
	cssClass="btn btn-block btn-primary"
	icon="icon-search"
	type="submit"
	value="search"
/>

<aui:button
	cssClass="btn btn-block btn-secondary"
	icon="icon-remove"
	onclick="clearSearchForm()"
	value="clear"
/>
					</div>
				</div>
			</div>

			<div class="row dynamic-filters-section" id="<portlet:namespace />dynamicFiltersSection" style="display: none;">
				<div class="col-md-12">
					<h5><liferay-ui:message key="form.field.filters" /></h5>

					<div id="<portlet:namespace />dynamicFiltersContainer">
					</div>

					<button
							type="button"
							class="btn btn-success btn-sm"
							id="<portlet:namespace />addFilterButton"
					>
						<i class="icon-plus"></i>
						<liferay-ui:message key="add.filter" />
					</button>

					<aui:input name="dynamicFilterCount" type="hidden" value="0" />
				</div>
			</div>

			</aui:form>
		</div>

		<div class="form-records-container">
<liferay-ui:search-container
	delta="60"
	deltaConfigurable="true"
	emptyResultsMessage="no.form.records.found"
	id="formRecordsSearchContainer"
	total="<%= totalCount %>"
	var="searchContainer"
>
<liferay-ui:search-container-results
	results="<%= formRecords %>"
/>

<liferay-ui:search-container-row
	className="ir.seydef.plugin.formcounter.model.FormRecordDisplayDTO"
	indexVar="i"
	keyProperty="recordId"
	modelVar="formRecord"
>

					<%
					row.setCssClass(formRecord.isSeen() ? "record-seen" : "record-unseen");

					String currentURL = themeDisplay.getURLCurrent();
					%>

					<portlet:renderURL var="viewRecordDetailURL">
						<portlet:param name="recordId" value="<%= String.valueOf(formRecord.getRecordId()) %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="mvcPath" value="/view_record_detail.jsp" />
					</portlet:renderURL>

<liferay-ui:search-container-column-text
	href="<%= viewRecordDetailURL %>"
	name="row"
	orderable="false"
	value="<%= String.valueOf(i + 1) %>"
/>

<liferay-ui:search-container-column-text
	href="<%= viewRecordDetailURL %>"
	name="form.name"
	orderable="false"
	property="formInstanceName"
/>

					<%
					String submitterName = formRecord.getSubmitterName();

					if (submitterName.isEmpty()) {
						submitterName = " - ";
					}
					%>

<liferay-ui:search-container-column-text
	href="<%= viewRecordDetailURL %>"
	name="submitted.by"
	orderable="false"
	value="<%= submitterName %>"
/>

<liferay-ui:search-container-column-date
	href="<%= viewRecordDetailURL %>"
	name="create.date"
	orderable="true"
	orderableProperty="createDate"
	property="createDate"
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
				<h4><liferay-ui:message key="forms.summary" /></h4>

				<div class="summary-grid">
					<c:forEach items="<%= formInstances %>" var="formInstance">
						<div
							class="summary-item clickable-summary"
							onclick="selectFormInstance('${formInstance.formInstanceId}')"
							data-form-id="${formInstance.formInstanceId}"
						>
							<div class="summary-name">
									${formInstance.displayName}
							</div>

							<div class="summary-count">
								<liferay-ui:message key="records.count" />:
									${formInstance.recordCount}

								<c:if test="${formInstance.unseenCount > 0}">
									<span class="badge badge-warning unseen-badge">
										${formInstance.unseenCount}
										<liferay-ui:message key="unseen" />
									</span>
								</c:if>
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
	<div class="unseen-notification" id="unseenNotification">
		<div class="notification-content">
			<span class="notification-text">
				<liferay-ui:message key="you.have" />

				<span class="notification-count"><%= unseenCount %></span>

				<liferay-ui:message key="unseen.records" />
			</span>
		</div>
	</div>
	</c:if>
</div>

<aui:script>
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

	function selectFormInstance(formInstanceId) {
		var formInstanceSelect = document.getElementById(
			"<portlet:namespace /><%= FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID %>"
		);
		if (formInstanceSelect) {
			formInstanceSelect.value = formInstanceId;
			var event = new Event('change');
			formInstanceSelect.dispatchEvent(event);
		}
	}

	(function() {
		function init() {
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

			var formFieldsCache = {};
			var filterIndex = 0;

			var formInstanceSelect = document.getElementById(
				"<portlet:namespace /><%= FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID %>"
			);

			if (formInstanceSelect) {
				var currentFormInstanceId = formInstanceSelect.value;
				if (currentFormInstanceId && currentFormInstanceId !== "0") {
					var filtersSection = document.getElementById(
						"<portlet:namespace />dynamicFiltersSection"
					);
					if (filtersSection) {
						filtersSection.style.display = "block";
					}
					loadFormFields(currentFormInstanceId);
				}

				formInstanceSelect.addEventListener("change", function() {
					var formInstanceId = this.value;
					var filtersSection = document.getElementById(
						"<portlet:namespace />dynamicFiltersSection"
					);

					if (formInstanceId && formInstanceId !== "0") {
						if (filtersSection) {
							filtersSection.style.display = "block";
						}
						loadFormFields(formInstanceId);
					} else {
						if (filtersSection) {
							filtersSection.style.display = "none";
						}
						clearAllFilters();
					}
				});
			}

			var addFilterButton = document.getElementById(
				"<portlet:namespace />addFilterButton"
			);

			if (addFilterButton) {
				addFilterButton.addEventListener("click", function() {
					console.log("Add filter button clicked");
					var formInstanceId = formInstanceSelect ? formInstanceSelect.value : "0";
					console.log("Form instance ID:", formInstanceId);
					console.log("Form fields cache:", formFieldsCache);
					if (formInstanceId && formInstanceId !== "0") {
						addFilter(formFieldsCache[formInstanceId] || []);
					} else {
						console.log("No form selected");
					}
				});
			} else {
				console.log("Add filter button not found");
			}

			function loadFormFields(formInstanceId) {
				if (formFieldsCache[formInstanceId]) {
					return;
				}

				var resourceURL = '<%= getFormFieldsURL %>' + '&<portlet:namespace />formInstanceId=' + formInstanceId;

				fetch(resourceURL)
					.then(response => response.json())
					.then(data => {
						if (data && !data.error) {
							formFieldsCache[formInstanceId] = data;
							console.log("Form fields loaded:", data);
						}
					})
					.catch(error => {
						console.error('Error loading form fields:', error);
					});
			}

			function addFilter(fields) {
				console.log("addFilter called with fields:", fields);
				var container = document.getElementById(
					"<portlet:namespace />dynamicFiltersContainer"
				);
				console.log("Container element:", container);

				var filterDiv = document.createElement("div");
				filterDiv.className = "row dynamic-filter-row";
				filterDiv.setAttribute("data-filter-index", filterIndex);
				filterDiv.style.marginBottom = "15px";
				filterDiv.style.padding = "10px";
				filterDiv.style.border = "1px solid #ddd";
				filterDiv.style.borderRadius = "4px";
				filterDiv.style.backgroundColor = "#f9f9f9";

				var fieldSelectHtml = '<div class="col-md-4">' +
					'<label><liferay-ui:message key="field" /></label>' +
					'<select class="form-control field-select" name="<portlet:namespace />filterField' + filterIndex + '" id="<portlet:namespace />filterField' + filterIndex + '">';

				if (fields && fields.length > 0) {
					fields.forEach(function(field, index) {
						var selected = (index === 0) ? ' selected' : '';
						fieldSelectHtml += '<option value="' + field.name + '" data-type="' + field.type + '"' + selected + '>' + field.label + '</option>';
					});
				}

				fieldSelectHtml += '</select></div>';

				var valueInputHtml = '<div class="col-md-6">' +
					'<label><liferay-ui:message key="value" /></label>' +
					'<div id="<portlet:namespace />filterValueContainer' + filterIndex + '"></div>' +
					'<input type="hidden" name="<portlet:namespace />filterFieldType' + filterIndex + '" id="<portlet:namespace />filterFieldType' + filterIndex + '" />' +
					'</div>';

				var removeButtonHtml = '<div class="col-md-2">' +
					'<label>&nbsp;</label>' +
					'<button type="button" class="btn btn-danger remove-filter-btn btn-block" data-index="' + filterIndex + '">' +
					'<span>' +
					'<svg class="lexicon-icon lexicon-icon-trash"><use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#trash"></use></svg>' +
					'</span>' +
					'</button></div>';

				filterDiv.innerHTML = fieldSelectHtml + valueInputHtml + removeButtonHtml;

				if (container) {
					container.appendChild(filterDiv);
				} else {
					console.warn("dynamicFiltersContainer not found; cannot append filter.");
				}

				var fieldSelect = filterDiv.querySelector(".field-select");

				if (fields && fields.length > 0) {
					var firstField = fields[0];
					var currentFilterIndex = filterDiv.getAttribute("data-filter-index");
					var hiddenTypeInput = document.getElementById("<portlet:namespace />filterFieldType" + currentFilterIndex);
					if (hiddenTypeInput) {
						hiddenTypeInput.value = firstField.type;
					}
					renderValueInput(currentFilterIndex, firstField.type, firstField.name, fields);
				}

				if (fieldSelect) {
					fieldSelect.addEventListener("change", function() {
						var selectedOption = this.options[this.selectedIndex];
						var fieldType = selectedOption.getAttribute("data-type");
						var fieldName = selectedOption.value;
						var currentFilterIndex = filterDiv.getAttribute("data-filter-index");

						var hiddenTypeInput = document.getElementById("<portlet:namespace />filterFieldType" + currentFilterIndex);
						if (hiddenTypeInput) {
							hiddenTypeInput.value = fieldType;
						}

						renderValueInput(currentFilterIndex, fieldType, fieldName, fields);
					});
				}

				var removeButton = filterDiv.querySelector(".remove-filter-btn");
				if (removeButton && container) {
					removeButton.addEventListener("click", function() {
						container.removeChild(filterDiv);
						updateFilterCount();
					});
				}

				filterIndex++;
				updateFilterCount();
			}

			function renderValueInput(filterIndex, fieldType, fieldName, fields) {
				var container = document.getElementById(
					"<portlet:namespace />filterValueContainer" + filterIndex
				);

				if (!container) return;

				container.innerHTML = "";

				var field = fields.find(function(f) { return f.name === fieldName; });
				var fieldLabel = field ? field.label : fieldName;

				if (fieldType === "text" || fieldType === "rich_text") {
					container.innerHTML = '<input type="text" class="form-control" name="<portlet:namespace />filterValue' + filterIndex + '" placeholder="' + '<%= LanguageUtil.get(request, "search.for") %>' + ' ' + fieldLabel + ' ..." />';
				} else if (fieldType === "numeric") {
					container.innerHTML = '<input type="number" class="form-control" name="<portlet:namespace />filterValue' + filterIndex + '" placeholder="' + '<%= LanguageUtil.get(request, "search.for") %>' + ' ' + fieldLabel + ' ..." />';
				} else if (fieldType === "date") {
					container.innerHTML = '<input type="date" class="form-control" name="<portlet:namespace />filterValue' + filterIndex + '" />';
				} else if (fieldType === "select" || fieldType === "radio") {
					var selectHtml = '<select class="form-control" name="<portlet:namespace />filterValue' + filterIndex + '">' +
						'<option value=""><liferay-ui:message key="select.option" /></option>';

					if (field && field.options) {
						field.options.forEach(function(option) {
							selectHtml += '<option value="' + option.value + '">' + option.label + '</option>';
						});
					}

					selectHtml += '</select>';
					container.innerHTML = selectHtml;
				} else if (fieldType === "checkbox_multiple") {
					var checkboxHtml = '<div class="checkbox-group">';

					if (field && field.options) {
						field.options.forEach(function(option, index) {
							checkboxHtml += '<div class="checkbox">' +
								'<label>' +
								'<input type="checkbox" name="<portlet:namespace />filterValue' + filterIndex + '" value="' + option.value + '"> ' +
								option.label +
								'</label>' +
								'</div>';
						});
					}

					checkboxHtml += '</div>';
					container.innerHTML = checkboxHtml;
				} else {
					container.innerHTML = '<input type="text" class="form-control" name="<portlet:namespace />filterValue' + filterIndex + '" placeholder="' + '<%= LanguageUtil.get(locale, "search.for") %>' + ' ' + fieldName + ' ..." />';
				}
			}

			function updateFilterCount() {
				var count = document.querySelectorAll(".dynamic-filter-row").length;
				var countInput = document.getElementById("<portlet:namespace />dynamicFilterCount");
				if (countInput) {
					countInput.value = count;
				}
			}

			function clearAllFilters() {
				var container = document.getElementById(
					"<portlet:namespace />dynamicFiltersContainer"
				);
				if (container) {
					container.innerHTML = "";
				}
				filterIndex = 0;
				updateFilterCount();
			}
		}

		setTimeout(function() {
			if (document.readyState === 'loading') {
				document.addEventListener('DOMContentLoaded', init);
			} else {
				init();
			}
		}, 250);
	})();
</aui:script>