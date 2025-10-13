/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FormCounterRule}.
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRule
 * @generated
 */
public class FormCounterRuleWrapper
	extends BaseModelWrapper<FormCounterRule>
	implements FormCounterRule, ModelWrapper<FormCounterRule> {

	public FormCounterRuleWrapper(FormCounterRule formCounterRule) {
		super(formCounterRule);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("formCounterRuleId", getFormCounterRuleId());
		attributes.put("ruleName", getRuleName());
		attributes.put("description", getDescription());
		attributes.put("ruleConditions", getRuleConditions());
		attributes.put("logicOperator", getLogicOperator());
		attributes.put("active", isActive());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long formCounterRuleId = (Long)attributes.get("formCounterRuleId");

		if (formCounterRuleId != null) {
			setFormCounterRuleId(formCounterRuleId);
		}

		String ruleName = (String)attributes.get("ruleName");

		if (ruleName != null) {
			setRuleName(ruleName);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String ruleConditions = (String)attributes.get("ruleConditions");

		if (ruleConditions != null) {
			setRuleConditions(ruleConditions);
		}

		String logicOperator = (String)attributes.get("logicOperator");

		if (logicOperator != null) {
			setLogicOperator(logicOperator);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}
	}

	/**
	 * Returns the active of this form counter rule.
	 *
	 * @return the active of this form counter rule
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the company ID of this form counter rule.
	 *
	 * @return the company ID of this form counter rule
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this form counter rule.
	 *
	 * @return the create date of this form counter rule
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this form counter rule.
	 *
	 * @return the description of this form counter rule
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the form counter rule ID of this form counter rule.
	 *
	 * @return the form counter rule ID of this form counter rule
	 */
	@Override
	public long getFormCounterRuleId() {
		return model.getFormCounterRuleId();
	}

	/**
	 * Returns the group ID of this form counter rule.
	 *
	 * @return the group ID of this form counter rule
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the logic operator of this form counter rule.
	 *
	 * @return the logic operator of this form counter rule
	 */
	@Override
	public String getLogicOperator() {
		return model.getLogicOperator();
	}

	/**
	 * Returns the modified date of this form counter rule.
	 *
	 * @return the modified date of this form counter rule
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this form counter rule.
	 *
	 * @return the primary key of this form counter rule
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the rule conditions of this form counter rule.
	 *
	 * @return the rule conditions of this form counter rule
	 */
	@Override
	public String getRuleConditions() {
		return model.getRuleConditions();
	}

	/**
	 * Returns the rule name of this form counter rule.
	 *
	 * @return the rule name of this form counter rule
	 */
	@Override
	public String getRuleName() {
		return model.getRuleName();
	}

	/**
	 * Returns the user ID of this form counter rule.
	 *
	 * @return the user ID of this form counter rule
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this form counter rule.
	 *
	 * @return the user name of this form counter rule
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this form counter rule.
	 *
	 * @return the user uuid of this form counter rule
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this form counter rule is active.
	 *
	 * @return <code>true</code> if this form counter rule is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this form counter rule is active.
	 *
	 * @param active the active of this form counter rule
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this form counter rule.
	 *
	 * @param companyId the company ID of this form counter rule
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this form counter rule.
	 *
	 * @param createDate the create date of this form counter rule
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this form counter rule.
	 *
	 * @param description the description of this form counter rule
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the form counter rule ID of this form counter rule.
	 *
	 * @param formCounterRuleId the form counter rule ID of this form counter rule
	 */
	@Override
	public void setFormCounterRuleId(long formCounterRuleId) {
		model.setFormCounterRuleId(formCounterRuleId);
	}

	/**
	 * Sets the group ID of this form counter rule.
	 *
	 * @param groupId the group ID of this form counter rule
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the logic operator of this form counter rule.
	 *
	 * @param logicOperator the logic operator of this form counter rule
	 */
	@Override
	public void setLogicOperator(String logicOperator) {
		model.setLogicOperator(logicOperator);
	}

	/**
	 * Sets the modified date of this form counter rule.
	 *
	 * @param modifiedDate the modified date of this form counter rule
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this form counter rule.
	 *
	 * @param primaryKey the primary key of this form counter rule
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the rule conditions of this form counter rule.
	 *
	 * @param ruleConditions the rule conditions of this form counter rule
	 */
	@Override
	public void setRuleConditions(String ruleConditions) {
		model.setRuleConditions(ruleConditions);
	}

	/**
	 * Sets the rule name of this form counter rule.
	 *
	 * @param ruleName the rule name of this form counter rule
	 */
	@Override
	public void setRuleName(String ruleName) {
		model.setRuleName(ruleName);
	}

	/**
	 * Sets the user ID of this form counter rule.
	 *
	 * @param userId the user ID of this form counter rule
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this form counter rule.
	 *
	 * @param userName the user name of this form counter rule
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this form counter rule.
	 *
	 * @param userUuid the user uuid of this form counter rule
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected FormCounterRuleWrapper wrap(FormCounterRule formCounterRule) {
		return new FormCounterRuleWrapper(formCounterRule);
	}

}