/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing FormSubmissionStatus in entity cache.
 *
 * @author S.Abolfazl Eftekhari
 * @generated
 */
public class FormSubmissionStatusCacheModel
	implements CacheModel<FormSubmissionStatus>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormSubmissionStatusCacheModel)) {
			return false;
		}

		FormSubmissionStatusCacheModel formSubmissionStatusCacheModel =
			(FormSubmissionStatusCacheModel)object;

		if (formSubmissionStatusId ==
				formSubmissionStatusCacheModel.formSubmissionStatusId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, formSubmissionStatusId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{formSubmissionStatusId=");
		sb.append(formSubmissionStatusId);
		sb.append(", formInstanceRecordId=");
		sb.append(formInstanceRecordId);
		sb.append(", seen=");
		sb.append(seen);
		sb.append(", seenDate=");
		sb.append(seenDate);
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
	public FormSubmissionStatus toEntityModel() {
		FormSubmissionStatusImpl formSubmissionStatusImpl =
			new FormSubmissionStatusImpl();

		formSubmissionStatusImpl.setFormSubmissionStatusId(
			formSubmissionStatusId);
		formSubmissionStatusImpl.setFormInstanceRecordId(formInstanceRecordId);
		formSubmissionStatusImpl.setSeen(seen);

		if (seenDate == Long.MIN_VALUE) {
			formSubmissionStatusImpl.setSeenDate(null);
		}
		else {
			formSubmissionStatusImpl.setSeenDate(new Date(seenDate));
		}

		formSubmissionStatusImpl.setCompanyId(companyId);
		formSubmissionStatusImpl.setGroupId(groupId);
		formSubmissionStatusImpl.setUserId(userId);

		if (userName == null) {
			formSubmissionStatusImpl.setUserName("");
		}
		else {
			formSubmissionStatusImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			formSubmissionStatusImpl.setCreateDate(null);
		}
		else {
			formSubmissionStatusImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			formSubmissionStatusImpl.setModifiedDate(null);
		}
		else {
			formSubmissionStatusImpl.setModifiedDate(new Date(modifiedDate));
		}

		formSubmissionStatusImpl.resetOriginalValues();

		return formSubmissionStatusImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		formSubmissionStatusId = objectInput.readLong();

		formInstanceRecordId = objectInput.readLong();

		seen = objectInput.readBoolean();
		seenDate = objectInput.readLong();

		companyId = objectInput.readLong();

		groupId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(formSubmissionStatusId);

		objectOutput.writeLong(formInstanceRecordId);

		objectOutput.writeBoolean(seen);
		objectOutput.writeLong(seenDate);

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

	public long formSubmissionStatusId;
	public long formInstanceRecordId;
	public boolean seen;
	public long seenDate;
	public long companyId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;

}