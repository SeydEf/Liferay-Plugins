<%@ include file="/init.jsp" %>

<%
List<FormCounterRule> rules = (List<FormCounterRule>)request.getAttribute("rules");
%>

<div class="container-fluid-1280">
	<liferay-ui:success
		key="rule-added-successfully"
		message="rule-added-successfully"
	/>

	<liferay-ui:success
		key="rule-updated-successfully"
		message="rule-updated-successfully"
	/>

	<liferay-ui:success
		key="rule-deleted-successfully"
		message="rule-deleted-successfully"
	/>

	<liferay-ui:error
		key="error-loading-rules"
		message="error-loading-rules"
	/>

	<liferay-ui:error key="errorAddingRule" message="error-adding-rule" />

	<liferay-ui:error
		key="error-updating-rule"
		message="error-updating-rule"
	/>

	<liferay-ui:error
		key="error-deleting-rule"
		message="error-deleting-rule"
	/>

	<liferay-ui:error
		key="error-invalid-rule-id"
		message="error-invalid-rule-id"
	/>

	<portlet:renderURL var="addRuleURL">
		<portlet:param name="mvcPath" value="/edit_rule.jsp" />
	</portlet:renderURL>

	<div class="button-holder">
		<aui:button-row>
			<aui:button
				cssClass="btn-primary"
				href="<%= addRuleURL %>"
				value="add-rule"
			/>
		</aui:button-row>
	</div>

	<liferay-ui:search-container
		emptyResultsMessage="no-rules-found"
		total="<%= rules.size() %>"
	>
		<liferay-ui:search-container-results results="<%= rules %>" />

		<liferay-ui:search-container-row
			className="ir.seydef.plugin.formcounter.model.FormCounterRule"
			keyProperty="formCounterRuleId"
			modelVar="rule"
		>
			<liferay-ui:search-container-column-text
				name="rule-name"
				value="<%= rule.getRuleName() %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= rule.getDescription() %>"
			/>

			<liferay-ui:search-container-column-text name="status">
				<c:choose>
					<c:when test="<%= rule.getActive() %>">
						<span class="label label-success">
							<liferay-ui:message key="active" />
						</span>
					</c:when>
					<c:otherwise>
						<span class="label label-warning">
							<liferay-ui:message key="inactive" />
						</span>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= rule.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp path="/rule_actions.jsp" />
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>