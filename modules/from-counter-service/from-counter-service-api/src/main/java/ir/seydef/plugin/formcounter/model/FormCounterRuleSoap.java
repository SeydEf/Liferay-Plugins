/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link ir.seydef.plugin.formcounter.service.http.FormCounterRuleServiceSoap}.
 *
 * @author S.Abolfazl Eftekhari
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FormCounterRuleSoap implements Serializable {

	public static FormCounterRuleSoap toSoapModel(FormCounterRule model) {
		FormCounterRuleSoap soapModel = new FormCounterRuleSoap();

		soapModel.setFormCounterRuleId(model.getFormCounterRuleId());
		soapModel.setRuleName(model.getRuleName());
		soapModel.setDescription(model.getDescription());
		soapModel.setRuleConditions(model.getRuleConditions());
		soapModel.setActive(model.isActive());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());

		return soapModel;
	}

	public static FormCounterRuleSoap[] toSoapModels(FormCounterRule[] models) {
		FormCounterRuleSoap[] soapModels =
			new FormCounterRuleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FormCounterRuleSoap[][] toSoapModels(
		FormCounterRule[][] models) {

		FormCounterRuleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FormCounterRuleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FormCounterRuleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FormCounterRuleSoap[] toSoapModels(
		List<FormCounterRule> models) {

		List<FormCounterRuleSoap> soapModels =
			new ArrayList<FormCounterRuleSoap>(models.size());

		for (FormCounterRule model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FormCounterRuleSoap[soapModels.size()]);
	}

	public FormCounterRuleSoap() {
	}

	public long getPrimaryKey() {
		return _formCounterRuleId;
	}

	public void setPrimaryKey(long pk) {
		setFormCounterRuleId(pk);
	}

	public long getFormCounterRuleId() {
		return _formCounterRuleId;
	}

	public void setFormCounterRuleId(long formCounterRuleId) {
		_formCounterRuleId = formCounterRuleId;
	}

	public String getRuleName() {
		return _ruleName;
	}

	public void setRuleName(String ruleName) {
		_ruleName = ruleName;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getRuleConditions() {
		return _ruleConditions;
	}

	public void setRuleConditions(String ruleConditions) {
		_ruleConditions = ruleConditions;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	private long _formCounterRuleId;
	private String _ruleName;
	private String _description;
	private String _ruleConditions;
	private boolean _active;
	private long _companyId;
	private long _groupId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;

}