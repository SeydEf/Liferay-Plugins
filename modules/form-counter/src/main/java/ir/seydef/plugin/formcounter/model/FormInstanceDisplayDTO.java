package ir.seydef.plugin.formcounter.model;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;

import java.util.Locale;

/**
 * @author S.Abolfazl Eftekhari
 */
public class FormInstanceDisplayDTO {

	public FormInstanceDisplayDTO() {
	}

	public FormInstanceDisplayDTO(DDMFormInstance formInstance, Locale locale) {
		_formInstanceId = formInstance.getFormInstanceId();
		_name = formInstance.getName(locale);
		_description = formInstance.getDescription(locale);
		_recordCount = 0;
	}

	public String getDescription() {
		return _description;
	}

	public String getDisplayName() {
		if (_name != null) {
			return _name;
		}

		return "Form #" + _formInstanceId;
	}

	public long getFormInstanceId() {
		return _formInstanceId;
	}

	public String getName() {
		return _name;
	}

	public int getRecordCount() {
		return _recordCount;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setFormInstanceId(long formInstanceId) {
		_formInstanceId = formInstanceId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRecordCount(int recordCount) {
		_recordCount = recordCount;
	}

	@Override
	public String toString() {
		return "FormInstanceDisplayDTO{formInstanceId=" + _formInstanceId +
			", name='" + _name + '\'' + ", recordCount=" + _recordCount + '}';
	}

	private String _description;
	private long _formInstanceId;
	private String _name;
	private int _recordCount;

}