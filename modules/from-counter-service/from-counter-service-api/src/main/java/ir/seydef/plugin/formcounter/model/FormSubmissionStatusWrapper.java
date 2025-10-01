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
 * This class is a wrapper for {@link FormSubmissionStatus}.
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatus
 * @generated
 */
public class FormSubmissionStatusWrapper
	extends BaseModelWrapper<FormSubmissionStatus>
	implements FormSubmissionStatus, ModelWrapper<FormSubmissionStatus> {

	public FormSubmissionStatusWrapper(
		FormSubmissionStatus formSubmissionStatus) {

		super(formSubmissionStatus);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("formSubmissionStatusId", getFormSubmissionStatusId());
		attributes.put("formInstanceRecordId", getFormInstanceRecordId());
		attributes.put("seen", isSeen());
		attributes.put("seenDate", getSeenDate());
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
		Long formSubmissionStatusId = (Long)attributes.get(
			"formSubmissionStatusId");

		if (formSubmissionStatusId != null) {
			setFormSubmissionStatusId(formSubmissionStatusId);
		}

		Long formInstanceRecordId = (Long)attributes.get(
			"formInstanceRecordId");

		if (formInstanceRecordId != null) {
			setFormInstanceRecordId(formInstanceRecordId);
		}

		Boolean seen = (Boolean)attributes.get("seen");

		if (seen != null) {
			setSeen(seen);
		}

		Date seenDate = (Date)attributes.get("seenDate");

		if (seenDate != null) {
			setSeenDate(seenDate);
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
	 * Returns the company ID of this form submission status.
	 *
	 * @return the company ID of this form submission status
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this form submission status.
	 *
	 * @return the create date of this form submission status
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the form instance record ID of this form submission status.
	 *
	 * @return the form instance record ID of this form submission status
	 */
	@Override
	public long getFormInstanceRecordId() {
		return model.getFormInstanceRecordId();
	}

	/**
	 * Returns the form submission status ID of this form submission status.
	 *
	 * @return the form submission status ID of this form submission status
	 */
	@Override
	public long getFormSubmissionStatusId() {
		return model.getFormSubmissionStatusId();
	}

	/**
	 * Returns the group ID of this form submission status.
	 *
	 * @return the group ID of this form submission status
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this form submission status.
	 *
	 * @return the modified date of this form submission status
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this form submission status.
	 *
	 * @return the primary key of this form submission status
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the seen of this form submission status.
	 *
	 * @return the seen of this form submission status
	 */
	@Override
	public boolean getSeen() {
		return model.getSeen();
	}

	/**
	 * Returns the seen date of this form submission status.
	 *
	 * @return the seen date of this form submission status
	 */
	@Override
	public Date getSeenDate() {
		return model.getSeenDate();
	}

	/**
	 * Returns the user ID of this form submission status.
	 *
	 * @return the user ID of this form submission status
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this form submission status.
	 *
	 * @return the user name of this form submission status
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this form submission status.
	 *
	 * @return the user uuid of this form submission status
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this form submission status is seen.
	 *
	 * @return <code>true</code> if this form submission status is seen; <code>false</code> otherwise
	 */
	@Override
	public boolean isSeen() {
		return model.isSeen();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this form submission status.
	 *
	 * @param companyId the company ID of this form submission status
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this form submission status.
	 *
	 * @param createDate the create date of this form submission status
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the form instance record ID of this form submission status.
	 *
	 * @param formInstanceRecordId the form instance record ID of this form submission status
	 */
	@Override
	public void setFormInstanceRecordId(long formInstanceRecordId) {
		model.setFormInstanceRecordId(formInstanceRecordId);
	}

	/**
	 * Sets the form submission status ID of this form submission status.
	 *
	 * @param formSubmissionStatusId the form submission status ID of this form submission status
	 */
	@Override
	public void setFormSubmissionStatusId(long formSubmissionStatusId) {
		model.setFormSubmissionStatusId(formSubmissionStatusId);
	}

	/**
	 * Sets the group ID of this form submission status.
	 *
	 * @param groupId the group ID of this form submission status
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this form submission status.
	 *
	 * @param modifiedDate the modified date of this form submission status
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this form submission status.
	 *
	 * @param primaryKey the primary key of this form submission status
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this form submission status is seen.
	 *
	 * @param seen the seen of this form submission status
	 */
	@Override
	public void setSeen(boolean seen) {
		model.setSeen(seen);
	}

	/**
	 * Sets the seen date of this form submission status.
	 *
	 * @param seenDate the seen date of this form submission status
	 */
	@Override
	public void setSeenDate(Date seenDate) {
		model.setSeenDate(seenDate);
	}

	/**
	 * Sets the user ID of this form submission status.
	 *
	 * @param userId the user ID of this form submission status
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this form submission status.
	 *
	 * @param userName the user name of this form submission status
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this form submission status.
	 *
	 * @param userUuid the user uuid of this form submission status
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected FormSubmissionStatusWrapper wrap(
		FormSubmissionStatus formSubmissionStatus) {

		return new FormSubmissionStatusWrapper(formSubmissionStatus);
	}

}