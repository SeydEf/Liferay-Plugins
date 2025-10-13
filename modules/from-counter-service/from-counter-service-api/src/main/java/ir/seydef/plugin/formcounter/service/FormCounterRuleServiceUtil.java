/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import ir.seydef.plugin.formcounter.model.FormCounterRule;

import java.util.List;

/**
 * Provides the remote service utility for FormCounterRule. This utility wraps
 * <code>ir.seydef.plugin.formcounter.service.impl.FormCounterRuleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRuleService
 * @generated
 */
public class FormCounterRuleServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>ir.seydef.plugin.formcounter.service.impl.FormCounterRuleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Add a new FormCounterRule
	 *
	 * @param ruleName the rule name
	 * @param description the rule description
	 * @param ruleConditions the rule conditions as JSON string
	 * @param logicOperator the logic operator (AND/OR)
	 * @param active whether the rule is active
	 * @param serviceContext the service context
	 * @return the new FormCounterRule
	 * @throws PortalException
	 */
	public static FormCounterRule addFormCounterRule(
			String ruleName, String description, String ruleConditions,
			String logicOperator, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFormCounterRule(
			ruleName, description, ruleConditions, logicOperator, active,
			serviceContext);
	}

	/**
	 * Delete a FormCounterRule
	 *
	 * @param formCounterRuleId the primary key
	 * @return the deleted FormCounterRule
	 * @throws PortalException
	 */
	public static FormCounterRule deleteFormCounterRule(long formCounterRuleId)
		throws PortalException {

		return getService().deleteFormCounterRule(formCounterRuleId);
	}

	/**
	 * Get FormCounterRule by primary key
	 *
	 * @param formCounterRuleId the primary key
	 * @return FormCounterRule entity
	 * @throws PortalException
	 */
	public static FormCounterRule getFormCounterRule(long formCounterRuleId)
		throws PortalException {

		return getService().getFormCounterRule(formCounterRuleId);
	}

	/**
	 * Get all FormCounterRule entities
	 *
	 * @param start the start index
	 * @param end the end index
	 * @return list of FormCounterRule entities
	 */
	public static List<FormCounterRule> getFormCounterRules(
		int start, int end) {

		return getService().getFormCounterRules(start, end);
	}

	/**
	 * Get all FormCounterRule entities with ordering
	 *
	 * @param start the start index
	 * @param end the end index
	 * @param orderByComparator the order by comparator
	 * @return list of FormCounterRule entities
	 */
	public static List<FormCounterRule> getFormCounterRules(
		int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator) {

		return getService().getFormCounterRules(start, end, orderByComparator);
	}

	/**
	 * Get FormCounterRules by active status
	 *
	 * @param active the active status
	 * @return list of active/inactive FormCounterRule entities
	 */
	public static List<FormCounterRule> getFormCounterRulesByActive(
		boolean active) {

		return getService().getFormCounterRulesByActive(active);
	}

	/**
	 * Count all FormCounterRules
	 *
	 * @return the count of all FormCounterRules
	 */
	public static int getFormCounterRulesCount() {
		return getService().getFormCounterRulesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * Update an existing FormCounterRule
	 *
	 * @param formCounterRuleId the primary key
	 * @param ruleName the rule name
	 * @param description the rule description
	 * @param ruleConditions the rule conditions as JSON string
	 * @param logicOperator the logic operator (AND/OR)
	 * @param active whether the rule is active
	 * @param serviceContext the service context
	 * @return the updated FormCounterRule
	 * @throws PortalException
	 */
	public static FormCounterRule updateFormCounterRule(
			long formCounterRuleId, String ruleName, String description,
			String ruleConditions, String logicOperator, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFormCounterRule(
			formCounterRuleId, ruleName, description, ruleConditions,
			logicOperator, active, serviceContext);
	}

	public static FormCounterRuleService getService() {
		return _service;
	}

	public static void setService(FormCounterRuleService service) {
		_service = service;
	}

	private static volatile FormCounterRuleService _service;

}