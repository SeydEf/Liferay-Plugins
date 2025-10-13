/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.service.base.FormCounterRuleLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * Implementation for the form counter rule local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * <code>ir.seydef.plugin.formcounter.service.FormCounterRuleLocalService</code>
 * interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be
 * accessed from within the same VM.
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 */
@Component(property = "model.class.name=ir.seydef.plugin.formcounter.model.FormCounterRule", service = AopService.class)
public class FormCounterRuleLocalServiceImpl
		extends FormCounterRuleLocalServiceBaseImpl {

	/**
	 * Add a new form counter rule
	 *
	 * @param formCounterRuleId the primary key
	 * @param ruleName          the rule name
	 * @param description       the rule description
	 * @param ruleConditions    JSON string representing the rule conditions
	 * @param logicOperator     the logic operator (AND, OR)
	 * @param active            whether the rule is active
	 * @param serviceContext    the service context
	 * @return the new form counter rule
	 * @throws PortalException
	 */
	public FormCounterRule addFormCounterRule(
			long formCounterRuleId, String ruleName, String description,
			String ruleConditions, String logicOperator, boolean active,
			ServiceContext serviceContext)
			throws PortalException {

		// Generate primary key if not provided
		if (formCounterRuleId <= 0) {
			formCounterRuleId = counterLocalService.increment(
					FormCounterRule.class.getName());
		}

		// Get user information from service context
		long userId = serviceContext.getUserId();
		User user = userLocalService.getUser(userId);

		Date now = new Date();

		// Create and populate the rule object
		FormCounterRule rule = formCounterRulePersistence.create(formCounterRuleId);

		// Set audit fields
		rule.setCompanyId(serviceContext.getCompanyId());
		rule.setGroupId(serviceContext.getScopeGroupId());
		rule.setUserId(userId);
		rule.setUserName(user.getFullName());
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
	 * Find form counter rules by active status
	 *
	 * @param active whether rules are active
	 * @return list of matching rules
	 */
	public List<FormCounterRule> findByActive(boolean active) {
		return formCounterRulePersistence.findByActive(active);
	}

	/**
	 * Update an existing form counter rule
	 *
	 * @param formCounterRuleId the primary key
	 * @param ruleName          the rule name
	 * @param description       the rule description
	 * @param ruleConditions    JSON string representing the rule conditions
	 * @param logicOperator     the logic operator (AND, OR)
	 * @param active            whether the rule is active
	 * @param serviceContext    the service context
	 * @return the updated form counter rule
	 * @throws PortalException
	 */
	public FormCounterRule updateFormCounterRule(
			long formCounterRuleId, String ruleName, String description,
			String ruleConditions, String logicOperator, boolean active,
			ServiceContext serviceContext)
			throws PortalException {

		// Validate input
		if (formCounterRuleId <= 0) {
			throw new PortalException("Invalid rule ID: " + formCounterRuleId);
		}

		if (Validator.isNull(ruleName)) {
			throw new PortalException("Rule name cannot be empty");
		}

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
	 * Delete a form counter rule
	 *
	 * @param formCounterRuleId the rule ID
	 * @return the deleted rule
	 * @throws PortalException
	 */
	@Override
	public FormCounterRule deleteFormCounterRule(long formCounterRuleId)
			throws PortalException {

		// Get the rule
		FormCounterRule rule = formCounterRulePersistence.findByPrimaryKey(
				formCounterRuleId);

		// Delete the rule
		return formCounterRulePersistence.remove(rule);
	}
}