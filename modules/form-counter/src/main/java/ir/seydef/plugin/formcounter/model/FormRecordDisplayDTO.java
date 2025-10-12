package ir.seydef.plugin.formcounter.model;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.util.Validator;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;
import ir.seydef.plugin.formcounter.helper.DDMFormService;

import java.util.Date;
import java.util.Locale;

/**
 * @author S.Abolfazl Eftekhari
 */
public class FormRecordDisplayDTO {

    private long recordId;
    private long formInstanceId;
    private String formInstanceName;
    private String branchId;
    private Date createDate;
    private Date modifiedDate;
    private String userName;
    private String submitterName;
    private boolean seen;
    private String statusLabel;
    private Date seenDate;

    public FormRecordDisplayDTO() {
    }

    public FormRecordDisplayDTO(DDMFormInstanceRecord record, DDMFormInstance formInstance,
            String branchId, Locale locale) {
        this.recordId = record.getFormInstanceRecordId();
        this.formInstanceId = record.getFormInstanceId();
        this.formInstanceName = formInstance != null ? formInstance.getName(locale) : "";
        this.branchId = branchId;
        this.createDate = record.getCreateDate();
        this.modifiedDate = record.getModifiedDate();
        this.userName = record.getUserName();

        // Extract submitter name from form fields
        this.submitterName = DDMFormService.extractSubmitterNameFromRecord(record);

        try {
            FormSubmissionStatus submissionStatus = FormSubmissionStatusLocalServiceUtil
                    .getByFormInstanceRecordId(record.getFormInstanceRecordId());
            if (submissionStatus != null) {
                this.seen = submissionStatus.isSeen();
                this.seenDate = submissionStatus.getSeenDate();
                this.statusLabel = submissionStatus.isSeen() ? "seen" : "unseen";
            } else {
                this.seen = false;
                this.seenDate = null;
                this.statusLabel = "unseen";
            }
        } catch (Exception e) {
            this.seen = false;
            this.seenDate = null;
            this.statusLabel = "unseen";
        }
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public long getFormInstanceId() {
        return formInstanceId;
    }

    public void setFormInstanceId(long formInstanceId) {
        this.formInstanceId = formInstanceId;
    }

    public String getFormInstanceName() {
        return formInstanceName;
    }

    public void setFormInstanceName(String formInstanceName) {
        this.formInstanceName = formInstanceName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Date getSeenDate() {
        return seenDate;
    }

    public void setSeenDate(Date seenDate) {
        this.seenDate = seenDate;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public boolean hasValidBranchId() {
        return Validator.isNotNull(branchId);
    }

    public String getFormattedCreateDate() {
        if (createDate != null) {
            return createDate.toString();
        }
        return "";
    }

    @Override
    public String toString() {
        return "FormRecordDisplayDTO{" +
                "recordId=" + recordId +
                ", formInstanceId=" + formInstanceId +
                ", formInstanceName='" + formInstanceName + '\'' +
                ", branchId='" + branchId + '\'' +
                ", createDate=" + createDate +
                ", userName='" + userName + '\'' +
                ", seen=" + seen +
                ", statusLabel='" + statusLabel + '\'' +
                '}';
    }
}