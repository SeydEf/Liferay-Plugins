package ir.seydef.plugin.formcounter.rules.admin.model;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author S.Abolfazl Eftekhari
 */
public class Rule {

	public static Rule fromJSONString(String jsonString) {
		if (Validator.isNull(jsonString)) {
			return null;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				jsonString);
			Rule rule = new Rule();

			rule.setLogicOperator(jsonObject.getString("logic"));

			JSONArray conditionsArray = jsonObject.getJSONArray("conditions");

			if (conditionsArray != null) {
				List<RuleCondition> conditions = new ArrayList<>();

				for (int i = 0; i < conditionsArray.length(); i++) {
					JSONObject conditionObj = conditionsArray.getJSONObject(i);

					RuleCondition condition = RuleCondition.fromJSONObject(
						conditionObj);

					if (condition != null) {
						conditions.add(condition);
					}
				}

				rule.setConditions(conditions);
			}

			return rule;
		}
		catch (JSONException jsonException) {
			_log.error("Error parsing JSON string to Rule", jsonException);

			return null;
		}
	}

	public Rule() {
		_conditions = new ArrayList<>();
	}

	public void addCondition(RuleCondition condition) {
		if (_conditions == null) {
			_conditions = new ArrayList<>();
		}

		_conditions.add(condition);
	}

	public List<RuleCondition> getConditions() {
		return _conditions;
	}

	public String getDescription() {
		return _description;
	}

	public String getLogicOperator() {
		return _logicOperator;
	}

	public String getName() {
		return _name;
	}

	public long getRuleId() {
		return _ruleId;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setConditions(List<RuleCondition> conditions) {
		_conditions = conditions;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setLogicOperator(String logicOperator) {
		_logicOperator = logicOperator;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRuleId(long ruleId) {
		_ruleId = ruleId;
	}

	public String toJSONString() {
		try {
			JSONArray conditionsArray = JSONFactoryUtil.createJSONArray();

			for (RuleCondition condition : _conditions) {
				conditionsArray.put(condition.toJSONObject());
			}

			return JSONUtil.put(
				"conditions", conditionsArray
			).put(
				"logic", _logicOperator
			).toString();
		}
		catch (Exception exception) {
			_log.error("Error converting rule to JSON string", exception);

			return "{}";
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(Rule.class);

	private boolean _active;
	private List<RuleCondition> _conditions;
	private String _description;
	private String _logicOperator;
	private String _name;
	private long _ruleId;

}