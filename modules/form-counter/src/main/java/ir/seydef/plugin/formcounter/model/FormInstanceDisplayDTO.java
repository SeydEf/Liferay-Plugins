package ir.seydef.plugin.formcounter.model;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;

import java.util.Locale;

/**
 * @author S.Abolfazl Eftekhari
 */
public class FormInstanceDisplayDTO {

    private long formInstanceId;
    private String name;
    private String description;
    private boolean hasBranchIdField;
    private int recordCount;

    public FormInstanceDisplayDTO() {
    }

    public FormInstanceDisplayDTO(DDMFormInstance formInstance, Locale locale, boolean hasBranchIdField) {
        this.formInstanceId = formInstance.getFormInstanceId();
        this.name = formInstance.getName(locale);
        this.description = formInstance.getDescription(locale);
        this.hasBranchIdField = hasBranchIdField;
        this.recordCount = 0;
    }

    public long getFormInstanceId() {
        return formInstanceId;
    }

    public void setFormInstanceId(long formInstanceId) {
        this.formInstanceId = formInstanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasBranchIdField() {
        return hasBranchIdField;
    }

    public void setHasBranchIdField(boolean hasBranchIdField) {
        this.hasBranchIdField = hasBranchIdField;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public String getDisplayName() {
        return name != null ? name : "Form #" + formInstanceId;
    }

    @Override
    public String toString() {
        return "FormInstanceDisplayDTO{" +
                "formInstanceId=" + formInstanceId +
                ", name='" + name + '\'' +
                ", hasBranchIdField=" + hasBranchIdField +
                ", recordCount=" + recordCount +
                '}';
    }
}