package ir.seydef.plugin.formcounter.rules.admin.model;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author S.Abolfazl Eftekhari
 */
public class RuleCondition {
    private static final Log _log = LogFactoryUtil.getLog(RuleCondition.class);

    private String field;
    private String operator;
    private String reference;

    public RuleCondition() {
    }

    public RuleCondition(String field, String operator, String reference) {
        this.field = field;
        this.operator = operator;
        this.reference = reference;
    }

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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
        jsonObject.put("field", field);
        jsonObject.put("operator", operator);
        jsonObject.put("reference", reference);
        return jsonObject;
    }
}