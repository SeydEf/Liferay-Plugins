package ir.seydef.plugin.formcounter.model;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.util.Validator;

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
    private int status;
    private String statusLabel;

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

        try {
            this.status = record.getStatus();
            this.statusLabel = getStatusLabel(record.getStatus());
        } catch (Exception e) {
            this.status = 0;
            this.statusLabel = "Unknown";
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    private String getStatusLabel(int status) {
        switch (status) {
            case 0:
                return "Approved";
            case 1:
                return "Draft";
            default:
                return "Unknown";
        }
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
                ", status=" + status +
                '}';
    }
}