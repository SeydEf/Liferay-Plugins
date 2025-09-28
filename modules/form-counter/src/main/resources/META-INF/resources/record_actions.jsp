<%@ page import="com.liferay.petra.string.StringPool" %>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ include file="/init.jsp" %>

<%
    ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

    FormRecordDisplayDTO formRecord = (FormRecordDisplayDTO)
            row.getObject();

    String currentURL = themeDisplay.getURLCurrent();
%>

<liferay-ui:icon-menu
        direction="left-side"
        icon="<%= StringPool.BLANK %>"
        markupView="lexicon"
        message="<%= StringPool.BLANK %>"
>
    <portlet:renderURL var="viewRecordDetailURL">
        <portlet:param
                name="recordId"
                value="<%= String.valueOf(formRecord.getRecordId()) %>"
        />
        <portlet:param name="redirect" value="<%= currentURL %>"/>
        <portlet:param name="mvcPath" value="/view_record_detail.jsp"/>
    </portlet:renderURL>

    // Not implemented yet!
    <liferay-ui:icon message="view" url="<%= viewRecordDetailURL %>"/>

    <portlet:resourceURL var="exportRecordURL">
        <portlet:param name="cmd" value="export"/>
        <portlet:param
                name="recordId"
                value="<%= String.valueOf(formRecord.getRecordId()) %>"
        />
    </portlet:resourceURL>

    <liferay-ui:icon message="export" url="<%= exportRecordURL %>"/>
</liferay-ui:icon-menu>
