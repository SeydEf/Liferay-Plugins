<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %>

<%@ page import="ir.seydef.plugin.formcounter.rules.admin.util.ExpandoFieldUtil" %>

<%@ page import="java.util.ArrayList" %>

<%@ include file="/init.jsp" %>

<%
FormCounterRule formCounterRule = (FormCounterRule)request.getAttribute("rule");
Rule ruleModel = (Rule)request.getAttribute("ruleModel");

long ruleId = 0;
String ruleName = StringPool.BLANK;
String description = StringPool.BLANK;
boolean active = Boolean.TRUE;
String logicOperator = FormCounterRulesAdminPortletKeys.LOGIC_OR;

List<RuleCondition> conditions = new ArrayList<>();
List<ExpandoFieldInfo> customFields = ExpandoFieldUtil.getAllCustomFields(themeDisplay.getCompanyId());

if (formCounterRule != null) {
	ruleId = formCounterRule.getFormCounterRuleId();
	ruleName = formCounterRule.getRuleName();
	description = formCounterRule.getDescription();
	active = formCounterRule.getActive();

	logicOperator = FormCounterRulesAdminPortletKeys.LOGIC_AND;

	if (ruleModel != null) {
		logicOperator = ruleModel.getLogicOperator();
		conditions = ruleModel.getConditions();
	}
}
%>

<portlet:actionURL name="<%= (ruleId > 0) ? FormCounterRulesAdminPortletKeys.ACTION_UPDATE_RULE : FormCounterRulesAdminPortletKeys.ACTION_ADD_RULE %>" var="saveRuleURL" />
<portlet:renderURL var="viewRulesURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= saveRuleURL %>" method="post" name="fm">
		<aui:input name="ruleId" type="hidden" value="<%= ruleId %>" />

		<aui:input
			id="<portlet:namespace />conditionCount"
			name="conditionCount"
			type="hidden"
			value="<%= conditions.size() %>"
		/>

		<aui:model-context
			bean="<%= formCounterRule %>"
			model="<%= FormCounterRule.class %>"
		/>

		<liferay-ui:header
			backURL="<%= viewRulesURL %>"
			title='<%= (ruleId > 0) ? "edit-rule" : "add-rule" %>'
		/>

		<aui:fieldset>
			<aui:input
				label="rule-name"
				name="ruleName"
				required="true"
				value="<%= ruleName %>"
			/>

			<aui:input
				label="description"
				name="description"
				value="<%= description %>"
			/>

			<aui:select helpMessage="logic-operator-hint" label="logic-operator" name="logicOperator">
				<aui:option
					label="and-description"
					selected="<%= FormCounterRulesAdminPortletKeys.LOGIC_AND.equals(logicOperator) %>"
					value="<%= FormCounterRulesAdminPortletKeys.LOGIC_AND %>"
				/>

				<aui:option
					label="or-description"
					selected="<%= FormCounterRulesAdminPortletKeys.LOGIC_OR.equals(logicOperator) %>"
					value="<%= FormCounterRulesAdminPortletKeys.LOGIC_OR %>"
				/>
			</aui:select>

			<aui:input
				checked="<%= active %>"
				label="active"
				name="active"
				type="checkbox"
			/>
		</aui:fieldset>

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset label="conditions">
				<div id="conditionsContainer">

					<%
					for (int i = 0; i < conditions.size(); i++) {
						RuleCondition condition = conditions.get(i);
					%>

						<div class="condition-row" data-index="<%= i %>">
							<div class="row">
								<div class="col-md-3">
									<aui:select
										label="custom-field"
										name='<%= "field" + i %>'
									>

										<%
										for (ExpandoFieldInfo field : customFields) {
										%>

											<aui:option
												selected="<%= condition.getField().equals(field.getName()) %>"
												value="<%= field.getName() %>"
											>
												<%= field.getDisplayName() %>
											</aui:option>

										<%
										}
										%>

									</aui:select>
								</div>

								<div class="col-md-3">
									<aui:select label="operator" name='<%= "operator" + i %>'>
										<aui:option
											label="contains"
											selected='<%= "contains".equals(condition.getOperator()) %>'
											value="<%= FormCounterRulesAdminPortletKeys.OPERATOR_CONTAINS %>"
										/>

										<aui:option
											label="equal"
											selected='<%= "equal".equals(condition.getOperator()) %>'
											value="<%= FormCounterRulesAdminPortletKeys.OPERATOR_EQUAL %>"
										/>

										<aui:option
											label="not-equal"
											selected='<%= "not-equal".equals(condition.getOperator()) %>'
											value="<%= FormCounterRulesAdminPortletKeys.OPERATOR_NOT_EQUAL %>"
										/>
									</aui:select>
								</div>

								<div class="col-md-5">
									<aui:input
										label="reference-field-name"
										name='<%= "reference" + i %>'
										type="text"
										value="<%= condition.getReference() %>"
									/>
								</div>

								<div class="col-md-1">
									<div class="form-group">
										<button
											type="button"
											class="btn btn-danger remove-condition btn-block"
											data-index="<%= i %>"
										>
											<span><liferay-ui:icon alt="remove" icon="trash" markupView="lexicon" /></span>
										</button>
									</div>
								</div>
							</div>
						</div>

					<%
					}
					%>

				</div>

				<div class="text-center" style="margin-top: 15px;">
					<button class="add-condition btn btn-success" type="button">
						<i class="icon-plus"></i>

						<liferay-ui:message key="add-condition" />
					</button>
				</div>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-primary" type="submit" value="save" />
			<aui:button href="<%= viewRulesURL %>" value="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	var customFieldsOptions = [
		<%
		for (int i = 0; i < customFields.size(); i++) {
			ExpandoFieldInfo field = customFields.get(i);
		%>

			<%= (i > 0) ? "," : "" %>{name: "<%= field.getName() %>", displayName: "<%= field.getDisplayName() %>", type: "<%= field.getType() %>"}
		<%
		}
		%>

	];

	function getCustomFieldOptionsHTML() {
		var optionsHTML = '';

		for (var i = 0; i < customFieldsOptions.length; i++) {
			optionsHTML += '<option value="' + customFieldsOptions[i].name + '">' +
				customFieldsOptions[i].displayName + '</option>';
		}

		return optionsHTML;
	}

	var conditionTemplate =
	'<div class="condition-row" data-index="{index}">' +
		'<div class="row">' +
			'<div class="col-md-3">' +
				'<div class="form-group">' +
					'<label for="_<portlet:namespace />field{index}"><%= LanguageUtil.get(request, "custom-field") %></label>' +
					'<select id="_<portlet:namespace />field{index}" name="<portlet:namespace />field{index}" class="form-control">' +
							'{CustomFieldOptionsHTML}' +
					'</select>' +
				'</div>' +
			'</div>' +
			'<div class="col-md-3">' +
				'<div class="form-group">' +
					'<label for="_<portlet:namespace />operator{index}"><%= LanguageUtil.get(request, "operator") %></label>' +
					'<select id="_<portlet:namespace />operator{index}" name="<portlet:namespace />operator{index}" class="form-control">' +
						'<option value="<%= FormCounterRulesAdminPortletKeys.OPERATOR_CONTAINS %>"><%= LanguageUtil.get(request, "contains") %></option>' +
						'<option value="<%= FormCounterRulesAdminPortletKeys.OPERATOR_EQUAL %>"><%= LanguageUtil.get(request, "equal") %></option>' +
						'<option value="<%= FormCounterRulesAdminPortletKeys.OPERATOR_NOT_EQUAL %>"><%= LanguageUtil.get(request, "not-equal") %></option>' +
					'</select>' +
				'</div>' +
			'</div>' +
			'<div class="col-md-5">' +
				'<div class="form-group">' +
					'<label for="_<portlet:namespace />reference{index}"><%= LanguageUtil.get(request, "reference-field-name") %></label>' +
					'<input id="_<portlet:namespace />reference{index}" name="<portlet:namespace />reference{index}" class="form-control" type="text">' +
				'</div>' +
			'</div>' +
			'<div class="col-md-1">' +
				'<div class="form-group">' +
					'<button type="button" class="btn btn-danger remove-condition btn-block" data-index="{index}">' +
						'<span>' +
							'<svg class="lexicon-icon lexicon-icon-trash" ><use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#trash"></use></svg>' +
						'</span>'
					'</button>' +
				'</div>' +
			'</div>' +
		'</div>' +
	'</div>';

	/* Rule Builder JS */

	AUI().ready(function () {
	var conditionsContainer = document.getElementById("conditionsContainer");
	var addConditionButton = document.querySelector(".add-condition");
	var conditionCountInput =
		document.getElementById("<portlet:namespace />conditionCount") ||
		document.querySelector(
			"input[name='<portlet:namespace />conditionCount']"
		) ||
		document.querySelector("input[name='conditionCount']");

	if (!conditionCountInput) {
		var formElement = document.querySelector("form");
		if (formElement) {
			conditionCountInput = document.createElement("input");
			conditionCountInput.type = "hidden";
			conditionCountInput.name = "<portlet:namespace />conditionCount";
			conditionCountInput.id = "<portlet:namespace />conditionCount";
			conditionCountInput.value = document.querySelectorAll(".condition-row").length;
			formElement.appendChild(conditionCountInput);
		}
	}

	var nextIndex = document.querySelectorAll(".condition-row").length;
	var form =
		document.getElementById("<portlet:namespace />fm") ||
		document.querySelector("form[name='<portlet:namespace />fm']") ||
		document.querySelector("form");

	function addNewCondition() {
		var newConditionHtml = conditionTemplate.replace(/{index}/g, nextIndex);
		newConditionHtml = newConditionHtml.replace(
			"{CustomFieldOptionsHTML}",
			getCustomFieldOptionsHTML()
		);

		var tempDiv = document.createElement("div");
		tempDiv.innerHTML = newConditionHtml;

		var newConditionNode = tempDiv.firstElementChild;
		conditionsContainer.appendChild(newConditionNode);
		nextIndex++;
		updateConditionCount();

		attachRemoveConditionEvent(
			newConditionNode.querySelector(".remove-condition")
		);
	}

	var isNewRule ='<%= ruleId == 0 %>';
	if (isNewRule && nextIndex === 0) {
		addNewCondition();
	}

	if (addConditionButton) {
		addConditionButton.addEventListener("click", function () {
			addNewCondition()
		});
	}

	var removeButtons = document.querySelectorAll(".remove-condition");
	for (var i = 0; i < removeButtons.length; i++) {
		attachRemoveConditionEvent(removeButtons[i]);
	}

	function attachRemoveConditionEvent(button) {
		button.addEventListener("click", function () {
			var conditionRow = this.closest(".condition-row");
			conditionRow.parentNode.removeChild(conditionRow);
			updateConditionCount();
			reindexConditions();
		});
	}

	function reindexConditions() {
		var conditionRows = document.querySelectorAll(".condition-row");
		nextIndex = conditionRows.length;

		for (var i = 0; i < conditionRows.length; i++) {
			var currentIndex = conditionRows[i].getAttribute("data-index");
			var newIndex = i;

			conditionRows[i].setAttribute("data-index", newIndex);

			var fieldSelect = conditionRows[i].querySelector(
				'select[name$="field' + currentIndex + '"]'
			);
			var operatorSelect = conditionRows[i].querySelector(
				'select[name$="operator' + currentIndex + '"]'
			);
			var referenceInput = conditionRows[i].querySelector(
				'input[name$="reference' + currentIndex + '"]'
			);
			var removeButton = conditionRows[i].querySelector(
				"button.remove-condition"
			);

			if (fieldSelect)
				fieldSelect.name = "<portlet:namespace />field" + newIndex;
			if (operatorSelect)
				operatorSelect.name = "<portlet:namespace />operator" + newIndex;
			if (referenceInput)
				referenceInput.name = "<portlet:namespace />reference" + newIndex;
			if (removeButton) removeButton.setAttribute("data-index", newIndex);
		}
	}

	function updateConditionCount() {
		var count = document.querySelectorAll(".condition-row").length;
		if (conditionCountInput) {
			conditionCountInput.value = count;
		} else {
			conditionCountInput =
				document.getElementById("<portlet:namespace />conditionCount") ||
				document.querySelector(
					"input[name='<portlet:namespace />conditionCount']"
				) ||
				document.querySelector("input[name='conditionCount']");

			if (conditionCountInput) {
				conditionCountInput.value = count;
			} else {
				var formElement = document.querySelector("form");
				if (formElement) {
					conditionCountInput = document.createElement("input");
					conditionCountInput.type = "hidden";
					conditionCountInput.name = "<portlet:namespace />conditionCount";
					conditionCountInput.id = "<portlet:namespace />conditionCount";
					conditionCountInput.value = count;
					formElement.appendChild(conditionCountInput);
				}
			}
		}
	}

	if (form) {
		form.addEventListener("submit", function (event) {
			updateConditionCount();

			var conditionCount = 0;

			if (conditionCountInput && conditionCountInput.value) {
				conditionCount = parseInt(conditionCountInput.value);
			} else {
				conditionCount = document.querySelectorAll(".condition-row").length;

				var hiddenInput = document.querySelector(
					"input[name='<portlet:namespace />conditionCount']"
				);
				if (!hiddenInput) {
					hiddenInput = document.createElement("input");
					hiddenInput.type = "hidden";
					hiddenInput.name = "<portlet:namespace />conditionCount";
					hiddenInput.value = conditionCount;
					form.appendChild(hiddenInput);
				} else {
					hiddenInput.value = conditionCount;
				}
			}
			var ruleName = document
				.getElementById("<portlet:namespace />ruleName")
				.value.trim();

			if (!ruleName) {
				alert('<%= UnicodeLanguageUtil.get(request, "please-enter-a-rule-name") %>');
				event.preventDefault();
				return false;
			}

			if (conditionCount === 0) {
				alert('<%= UnicodeLanguageUtil.get(request, "please-add-at-least-one-condition") %>');
				event.preventDefault();
				return false;
			}

			var conditionRows = document.querySelectorAll(".condition-row");
			for (var i = 0; i < conditionRows.length; i++) {
				var index = conditionRows[i].getAttribute("data-index");
				var fieldSelect =
					document.querySelector(
						'select[name="<portlet:namespace />field' + index + '"]'
					) ||
					conditionRows[i].querySelector('select[id$="field' + index + '"]') ||
					conditionRows[i].querySelector("select");

				var operatorSelect =
					document.querySelector(
						'select[name="<portlet:namespace />operator' + index + '"]'
					) ||
					conditionRows[i].querySelector(
						'select[id$="operator' + index + '"]'
					) ||
					conditionRows[i].querySelectorAll("select")[1];

				var referenceInput =
					document.querySelector(
						'input[name="<portlet:namespace />reference' + index + '"]'
					) ||
					conditionRows[i].querySelector(
						'input[id$="reference' + index + '"]'
					) ||
					conditionRows[i].querySelector("input");

				if (!fieldSelect || !fieldSelect.value) {
					alert(
						'<%= LanguageUtil.get(request, "please-select-a-custom-field") %> ' + (parseInt(i) + 1)
					);
					event.preventDefault();
					return false;
				}

				if (!operatorSelect || !operatorSelect.value) {
					alert('<%= UnicodeLanguageUtil.get(request, "please-select-an-operator") %> ' + (parseInt(i) + 1));
					event.preventDefault();
					return false;
				}

				if (!referenceInput || !referenceInput.value.trim()) {
					alert(
						'<%= LanguageUtil.get(request, "please-enter-a-reference-field-name") %> ' + (parseInt(i) + 1)
					);
					event.preventDefault();
					return false;
				}
			}

			document.querySelector(
				"input[name='<portlet:namespace />conditionCount']"
			).value = conditionCount;

			return true;
		});
	}
});
</aui:script>