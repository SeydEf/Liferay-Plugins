/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import ir.seydef.plugin.formcounter.service.FormCounterRuleServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>FormCounterRuleServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>ir.seydef.plugin.formcounter.model.FormCounterRuleSoap</code>. If the method in the
 * service utility returns a
 * <code>ir.seydef.plugin.formcounter.model.FormCounterRule</code>, that is translated to a
 * <code>ir.seydef.plugin.formcounter.model.FormCounterRuleSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRuleServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FormCounterRuleServiceSoap {

	public static ir.seydef.plugin.formcounter.model.FormCounterRuleSoap
			addFormCounterRule(
				String ruleName, String description, String ruleConditions,
				boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			ir.seydef.plugin.formcounter.model.FormCounterRule returnValue =
				FormCounterRuleServiceUtil.addFormCounterRule(
					ruleName, description, ruleConditions, active,
					serviceContext);

			return ir.seydef.plugin.formcounter.model.FormCounterRuleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRuleSoap
			deleteFormCounterRule(long formCounterRuleId)
		throws RemoteException {

		try {
			ir.seydef.plugin.formcounter.model.FormCounterRule returnValue =
				FormCounterRuleServiceUtil.deleteFormCounterRule(
					formCounterRuleId);

			return ir.seydef.plugin.formcounter.model.FormCounterRuleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRuleSoap
			getFormCounterRule(long formCounterRuleId)
		throws RemoteException {

		try {
			ir.seydef.plugin.formcounter.model.FormCounterRule returnValue =
				FormCounterRuleServiceUtil.getFormCounterRule(
					formCounterRuleId);

			return ir.seydef.plugin.formcounter.model.FormCounterRuleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRuleSoap[]
			getFormCounterRules(int start, int end)
		throws RemoteException {

		try {
			java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
				returnValue = FormCounterRuleServiceUtil.getFormCounterRules(
					start, end);

			return ir.seydef.plugin.formcounter.model.FormCounterRuleSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRuleSoap[]
			getFormCounterRules(
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<ir.seydef.plugin.formcounter.model.FormCounterRule>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
				returnValue = FormCounterRuleServiceUtil.getFormCounterRules(
					start, end, orderByComparator);

			return ir.seydef.plugin.formcounter.model.FormCounterRuleSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRuleSoap[]
			getFormCounterRulesByActive(boolean active)
		throws RemoteException {

		try {
			java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
				returnValue =
					FormCounterRuleServiceUtil.getFormCounterRulesByActive(
						active);

			return ir.seydef.plugin.formcounter.model.FormCounterRuleSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFormCounterRulesCount() throws RemoteException {
		try {
			int returnValue =
				FormCounterRuleServiceUtil.getFormCounterRulesCount();

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormCounterRuleSoap
			updateFormCounterRule(
				long formCounterRuleId, String ruleName, String description,
				String ruleConditions, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			ir.seydef.plugin.formcounter.model.FormCounterRule returnValue =
				FormCounterRuleServiceUtil.updateFormCounterRule(
					formCounterRuleId, ruleName, description, ruleConditions,
					active, serviceContext);

			return ir.seydef.plugin.formcounter.model.FormCounterRuleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FormCounterRuleServiceSoap.class);

}