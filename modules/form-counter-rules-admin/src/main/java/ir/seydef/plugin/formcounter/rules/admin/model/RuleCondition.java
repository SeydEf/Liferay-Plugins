package ir.seydef.plugin.formcounter.rules.admin.model;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author S.Abolfazl Eftekhari
 */
public class RuleCondition {

	public static RuleCondition fromJSONObject(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		RuleCondition condition = new RuleCondition();

		condition.setField(jsonObject.getString("field"));
		condition.setOperator(jsonObject.getString("operator"));
		condition.setReference(jsonObject.getString("reference"));

		return condition;
	}

	public RuleCondition() {
	}

	public RuleCondition(String field, String operator, String reference) {
		_field = field;
		_operator = operator;
		_reference = reference;
	}

	public String getField() {
		return _field;
	}

	public String getOperator() {
		return _operator;
	}

	public String getReference() {
		return _reference;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setOperator(String operator) {
		_operator = operator;
	}

	public void setReference(String reference) {
		_reference = reference;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"field", _field
		).put(
			"operator", _operator
		).put(
			"reference", _reference
		);
	}

	private String _field;
	private String _operator;
	private String _reference;

}