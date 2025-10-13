/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;

import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.service.base.FormCounterRuleServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * Remote service implementation for the FormCounterRule entity.
 * 
 * <p>
 * This is a remote service. Methods of this service are expected to have
 * security checks based on the propagated JAAS credentials.
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 */
@Component(property = {
		"json.web.service.context.name=formcounter",
		"json.web.service.context.path=FormCounterRule"
}, service = AopService.class)
public class FormCounterRuleServiceImpl extends FormCounterRuleServiceBaseImpl {

	/**
	 * Get all FormCounterRule entities
	 *
	 * @param start the start index
	 * @param end   the end index
	 * @return list of FormCounterRule entities
	 */
	public List<FormCounterRule> getFormCounterRules(int start, int end) {
		return formCounterRulePersistence.findAll(start, end);
	}

	/**
	 * Get all FormCounterRule entities with ordering
	 *
	 * @param start             the start index
	 * @param end               the end index
	 * @param orderByComparator the order by comparator
	 * @return list of FormCounterRule entities
	 */
	public List<FormCounterRule> getFormCounterRules(
			int start, int end,
			OrderByComparator<FormCounterRule> orderByComparator) {

		return formCounterRulePersistence.findAll(start, end, orderByComparator);
	}

	/**
	 * Get FormCounterRule by primary key
	 *
	 * @param formCounterRuleId the primary key
	 * @return FormCounterRule entity
	 * @throws PortalException
	 */
	public FormCounterRule getFormCounterRule(long formCounterRuleId)
			throws PortalException {

		return formCounterRuleLocalService.getFormCounterRule(formCounterRuleId);
	}

	/**
	 * Get FormCounterRules by active status
	 *
	 * @param active the active status
	 * @return list of active/inactive FormCounterRule entities
	 */
	public List<FormCounterRule> getFormCounterRulesByActive(boolean active) {
		return formCounterRulePersistence.findByActive(active);
	}

	/**
	 * Add a new FormCounterRule
	 *
	 * @param ruleName       the rule name
	 * @param description    the rule description
	 * @param ruleConditions the rule conditions as JSON string
	 * @param logicOperator  the logic operator (AND/OR)
	 * @param active         whether the rule is active
	 * @param serviceContext the service context
	 * @return the new FormCounterRule
	 * @throws PortalException
	 */
	public FormCounterRule addFormCounterRule(
			String ruleName, String description, String ruleConditions,
			String logicOperator, boolean active,
			ServiceContext serviceContext)
			throws PortalException {

		// Get user information from service context
		long userId = serviceContext.getUserId();
		Date now = new Date();

		// Create a new rule
		long formCounterRuleId = counterLocalService.increment(
				FormCounterRule.class.getName());

		FormCounterRule rule = formCounterRulePersistence.create(formCounterRuleId);

		// Set audit fields
		rule.setCompanyId(serviceContext.getCompanyId());
		rule.setGroupId(serviceContext.getScopeGroupId());
		rule.setUserId(userId);
		rule.setUserName(userLocalService.getUser(userId).getFullName());
		rule.setCreateDate(serviceContext.getCreateDate(now));
		rule.setModifiedDate(serviceContext.getModifiedDate(now));

		// Set rule fields
		rule.setRuleName(ruleName);
		rule.setDescription(description);
		rule.setRuleConditions(ruleConditions);
		rule.setLogicOperator(logicOperator);
		rule.setActive(active);

		// Persist the rule
		return formCounterRulePersistence.update(rule);
	}

	/**
	 * Update an existing FormCounterRule
	 *
	 * @param formCounterRuleId the primary key
	 * @param ruleName          the rule name
	 * @param description       the rule description
	 * @param ruleConditions    the rule conditions as JSON string
	 * @param logicOperator     the logic operator (AND/OR)
	 * @param active            whether the rule is active
	 * @param serviceContext    the service context
	 * @return the updated FormCounterRule
	 * @throws PortalException
	 */
	public FormCounterRule updateFormCounterRule(
			long formCounterRuleId, String ruleName, String description,
			String ruleConditions, String logicOperator, boolean active,
			ServiceContext serviceContext)
			throws PortalException {

		// Get the rule
		FormCounterRule rule = formCounterRulePersistence.findByPrimaryKey(
				formCounterRuleId);

		// Update audit fields
		rule.setModifiedDate(serviceContext.getModifiedDate(new Date()));

		// Update rule fields
		rule.setRuleName(ruleName);
		rule.setDescription(description);
		rule.setRuleConditions(ruleConditions);
		rule.setLogicOperator(logicOperator);
		rule.setActive(active);

		// Persist the updated rule
		return formCounterRulePersistence.update(rule);
	}

	/**
	 * Delete a FormCounterRule
	 *
	 * @param formCounterRuleId the primary key
	 * @return the deleted FormCounterRule
	 * @throws PortalException
	 */
	public FormCounterRule deleteFormCounterRule(long formCounterRuleId)
			throws PortalException {

		return formCounterRuleLocalService.deleteFormCounterRule(formCounterRuleId);
	}

	/**
	 * Count all FormCounterRules
	 *
	 * @return the count of all FormCounterRules
	 */
	public int getFormCounterRulesCount() {
		return formCounterRulePersistence.countAll();
	}
}