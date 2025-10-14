package ir.seydef.plugin.formcounter.rules.admin.model;

/**
 * @author S.Abolfazl Eftekhari
 */
public class ExpandoFieldInfo {
    private String name;
    private String displayName;
    private String type;

    public ExpandoFieldInfo() {
    }

    public ExpandoFieldInfo(String name, String displayName, String type) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}