package ir.seydef.plugin.formcounter.rules.admin.model;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author S.Abolfazl Eftekhari
 */
public class Rule {
    private static final Log _log = LogFactoryUtil.getLog(Rule.class);

    private long ruleId;
    private String name;
    private String description;
    private List<RuleCondition> conditions;
    private String logicOperator;
    private boolean active;

    public Rule() {
        this.conditions = new ArrayList<>();
    }

    public static Rule fromJSONString(String jsonString) {
        if (Validator.isNull(jsonString)) {
            return null;
        }

        try {
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonString);
            Rule rule = new Rule();

            rule.setLogicOperator(jsonObject.getString("logic"));

            JSONArray conditionsArray = jsonObject.getJSONArray("conditions");
            if (conditionsArray != null) {
                List<RuleCondition> conditions = new ArrayList<>();

                for (int i = 0; i < conditionsArray.length(); i++) {
                    JSONObject conditionObj = conditionsArray.getJSONObject(i);
                    RuleCondition condition = RuleCondition.fromJSONObject(conditionObj);
                    if (condition != null) {
                        conditions.add(condition);
                    }
                }

                rule.setConditions(conditions);
            }

            return rule;
        } catch (JSONException e) {
            _log.error("Error parsing JSON string to Rule", e);
            return null;
        }
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
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

    public List<RuleCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<RuleCondition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(RuleCondition condition) {
        if (this.conditions == null) {
            this.conditions = new ArrayList<>();
        }
        this.conditions.add(condition);
    }

    public String getLogicOperator() {
        return logicOperator;
    }

    public void setLogicOperator(String logicOperator) {
        this.logicOperator = logicOperator;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String toJSONString() {
        try {
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
            JSONArray conditionsArray = JSONFactoryUtil.createJSONArray();

            for (RuleCondition condition : conditions) {
                conditionsArray.put(condition.toJSONObject());
            }

            jsonObject.put("conditions", conditionsArray);
            jsonObject.put("logic", logicOperator);

            return jsonObject.toString();
        } catch (Exception e) {
            _log.error("Error converting rule to JSON string", e);
            return "{}";
        }
    }
}