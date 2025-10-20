package ir.seydef.plugin.formcounter.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author S.Abolfazl Eftekhari
 */
public class SearchCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private String registrantName;
    private String formNumber;
    private String trackingCode;
    private String formName;
    private Date startDate;
    private Date endDate;
    private String status;
    private long formInstanceId;
    private Map<String, List<String>> userCustomFields;

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

    public Map<String, List<String>> getUserCustomFields() {
        return userCustomFields;
    }

    public void setUserCustomFields(Map<String, List<String>> userCustomFields) {
        this.userCustomFields = userCustomFields;
    }

    public boolean hasSearchCriteria() {
        return registrantName != null || formNumber != null || trackingCode != null ||
                formName != null || startDate != null || endDate != null || status != null;
    }
}