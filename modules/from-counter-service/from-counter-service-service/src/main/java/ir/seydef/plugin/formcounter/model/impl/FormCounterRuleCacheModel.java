/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import ir.seydef.plugin.formcounter.model.FormCounterRule;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing FormCounterRule in entity cache.
 *
 * @author S.Abolfazl Eftekhari
 * @generated
 */
public class FormCounterRuleCacheModel
	implements CacheModel<FormCounterRule>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormCounterRuleCacheModel)) {
			return false;
		}

		FormCounterRuleCacheModel formCounterRuleCacheModel =
			(FormCounterRuleCacheModel)object;

		if (formCounterRuleId == formCounterRuleCacheModel.formCounterRuleId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, formCounterRuleId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{formCounterRuleId=");
		sb.append(formCounterRuleId);
		sb.append(", ruleName=");
		sb.append(ruleName);
		sb.append(", description=");
		sb.append(description);
		sb.append(", ruleConditions=");
		sb.append(ruleConditions);
		sb.append(", logicOperator=");
		sb.append(logicOperator);
		sb.append(", active=");
		sb.append(active);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FormCounterRule toEntityModel() {
		FormCounterRuleImpl formCounterRuleImpl = new FormCounterRuleImpl();

		formCounterRuleImpl.setFormCounterRuleId(formCounterRuleId);

		if (ruleName == null) {
			formCounterRuleImpl.setRuleName("");
		}
		else {
			formCounterRuleImpl.setRuleName(ruleName);
		}

		if (description == null) {
			formCounterRuleImpl.setDescription("");
		}
		else {
			formCounterRuleImpl.setDescription(description);
		}

		if (ruleConditions == null) {
			formCounterRuleImpl.setRuleConditions("");
		}
		else {
			formCounterRuleImpl.setRuleConditions(ruleConditions);
		}

		if (logicOperator == null) {
			formCounterRuleImpl.setLogicOperator("");
		}
		else {
			formCounterRuleImpl.setLogicOperator(logicOperator);
		}

		formCounterRuleImpl.setActive(active);
		formCounterRuleImpl.setCompanyId(companyId);
		formCounterRuleImpl.setGroupId(groupId);
		formCounterRuleImpl.setUserId(userId);

		if (userName == null) {
			formCounterRuleImpl.setUserName("");
		}
		else {
			formCounterRuleImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			formCounterRuleImpl.setCreateDate(null);
		}
		else {
			formCounterRuleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			formCounterRuleImpl.setModifiedDate(null);
		}
		else {
			formCounterRuleImpl.setModifiedDate(new Date(modifiedDate));
		}

		formCounterRuleImpl.resetOriginalValues();

		return formCounterRuleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		formCounterRuleId = objectInput.readLong();
		ruleName = objectInput.readUTF();
		description = objectInput.readUTF();
		ruleConditions = objectInput.readUTF();
		logicOperator = objectInput.readUTF();

		active = objectInput.readBoolean();

		companyId = objectInput.readLong();

		groupId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(formCounterRuleId);

		if (ruleName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(ruleName);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (ruleConditions == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(ruleConditions);
		}

		if (logicOperator == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(logicOperator);
		}

		objectOutput.writeBoolean(active);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
	}

	public long formCounterRuleId;
	public String ruleName;
	public String description;
	public String ruleConditions;
	public String logicOperator;
	public boolean active;
	public long companyId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;

}