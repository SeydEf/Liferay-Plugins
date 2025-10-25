/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.service.base.FormCounterRuleLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = "model.class.name=ir.seydef.plugin.formcounter.model.FormCounterRule",
	service = AopService.class
)
public class FormCounterRuleLocalServiceImpl
	extends FormCounterRuleLocalServiceBaseImpl {

	public FormCounterRule addFormCounterRule(
			String ruleName, String description, String ruleConditions,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		long formCounterRuleId = counterLocalService.increment(
			FormCounterRule.class.getName());

		long userId = serviceContext.getUserId();

		User user = userLocalService.getUser(userId);

		Date now = new Date();

		FormCounterRule rule = formCounterRulePersistence.create(
			formCounterRuleId);

		rule.setCompanyId(serviceContext.getCompanyId());
		rule.setGroupId(serviceContext.getScopeGroupId());
		rule.setUserId(userId);
		rule.setUserName(user.getFullName());
		rule.setCreateDate(serviceContext.getCreateDate(now));
		rule.setModifiedDate(serviceContext.getModifiedDate(now));

		rule.setRuleName(ruleName);
		rule.setDescription(description);
		rule.setRuleConditions(ruleConditions);
		rule.setActive(active);

		return formCounterRulePersistence.update(rule);
	}

	@Override
	public FormCounterRule deleteFormCounterRule(long formCounterRuleId)
		throws PortalException {

		FormCounterRule rule = formCounterRulePersistence.findByPrimaryKey(
			formCounterRuleId);

		return formCounterRulePersistence.remove(rule);
	}

	public List<FormCounterRule> findByActive(boolean active) {
		return formCounterRulePersistence.findByActive(active);
	}

	public FormCounterRule updateFormCounterRule(
			long formCounterRuleId, String ruleName, String description,
			String ruleConditions, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		if (formCounterRuleId <= 0) {
			throw new PortalException("Invalid rule ID: " + formCounterRuleId);
		}

		if (Validator.isNull(ruleName)) {
			throw new PortalException("Rule name cannot be empty");
		}

		FormCounterRule rule = formCounterRulePersistence.findByPrimaryKey(
			formCounterRuleId);

		rule.setModifiedDate(serviceContext.getModifiedDate(new Date()));

		rule.setRuleName(ruleName);
		rule.setDescription(description);
		rule.setRuleConditions(ruleConditions);
		rule.setActive(active);

		return formCounterRulePersistence.update(rule);
	}

}