package ir.seydef.plugin.formcounter.model;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;

import ir.seydef.plugin.formcounter.helper.DDMFormService;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;

import java.util.Date;
import java.util.Locale;

/**
 * @author S.Abolfazl Eftekhari
 */
public class FormRecordDisplayDTO {

	public FormRecordDisplayDTO() {
	}

	public FormRecordDisplayDTO(
		DDMFormInstanceRecord record, DDMFormInstance formInstance,
		Locale locale) {

		_recordId = record.getFormInstanceRecordId();
		_formInstanceId = record.getFormInstanceId();
		_formInstanceName =
			(formInstance != null) ? formInstance.getName(locale) : "";
		_createDate = record.getCreateDate();
		_modifiedDate = record.getModifiedDate();
		_userName = record.getUserName();

		_submitterName = DDMFormService.extractSubmitterNameFromRecord(record);

		try {
			FormSubmissionStatus submissionStatus =
				FormSubmissionStatusLocalServiceUtil.getByFormInstanceRecordId(
					record.getFormInstanceRecordId());

			if (submissionStatus != null) {
				_seen = submissionStatus.isSeen();
				_seenDate = submissionStatus.getSeenDate();
				_statusLabel = submissionStatus.isSeen() ? "seen" : "unseen";
			}
			else {
				_seen = false;
				_seenDate = null;
				_statusLabel = "unseen";
			}
		}
		catch (Exception exception) {
			_seen = false;
			_seenDate = null;
			_statusLabel = "unseen";
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getFormattedCreateDate() {
		if (_createDate != null) {
			return _createDate.toString();
		}

		return "";
	}

	public long getFormInstanceId() {
		return _formInstanceId;
	}

	public String getFormInstanceName() {
		return _formInstanceName;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public long getRecordId() {
		return _recordId;
	}

	public Date getSeenDate() {
		return _seenDate;
	}

	public String getStatusLabel() {
		return _statusLabel;
	}

	public String getSubmitterName() {
		return _submitterName;
	}

	public String getUserName() {
		return _userName;
	}

	public boolean isSeen() {
		return _seen;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setFormInstanceId(long formInstanceId) {
		_formInstanceId = formInstanceId;
	}

	public void setFormInstanceName(String formInstanceName) {
		_formInstanceName = formInstanceName;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setRecordId(long recordId) {
		_recordId = recordId;
	}

	public void setSeen(boolean seen) {
		_seen = seen;
	}

	public void setSeenDate(Date seenDate) {
		_seenDate = seenDate;
	}

	public void setStatusLabel(String statusLabel) {
		_statusLabel = statusLabel;
	}

	public void setSubmitterName(String submitterName) {
		_submitterName = submitterName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public String toString() {
		return "FormRecordDisplayDTO{recordId=" + _recordId +
			", formInstanceId=" + _formInstanceId + ", formInstanceName='" +
				_formInstanceName + '\'' + ", createDate=" + _createDate +
					", userName='" + _userName + '\'' + ", seen=" + _seen +
						", statusLabel='" + _statusLabel + '\'' + '}';
	}

	private Date _createDate;
	private long _formInstanceId;
	private String _formInstanceName;
	private Date _modifiedDate;
	private long _recordId;
	private boolean _seen;
	private Date _seenDate;
	private String _statusLabel;
	private String _submitterName;
	private String _userName;

}