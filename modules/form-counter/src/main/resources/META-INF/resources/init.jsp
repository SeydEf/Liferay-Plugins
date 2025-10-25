<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="ir.seydef.plugin.formcounter.constants.FormCounterPortletKeys" %><%@
page import="ir.seydef.plugin.formcounter.model.FormInstanceDisplayDTO" %><%@
page import="ir.seydef.plugin.formcounter.model.FormRecordDisplayDTO" %>

<%@ page import="java.util.List" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<link
	href="<%= request.getContextPath() %>/css/main.css"
	rel="stylesheet"
	type="text/css"
/>

<% String errorMessage = (String) request.getAttribute("errorMessage"); %>