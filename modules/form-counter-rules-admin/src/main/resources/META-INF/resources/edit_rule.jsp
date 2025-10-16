<%@ page import="ir.seydef.plugin.formcounter.rules.admin.util.ExpandoFieldUtil" %>
<%@ page import="java.util.ArrayList" %>

<%@ include file="/init.jsp" %>

<%
    FormCounterRule formCounterRule = (FormCounterRule) request.getAttribute("rule");
    Rule ruleModel = (Rule) request.getAttribute("ruleModel");
    long ruleId = 0;
    String ruleName = "";
    String description = "";
    boolean active = true;
    String logicOperator = FormCounterRulesAdminPortletKeys.LOGIC_OR;
    List<RuleCondition> conditions = new ArrayList<>();
    if (formCounterRule != null) {
        ruleId = formCounterRule.getFormCounterRuleId();
        ruleName = formCounterRule.getRuleName();
        description = formCounterRule.getDescription();
        active = formCounterRule.getActive();
        logicOperator = formCounterRule.getLogicOperator();
        if (ruleModel != null) {
            conditions = ruleModel.getConditions();
        }
    }
    String actionName = (ruleId > 0) ? FormCounterRulesAdminPortletKeys.ACTION_UPDATE_RULE :
            FormCounterRulesAdminPortletKeys.ACTION_ADD_RULE;
    String actionTitle = (ruleId > 0) ? "edit-rule" : "add-rule";
    List<ExpandoFieldInfo> customFields = ExpandoFieldUtil.getUserCustomFields(themeDisplay.getCompanyId());
%>

<portlet:actionURL name="<%= actionName %>" var="saveRuleURL"/>
<portlet:renderURL var="viewRulesURL"/>

<div class="container-fluid-1280">
    <aui:form action="<%= saveRuleURL %>" method="post" name="fm">
        <aui:input name="ruleId" type="hidden" value="<%= ruleId %>"/>
        <aui:input
                name="conditionCount"
                id="conditionCount"
                type="hidden"
                value="<%= conditions.size() %>"
        />

        <aui:model-context
                bean="<%= formCounterRule %>"
                model="<%= FormCounterRule.class %>"
        />

        <liferay-ui:header
                backURL="<%= viewRulesURL %>"
                title="<%= actionTitle %>"
        />

        <aui:fieldset>
            <aui:input
                    name="ruleName"
                    label="rule-name"
                    value="<%= ruleName %>"
                    required="true"
            />
            <aui:input
                    name="description"
                    label="description"
                    value="<%= description %>"
            />

            <aui:select name="logicOperator" label="logic-operator">
                <aui:option
                        value="<%= FormCounterRulesAdminPortletKeys.LOGIC_AND %>"
                        label="AND (All conditions must match)"
                        selected='<%= "AND".equals(logicOperator) %>'
                />
                <aui:option
                        value="<%= FormCounterRulesAdminPortletKeys.LOGIC_OR %>"
                        label="OR (Any condition can match)"
                        selected='<%= "OR".equals(logicOperator) %>'
                />
            </aui:select>

            <aui:input
                    name="active"
                    label="active"
                    type="checkbox"
                    checked="<%= active %>"
            />
        </aui:fieldset>

        <aui:fieldset-group markupView="lexicon">
            <aui:fieldset label="conditions">
                <div id="conditionsContainer">
                    <% for (int i = 0; i < conditions.size(); i++) {
                        RuleCondition
                                condition = conditions.get(i); %>
                    <div class="condition-row" data-index="<%= i %>">
                        <div class="row">
                            <div class="col-md-3">
                                <aui:select
                                        name='<%= "field" + i %>'
                                        label="custom-field"
                                >
                                    <% for (ExpandoFieldInfo field : customFields) { %>
                                    <aui:option
                                            value="<%= field.getName() %>"
                                            selected="<%= condition.getField().equals(field.getName()) %>"
                                    >
                                        <%= field.getDisplayName() %> (<%= field.getType() %>)
                                    </aui:option>
                                    <% } %>
                                </aui:select>
                            </div>
                            <div class="col-md-3">
                                <aui:select name='<%= "operator" + i %>' label="operator">
                                    <aui:option
                                            value="<%= FormCounterRulesAdminPortletKeys.OPERATOR_CONTAINS %>"
                                            label="contains"
                                            selected='<%= "contains".equals(condition.getOperator()) %>'
                                    />
                                    <aui:option
                                            value="<%= FormCounterRulesAdminPortletKeys.OPERATOR_EQUAL %>"
                                            label="equal"
                                            selected='<%= "equal".equals(condition.getOperator()) %>'
                                    />
                                    <aui:option
                                            value="<%= FormCounterRulesAdminPortletKeys.OPERATOR_NOT_EQUAL %>"
                                            label="not equal"
                                            selected='<%= "not-equal".equals(condition.getOperator()) %>'
                                    />
                                </aui:select>
                            </div>
                            <div class="col-md-5">
                                <aui:input
                                        name='<%= "reference" + i %>'
                                        label="reference-field-name"
                                        value="<%= condition.getReference() %>"
                                />
                            </div>
                            <div class="col-md-1">
                                <button
                                        type="button"
                                        class="btn btn-danger remove-condition"
                                        data-index="<%= i %>"
                                >
                                    <i class="icon-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>
                <div class="text-center" style="margin-top: 15px">
                    <button type="button" class="btn btn-success add-condition">
                        <i class="icon-plus"></i>
                        <liferay-ui:message key="add-condition"/>
                    </button>
                </div>
            </aui:fieldset>
        </aui:fieldset-group>

        <aui:button-row>
            <aui:button type="submit" value="save" cssClass="btn-primary"/>
            <aui:button href="<%= viewRulesURL %>" value="cancel"/>
        </aui:button-row>
    </aui:form>
</div>

<aui:script>
    // Generate custom fields options for JavaScript template
    var customFieldsOptions = [
    <%
        for (int i = 0; i < customFields.size(); i++) {
            ExpandoFieldInfo field = customFields.get(i);
            if (i > 0) {%>
    <%= "," %>
    <%}%>
    {name: "<%= field.getName() %>", displayName: "<%= field.getDisplayName() %>", type: "<%= field.getType() %>"}
    <%
        }
    %>
    ];

    // Build the select options HTML with field type
    var customFieldOptionsHTML = "";
    console.log(customFieldsOptions);
    for (var i = 0; i < customFieldsOptions.length; i++) {
    customFieldOptionsHTML += '<option value="' + customFieldsOptions[i].name + '">' +
    customFieldsOptions[i].displayName + ' (' + customFieldsOptions[i].type + ')</option>';
    }

    // Initialize empty condition template
    const conditionTemplate = `
    <div class="condition-row" data-index="{index}">
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label for="_<portlet:namespace />field{index}">Custom Field</label>
                    <select id="_<portlet:namespace />field{index}" name="<portlet:namespace />field{index}"
                            class="form-control">
                            ${customFieldOptionsHTML}
                    </select>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label for="_<portlet:namespace />operator{index}">Operator</label>
                    <select id="_<portlet:namespace />operator{index}" name="<portlet:namespace />operator{index}"
                            class="form-control">
                        <option value="contains">contains</option>
                        <option value="equal">equal</option>
                        <option value="not-equal">not equal</option>
                    </select>
                </div>
            </div>
            <div class="col-md-5">
                <div class="form-group">
                    <label for="_<portlet:namespace />reference{index}">Reference Field Name</label>
                    <input id="_<portlet:namespace />reference{index}" name="<portlet:namespace />reference{index}"
                           class="form-control" type="text">
                </div>
            </div>
            <div class="col-md-1">
                <label>&nbsp;</label>
                <button type="button" class="btn btn-danger remove-condition" data-index="{index}"
                        style="margin-top: 24px;">
                    <i class="icon-trash"></i>
                </button>
            </div>
        </div>
    </div>
    `;


    /* Rule Builder JS */

    AUI().ready(function () {
    var conditionsContainer = document.getElementById("conditionsContainer");
    var addConditionButton = document.querySelector(".add-condition");
    var conditionCountInput = document.getElementById(
    "_<portlet:namespace/>conditionCount"
    );
    var nextIndex = document.querySelectorAll(".condition-row").length;
    var form = document.getElementById("<portlet:namespace/>fm");

    // Handle add condition button
    if (addConditionButton) {
    addConditionButton.addEventListener("click", function () {
    console.log("Adding new condition with index: " + nextIndex);
    var newConditionHtml = conditionTemplate.replace(/{index}/g, nextIndex);
    var tempDiv = document.createElement("div");
    tempDiv.innerHTML = newConditionHtml;

    var newConditionNode = tempDiv.firstElementChild;
    conditionsContainer.appendChild(newConditionNode);

    nextIndex++;
    updateConditionCount();

    console.log("Condition added. Total conditions: " + document.querySelectorAll(".condition-row").length);

    // Attach event listeners to the new remove button
    attachRemoveConditionEvent(
    newConditionNode.querySelector(".remove-condition")
    );
    });
    }

    // Attach event listeners to existing remove buttons
    var removeButtons = document.querySelectorAll(".remove-condition");
    for (var i = 0; i < removeButtons.length; i++) {
    attachRemoveConditionEvent(removeButtons[i]);
    }

    // Function to handle remove condition button click
    function attachRemoveConditionEvent(button) {
    button.addEventListener("click", function () {
    var conditionRow = this.closest(".condition-row");
    conditionRow.parentNode.removeChild(conditionRow);
    updateConditionCount();
    reindexConditions();
    });
    }

    // Function to reindex conditions after removal
    function reindexConditions() {
    var conditionRows = document.querySelectorAll(".condition-row");
    nextIndex = conditionRows.length;

    for (var i = 0; i < conditionRows.length; i++) {
    var currentIndex = conditionRows[i].getAttribute("data-index");
    var newIndex = i;

    // Update data-index attribute
    conditionRows[i].setAttribute("data-index", newIndex);

    // Update name attributes
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
    fieldSelect.name = "<portlet:namespace/>field" + newIndex;
    if (operatorSelect)
    operatorSelect.name = "<portlet:namespace/>operator" + newIndex;
    if (referenceInput)
    referenceInput.name = "<portlet:namespace/>reference" + newIndex;
    if (removeButton) removeButton.setAttribute("data-index", newIndex);
    }
    }

    // Update the condition count hidden input
    function updateConditionCount() {
    var count = document.querySelectorAll(".condition-row").length;
    conditionCountInput.value = count;
    }

    // Form validation before submit
    if (form) {
    form.addEventListener("submit", function (event) {
    // Update condition count before validation
    updateConditionCount();

    var conditionCount = parseInt(conditionCountInput.value);
    var ruleName = document.getElementById("<portlet:namespace/>ruleName").value.trim();

    // Validate rule name
    if (!ruleName) {
    alert("Please enter a rule name.");
    event.preventDefault();
    return false;
    }

    // Validate at least one condition exists
    if (conditionCount === 0) {
    alert("Please add at least one condition to the rule.");
    event.preventDefault();
    return false;
    }

    // Validate all condition fields are filled
    var conditionRows = document.querySelectorAll(".condition-row");
    for (var i = 0; i < conditionRows.length; i++) {
    var index = conditionRows[i].getAttribute("data-index");
    var fieldSelect = document.querySelector('select[name="<portlet:namespace/>field' + index + '"]');
    var operatorSelect = document.querySelector('select[name="<portlet:namespace/>operator' + index + '"]');
    var referenceInput = document.querySelector('input[name="<portlet:namespace/>reference' + index + '"]');

    if (!fieldSelect || !fieldSelect.value) {
    alert("Please select a custom field for condition " + (parseInt(i) + 1));
    event.preventDefault();
    return false;
    }

    if (!operatorSelect || !operatorSelect.value) {
    alert("Please select an operator for condition " + (parseInt(i) + 1));
    event.preventDefault();
    return false;
    }

    if (!referenceInput || !referenceInput.value.trim()) {
    alert("Please enter a reference value for condition " + (parseInt(i) + 1));
    event.preventDefault();
    return false;
    }
    }

    // Log for debugging
    console.log("Form submitting with " + conditionCount + " conditions");
    return true;
    });
    }
    });
</aui:script>