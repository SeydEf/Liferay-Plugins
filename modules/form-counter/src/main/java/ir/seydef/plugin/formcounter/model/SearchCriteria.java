package ir.seydef.plugin.formcounter.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author S.Abolfazl Eftekhari
 */
public class SearchCriteria implements Serializable {

	public SearchCriteria() {
		_dynamicFilters = new ArrayList<>();
	}

	public void addDynamicFilter(DynamicFilter filter) {
		_dynamicFilters.add(filter);
	}

	public List<DynamicFilter> getDynamicFilters() {
		return _dynamicFilters;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public long getFormInstanceId() {
		return _formInstanceId;
	}

	public String getFormName() {
		return _formName;
	}

	public String getFormNumber() {
		return _formNumber;
	}

	public String getRegistrantName() {
		return _registrantName;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public String getStatus() {
		return _status;
	}

	public String getTrackingCode() {
		return _trackingCode;
	}

	public Map<String, List<String>> getUserCustomFields() {
		return _userCustomFields;
	}

	public boolean hasSearchCriteria() {
		if ((_registrantName != null) || (_formNumber != null) ||
			(_trackingCode != null) || (_formName != null) ||
			(_startDate != null) || (_endDate != null) || (_status != null) ||
			((_dynamicFilters != null) && !_dynamicFilters.isEmpty())) {

			return true;
		}

		return false;
	}

	public void setDynamicFilters(List<DynamicFilter> dynamicFilters) {
		_dynamicFilters = dynamicFilters;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public void setFormInstanceId(long formInstanceId) {
		_formInstanceId = formInstanceId;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setFormNumber(String formNumber) {
		_formNumber = formNumber;
	}

	public void setRegistrantName(String registrantName) {
		_registrantName = registrantName;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public void setTrackingCode(String trackingCode) {
		_trackingCode = trackingCode;
	}

	public void setUserCustomFields(
		Map<String, List<String>> userCustomFields) {

		_userCustomFields = userCustomFields;
	}

	public static class DynamicFilter implements Serializable {

		public DynamicFilter() {
		}

		public DynamicFilter(
			String fieldName, String fieldType, String fieldValue) {

			_fieldName = fieldName;
			_fieldType = fieldType;
			_fieldValue = fieldValue;
		}

		public String getFieldName() {
			return _fieldName;
		}

		public String getFieldType() {
			return _fieldType;
		}

		public String getFieldValue() {
			return _fieldValue;
		}

		public void setFieldName(String fieldName) {
			_fieldName = fieldName;
		}

		public void setFieldType(String fieldType) {
			_fieldType = fieldType;
		}

		public void setFieldValue(String fieldValue) {
			_fieldValue = fieldValue;
		}

		private static final long serialVersionUID = 1L;

		private String _fieldName;
		private String _fieldType;
		private String _fieldValue;

	}

	private static final long serialVersionUID = 1L;

	private List<DynamicFilter> _dynamicFilters;
	private Date _endDate;
	private long _formInstanceId;
	private String _formName;
	private String _formNumber;
	private String _registrantName;
	private Date _startDate;
	private String _status;
	private String _trackingCode;
	private Map<String, List<String>> _userCustomFields;

}