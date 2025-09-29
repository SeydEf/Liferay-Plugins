package ir.seydef.plugin.formcounter.model;

import java.util.Date;

/**
 * @author S.Abolfazl Eftekhari
 */
public class SearchCriteria {
    private String registrantName;
    private String formNumber;
    private String trackingCode;
    private String formName;
    private Date startDate;
    private Date endDate;
    private String status;
    private long formInstanceId;
    private String userBranchId;

    public SearchCriteria() {
    }

    public String getRegistrantName() {
        return registrantName;
    }

    public void setRegistrantName(String registrantName) {
        this.registrantName = registrantName;
    }

    public String getFormNumber() {
        return formNumber;
    }

    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getFormInstanceId() {
        return formInstanceId;
    }

    public void setFormInstanceId(long formInstanceId) {
        this.formInstanceId = formInstanceId;
    }

    public String getUserBranchId() {
        return userBranchId;
    }

    public void setUserBranchId(String userBranchId) {
        this.userBranchId = userBranchId;
    }

    public boolean hasSearchCriteria() {
        return registrantName != null || formNumber != null || trackingCode != null ||
               formName != null || startDate != null || endDate != null || status != null;
    }
}
