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
 * This class is used by SOAP remote services, specifically {@link ir.seydef.plugin.formcounter.service.http.FormSubmissionStatusServiceSoap}.
 *
 * @author S.Abolfazl Eftekhari
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FormSubmissionStatusSoap implements Serializable {

	public static FormSubmissionStatusSoap toSoapModel(
		FormSubmissionStatus model) {

		FormSubmissionStatusSoap soapModel = new FormSubmissionStatusSoap();

		soapModel.setFormSubmissionStatusId(model.getFormSubmissionStatusId());
		soapModel.setFormInstanceRecordId(model.getFormInstanceRecordId());
		soapModel.setSeen(model.isSeen());
		soapModel.setSeenDate(model.getSeenDate());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());

		return soapModel;
	}

	public static FormSubmissionStatusSoap[] toSoapModels(
		FormSubmissionStatus[] models) {

		FormSubmissionStatusSoap[] soapModels =
			new FormSubmissionStatusSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FormSubmissionStatusSoap[][] toSoapModels(
		FormSubmissionStatus[][] models) {

		FormSubmissionStatusSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FormSubmissionStatusSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FormSubmissionStatusSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FormSubmissionStatusSoap[] toSoapModels(
		List<FormSubmissionStatus> models) {

		List<FormSubmissionStatusSoap> soapModels =
			new ArrayList<FormSubmissionStatusSoap>(models.size());

		for (FormSubmissionStatus model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new FormSubmissionStatusSoap[soapModels.size()]);
	}

	public FormSubmissionStatusSoap() {
	}

	public long getPrimaryKey() {
		return _formSubmissionStatusId;
	}

	public void setPrimaryKey(long pk) {
		setFormSubmissionStatusId(pk);
	}

	public long getFormSubmissionStatusId() {
		return _formSubmissionStatusId;
	}

	public void setFormSubmissionStatusId(long formSubmissionStatusId) {
		_formSubmissionStatusId = formSubmissionStatusId;
	}

	public long getFormInstanceRecordId() {
		return _formInstanceRecordId;
	}

	public void setFormInstanceRecordId(long formInstanceRecordId) {
		_formInstanceRecordId = formInstanceRecordId;
	}

	public boolean getSeen() {
		return _seen;
	}

	public boolean isSeen() {
		return _seen;
	}

	public void setSeen(boolean seen) {
		_seen = seen;
	}

	public Date getSeenDate() {
		return _seenDate;
	}

	public void setSeenDate(Date seenDate) {
		_seenDate = seenDate;
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

	private long _formSubmissionStatusId;
	private long _formInstanceRecordId;
	private boolean _seen;
	private Date _seenDate;
	private long _companyId;
	private long _groupId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;

}