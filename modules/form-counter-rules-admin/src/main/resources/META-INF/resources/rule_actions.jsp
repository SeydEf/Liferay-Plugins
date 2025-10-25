<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FormCounterRule rule = (FormCounterRule)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" markupView="lexicon" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editRuleURL">
		<portlet:param name="mvcPath" value="/edit_rule.jsp" />
		<portlet:param name="ruleId" value="<%= String.valueOf(rule.getFormCounterRuleId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editRuleURL %>"
	/>

	<portlet:actionURL name="<%= FormCounterRulesAdminPortletKeys.ACTION_DELETE_RULE %>" var="deleteRuleURL">
		<portlet:param name="ruleId" value="<%= String.valueOf(rule.getFormCounterRuleId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteRuleURL %>"
	/>
</liferay-ui:icon-menu>