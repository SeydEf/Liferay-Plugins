package ir.seydef.plugin.formcounter.rules.admin.model;

/**
 * @author S.Abolfazl Eftekhari
 */
public class ExpandoFieldInfo {

	public ExpandoFieldInfo() {
	}

	public ExpandoFieldInfo(String name, String displayName, String type) {
		_name = name;
		_displayName = displayName;
		_type = type;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public void setDisplayName(String displayName) {
		_displayName = displayName;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _displayName;
	private String _name;
	private String _type;

}