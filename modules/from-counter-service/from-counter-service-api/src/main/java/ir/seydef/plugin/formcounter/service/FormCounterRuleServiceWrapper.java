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

	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			deleteFormCounterRule(long formCounterRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleService.deleteFormCounterRule(formCounterRuleId);
	}

	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			getFormCounterRule(long formCounterRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleService.getFormCounterRule(formCounterRuleId);
	}

	@Override
	public java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
		getFormCounterRules(int start, int end) {

		return _formCounterRuleService.getFormCounterRules(start, end);
	}

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

	@Override
	public java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
		getFormCounterRulesByActive(boolean active) {

		return _formCounterRuleService.getFormCounterRulesByActive(active);
	}

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