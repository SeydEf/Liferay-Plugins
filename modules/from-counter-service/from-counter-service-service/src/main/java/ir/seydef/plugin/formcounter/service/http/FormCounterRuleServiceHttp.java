/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import ir.seydef.plugin.formcounter.service.FormCounterRuleServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>FormCounterRuleServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRuleServiceSoap
 * @generated
 */
public class FormCounterRuleServiceHttp {

	public static java.util.List
		<ir.seydef.plugin.formcounter.model.FormCounterRule>
			getFormCounterRules(
				HttpPrincipal httpPrincipal, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				FormCounterRuleServiceUtil.class, "getFormCounterRules",
				_getFormCounterRulesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<ir.seydef.plugin.formcounter.model.FormCounterRule>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<ir.seydef.plugin.formcounter.model.FormCounterRule>
			getFormCounterRules(
				HttpPrincipal httpPrincipal, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<ir.seydef.plugin.formcounter.model.FormCounterRule>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				FormCounterRuleServiceUtil.class, "getFormCounterRules",
				_getFormCounterRulesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<ir.seydef.plugin.formcounter.model.FormCounterRule>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRule
			getFormCounterRule(
				HttpPrincipal httpPrincipal, long formCounterRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormCounterRuleServiceUtil.class, "getFormCounterRule",
				_getFormCounterRuleParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formCounterRuleId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (ir.seydef.plugin.formcounter.model.FormCounterRule)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<ir.seydef.plugin.formcounter.model.FormCounterRule>
			getFormCounterRulesByActive(
				HttpPrincipal httpPrincipal, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				FormCounterRuleServiceUtil.class, "getFormCounterRulesByActive",
				_getFormCounterRulesByActiveParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<ir.seydef.plugin.formcounter.model.FormCounterRule>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRule
			addFormCounterRule(
				HttpPrincipal httpPrincipal, String ruleName,
				String description, String ruleConditions, String logicOperator,
				boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormCounterRuleServiceUtil.class, "addFormCounterRule",
				_addFormCounterRuleParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleName, description, ruleConditions, logicOperator,
				active, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (ir.seydef.plugin.formcounter.model.FormCounterRule)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRule
			updateFormCounterRule(
				HttpPrincipal httpPrincipal, long formCounterRuleId,
				String ruleName, String description, String ruleConditions,
				String logicOperator, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormCounterRuleServiceUtil.class, "updateFormCounterRule",
				_updateFormCounterRuleParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formCounterRuleId, ruleName, description,
				ruleConditions, logicOperator, active, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (ir.seydef.plugin.formcounter.model.FormCounterRule)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRule
			deleteFormCounterRule(
				HttpPrincipal httpPrincipal, long formCounterRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormCounterRuleServiceUtil.class, "deleteFormCounterRule",
				_deleteFormCounterRuleParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formCounterRuleId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (ir.seydef.plugin.formcounter.model.FormCounterRule)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getFormCounterRulesCount(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(
				FormCounterRuleServiceUtil.class, "getFormCounterRulesCount",
				_getFormCounterRulesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FormCounterRuleServiceHttp.class);

	private static final Class<?>[] _getFormCounterRulesParameterTypes0 =
		new Class[] {int.class, int.class};
	private static final Class<?>[] _getFormCounterRulesParameterTypes1 =
		new Class[] {
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFormCounterRuleParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getFormCounterRulesByActiveParameterTypes3 = new Class[] {
			boolean.class
		};
	private static final Class<?>[] _addFormCounterRuleParameterTypes4 =
		new Class[] {
			String.class, String.class, String.class, String.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFormCounterRuleParameterTypes5 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFormCounterRuleParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _getFormCounterRulesCountParameterTypes7 =
		new Class[] {};

}