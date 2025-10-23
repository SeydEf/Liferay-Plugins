/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.service.base.FormCounterRuleServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = {
		"json.web.service.context.name=formcounter",
		"json.web.service.context.path=FormCounterRule"
	},
	service = AopService.class
)
public class FormCounterRuleServiceImpl extends FormCounterRuleServiceBaseImpl {

	public FormCounterRule addFormCounterRule(
			String ruleName, String description, String ruleConditions,
			String logicOperator, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		return formCounterRuleLocalService.addFormCounterRule(
			ruleName, description, ruleConditions,
			logicOperator, active, serviceContext);
	}

	public FormCounterRule deleteFormCounterRule(long formCounterRuleId)
		throws PortalException {

		FormCounterRule formCounterRule =
			formCounterRuleLocalService.getFormCounterRule(formCounterRuleId);

		_formCounterRuleModelResourcePermission.check(
			getPermissionChecker(), formCounterRule, ActionKeys.DELETE);

		return formCounterRuleLocalService.deleteFormCounterRule(
			formCounterRuleId);
	}

	public FormCounterRule getFormCounterRule(long formCounterRuleId)
		throws PortalException {

		FormCounterRule formCounterRule =
			formCounterRuleLocalService.getFormCounterRule(formCounterRuleId);

		_formCounterRuleModelResourcePermission.check(
			getPermissionChecker(), formCounterRule, ActionKeys.VIEW);

		return formCounterRule;
	}

	public List<FormCounterRule> getFormCounterRules(int start, int end) {
		return formCounterRuleLocalService.getFormCounterRules(start, end);
	}

	public List<FormCounterRule> getFormCounterRules(
		int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator) {

		return formCounterRuleLocalService.getFormCounterRules(start, end);
	}

	public List<FormCounterRule> getFormCounterRulesByActive(boolean active) {
		return formCounterRuleLocalService.findByActive(active);
	}

	public int getFormCounterRulesCount() {
		return formCounterRuleLocalService.getFormCounterRulesCount();
	}

	public FormCounterRule updateFormCounterRule(
			long formCounterRuleId, String ruleName, String description,
			String ruleConditions, String logicOperator, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		FormCounterRule formCounterRule =
			formCounterRuleLocalService.getFormCounterRule(formCounterRuleId);

		_formCounterRuleModelResourcePermission.check(
			getPermissionChecker(), formCounterRule, ActionKeys.UPDATE);

		return formCounterRuleLocalService.updateFormCounterRule(
			formCounterRuleId, ruleName, description, ruleConditions,
			logicOperator, active, serviceContext);
	}

	@Reference
	private ModelResourcePermission<FormCounterRule>
		_formCounterRuleModelResourcePermission;

	@Reference
	private PortletResourcePermission _portletResourcePermission;

}