/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FormCounterRuleService}.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRuleService
 * @generated
 */
public class FormCounterRuleServiceWrapper
	implements FormCounterRuleService, ServiceWrapper<FormCounterRuleService> {

	public FormCounterRuleServiceWrapper(
		FormCounterRuleService formCounterRuleService) {

		_formCounterRuleService = formCounterRuleService;
	}

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
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			addFormCounterRule(
				String ruleName, String description, String ruleConditions,
				String logicOperator, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleService.addFormCounterRule(
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
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			deleteFormCounterRule(long formCounterRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleService.deleteFormCounterRule(formCounterRuleId);
	}

	/**
	 * Get FormCounterRule by primary key
	 *
	 * @param formCounterRuleId the primary key
	 * @return FormCounterRule entity
	 * @throws PortalException
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			getFormCounterRule(long formCounterRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleService.getFormCounterRule(formCounterRuleId);
	}

	/**
	 * Get all FormCounterRule entities
	 *
	 * @param start the start index
	 * @param end the end index
	 * @return list of FormCounterRule entities
	 */
	@Override
	public java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
		getFormCounterRules(int start, int end) {

		return _formCounterRuleService.getFormCounterRules(start, end);
	}

	/**
	 * Get all FormCounterRule entities with ordering
	 *
	 * @param start the start index
	 * @param end the end index
	 * @param orderByComparator the order by comparator
	 * @return list of FormCounterRule entities
	 */
	@Override
	public java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
		getFormCounterRules(
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<ir.seydef.plugin.formcounter.model.FormCounterRule>
					orderByComparator) {

		return _formCounterRuleService.getFormCounterRules(
			start, end, orderByComparator);
	}

	/**
	 * Get FormCounterRules by active status
	 *
	 * @param active the active status
	 * @return list of active/inactive FormCounterRule entities
	 */
	@Override
	public java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
		getFormCounterRulesByActive(boolean active) {

		return _formCounterRuleService.getFormCounterRulesByActive(active);
	}

	/**
	 * Count all FormCounterRules
	 *
	 * @return the count of all FormCounterRules
	 */
	@Override
	public int getFormCounterRulesCount() {
		return _formCounterRuleService.getFormCounterRulesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _formCounterRuleService.getOSGiServiceIdentifier();
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
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			updateFormCounterRule(
				long formCounterRuleId, String ruleName, String description,
				String ruleConditions, String logicOperator, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleService.updateFormCounterRule(
			formCounterRuleId, ruleName, description, ruleConditions,
			logicOperator, active, serviceContext);
	}

	@Override
	public FormCounterRuleService getWrappedService() {
		return _formCounterRuleService;
	}

	@Override
	public void setWrappedService(
		FormCounterRuleService formCounterRuleService) {

		_formCounterRuleService = formCounterRuleService;
	}

	private FormCounterRuleService _formCounterRuleService;

}