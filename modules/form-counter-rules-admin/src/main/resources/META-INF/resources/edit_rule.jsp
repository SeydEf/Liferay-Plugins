<%@ page import="ir.seydef.plugin.formcounter.rules.admin.util.ExpandoFieldUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>

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
                id="<portlet:namespace/>conditionCount"
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
                                            label="not-equal"
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
    var customFieldsOptions = [
    <%
        for (int i = 0; i < customFields.size(); i++) {
            ExpandoFieldInfo field = customFields.get(i);
            if (i > 0) {
    %>
        <%= "," %>
    <%
            }
    %>
    {name: "<%= field.getName() %>", displayName: "<%= field.getDisplayName() %>", type: "<%= field.getType() %>"}
    <%
        }
    %>
    ];

    // Create a function that generates custom field options HTML dynamically
    function getCustomFieldOptionsHTML() {
        var optionsHTML = '';
        for (var i = 0; i < customFieldsOptions.length; i++) {
            optionsHTML += '<option value="' + customFieldsOptions[i].name + '">' +
                customFieldsOptions[i].displayName + ' (' + customFieldsOptions[i].type + ')</option>';
        }
        return optionsHTML;
    }

    // Initialize the condition template with a function that will insert options when needed
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
                '<label>&nbsp;</label>' +
                '<button type="button" class="btn btn-danger remove-condition" data-index="{index}" style="margin-top: 24px;">' +
                    '<i class="icon-trash"></i>' +
                '</button>' +
            '</div>' +
        '</div>' +
    '</div>';

    /* Rule Builder JS */

    AUI().ready(function () {
    var conditionsContainer = document.getElementById("conditionsContainer");
    var addConditionButton = document.querySelector(".add-condition");
    // Try multiple ways to get the conditionCount input element
    var conditionCountInput = document.getElementById("<portlet:namespace/>conditionCount") ||
                             document.querySelector("input[name='<portlet:namespace/>conditionCount']") ||
                             document.querySelector("input[name='conditionCount']");

    // Log whether we found the element
    if (!conditionCountInput) {
        console.error("Could not find conditionCount input element!");
        // Create a backup input if needed
        var formElement = document.querySelector("form");
        if (formElement) {
            conditionCountInput = document.createElement("input");
            conditionCountInput.type = "hidden";
            conditionCountInput.name = "<portlet:namespace/>conditionCount";
            conditionCountInput.id = "<portlet:namespace/>conditionCount";
            conditionCountInput.value = document.querySelectorAll(".condition-row").length;
            formElement.appendChild(conditionCountInput);
            console.log("Created backup conditionCount input element");
        }
    } else {
        console.log("Found conditionCount input with ID: " + conditionCountInput.id);
    }

    var nextIndex = document.querySelectorAll(".condition-row").length;
    var form = document.getElementById("<portlet:namespace/>fm") || document.querySelector("form[name='<portlet:namespace/>fm']") || document.querySelector("form");

    // Handle add condition button
    if (addConditionButton) {
    addConditionButton.addEventListener("click", function () {
    var newConditionHtml = conditionTemplate.replace(/{index}/g, nextIndex);
    // Insert custom field options
    newConditionHtml = newConditionHtml.replace('{CustomFieldOptionsHTML}', getCustomFieldOptionsHTML());
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
    // Check if conditionCountInput exists before accessing its value property
    if (conditionCountInput) {
        conditionCountInput.value = count;
        console.log("Updated condition count to: " + count);
    } else {
        console.error("Cannot update condition count - input element not found");
        // Try to find it again
        conditionCountInput = document.getElementById("<portlet:namespace/>conditionCount") ||
                             document.querySelector("input[name='<portlet:namespace/>conditionCount']") ||
                             document.querySelector("input[name='conditionCount']");

        if (conditionCountInput) {
            conditionCountInput.value = count;
            console.log("Found and updated condition count to: " + count);
        } else {
            // Create a new input if needed
            var formElement = document.querySelector("form");
            if (formElement) {
                conditionCountInput = document.createElement("input");
                conditionCountInput.type = "hidden";
                conditionCountInput.name = "<portlet:namespace/>conditionCount";
                conditionCountInput.id = "<portlet:namespace/>conditionCount";
                conditionCountInput.value = count;
                formElement.appendChild(conditionCountInput);
                console.log("Created and set condition count input to: " + count);
            }
        }
    }
    }

    // Form validation before submit
    if (form) {
    form.addEventListener("submit", function (event) {
    // Update condition count before validation
    updateConditionCount();

    var conditionCount = 0;

    // Safely get the condition count
    if (conditionCountInput && conditionCountInput.value) {
        conditionCount = parseInt(conditionCountInput.value);
    } else {
        conditionCount = document.querySelectorAll(".condition-row").length;
        console.log("Using DOM count for conditions: " + conditionCount);

        // Make sure we have an input for the form submission
        var hiddenInput = document.querySelector("input[name='<portlet:namespace/>conditionCount']");
        if (!hiddenInput) {
            hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = "<portlet:namespace/>conditionCount";
            hiddenInput.value = conditionCount;
            form.appendChild(hiddenInput);
            console.log("Created condition count input for form submission: " + conditionCount);
        } else {
            hiddenInput.value = conditionCount;
            console.log("Updated existing condition count input: " + conditionCount);
        }
    }
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
    // Try multiple selector approaches to find the fields
    var fieldSelect = document.querySelector('select[name="<portlet:namespace/>field' + index + '"]') ||
                      conditionRows[i].querySelector('select[id$="field' + index + '"]') ||
                      conditionRows[i].querySelector('select');

    var operatorSelect = document.querySelector('select[name="<portlet:namespace/>operator' + index + '"]') ||
                         conditionRows[i].querySelector('select[id$="operator' + index + '"]') ||
                         conditionRows[i].querySelectorAll('select')[1];

    var referenceInput = document.querySelector('input[name="<portlet:namespace/>reference' + index + '"]') ||
                        conditionRows[i].querySelector('input[id$="reference' + index + '"]') ||
                        conditionRows[i].querySelector('input');

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

    // Final update just to be sure
    document.querySelector("input[name='<portlet:namespace/>conditionCount']").value = conditionCount;

    return true;
    });
    }
    });
</aui:script>