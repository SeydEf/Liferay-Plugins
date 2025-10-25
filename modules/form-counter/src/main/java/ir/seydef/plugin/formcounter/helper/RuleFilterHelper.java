package ir.seydef.plugin.formcounter.helper;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import ir.seydef.plugin.formcounter.model.FormCounterRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author S.Abolfazl Eftekhari
 */
public class RuleFilterHelper {

	public static String getOperatorForCondition(
		FormCounterRule rule, String customFieldName, String referenceField) {

		try {
			if ((rule == null) || Validator.isNull(customFieldName) ||
				Validator.isNull(referenceField)) {

				return "contains";
			}

			String ruleConditions = rule.getRuleConditions();

			if (Validator.isNull(ruleConditions)) {
				return "contains";
			}

			JSONObject jsonConditions = JSONFactoryUtil.createJSONObject(
				ruleConditions);

			JSONArray conditions = jsonConditions.getJSONArray("conditions");

			if (conditions != null) {
				for (int i = 0; i < conditions.length(); i++) {
					JSONObject condition = conditions.getJSONObject(i);

					String field = condition.getString("field");
					String reference = condition.getString("reference");

					if (customFieldName.equalsIgnoreCase(field) &&
						referenceField.equalsIgnoreCase(reference)) {

						String operator = condition.getString("operator");

						if (Validator.isNotNull(operator)) {
							return operator;
						}

						return "contains";
					}
				}
			}
		}
		catch (Exception exception) {
			_log.warn(
				"Error getting operator for custom field: " + customFieldName +
					", reference: " + referenceField,
				exception);
		}

		return "contains";
	}

	public static Set<String> getReferenceFieldsForCustomField(
		List<FormCounterRule> rules, String customFieldName) {

		Set<String> referenceFields = new HashSet<>();

		if (ListUtil.isEmpty(rules) || Validator.isNull(customFieldName)) {
			return referenceFields;
		}

		for (FormCounterRule rule : rules) {
			try {
				if (!rule.isActive()) {
					continue;
				}

				String ruleConditions = rule.getRuleConditions();

				if (Validator.isNull(ruleConditions)) {
					continue;
				}

				JSONObject jsonConditions = JSONFactoryUtil.createJSONObject(
					ruleConditions);

				JSONArray conditions = jsonConditions.getJSONArray(
					"conditions");

				if (conditions != null) {
					for (int i = 0; i < conditions.length(); i++) {
						JSONObject condition = conditions.getJSONObject(i);

						String field = condition.getString("field");
						String reference = condition.getString("reference");

						if (customFieldName.equalsIgnoreCase(field) &&
							Validator.isNotNull(reference)) {

							referenceFields.add(reference);
						}
					}
				}
			}
			catch (Exception exception) {
				_log.warn(
					"Error parsing rule conditions for rule: " +
						rule.getFormCounterRuleId(),
					exception);
			}
		}

		return referenceFields;
	}

	public static Map<String, Set<String>> getReferenceFieldsForCustomFields(
		List<FormCounterRule> rules, Set<String> customFieldNames) {

		Map<String, Set<String>> referenceFieldsMap = new HashMap<>();

		if ((customFieldNames == null) || customFieldNames.isEmpty()) {
			return referenceFieldsMap;
		}

		for (String customFieldName : customFieldNames) {
			Set<String> referenceFields = getReferenceFieldsForCustomField(
				rules, customFieldName);

			if (!referenceFields.isEmpty()) {
				referenceFieldsMap.put(customFieldName, referenceFields);
			}
		}

		return referenceFieldsMap;
	}

	public static List<FormCounterRule> getRulesForCustomField(
		List<FormCounterRule> rules, String customFieldName) {

		List<FormCounterRule> applicableRules = new ArrayList<>();

		if (ListUtil.isEmpty(rules) || Validator.isNull(customFieldName)) {
			return applicableRules;
		}

		for (FormCounterRule rule : rules) {
			try {
				if (!rule.isActive()) {
					continue;
				}

				String ruleConditions = rule.getRuleConditions();

				if (Validator.isNull(ruleConditions)) {
					continue;
				}

				JSONObject jsonConditions = JSONFactoryUtil.createJSONObject(
					ruleConditions);

				JSONArray conditions = jsonConditions.getJSONArray(
					"conditions");

				if (conditions != null) {
					for (int i = 0; i < conditions.length(); i++) {
						JSONObject condition = conditions.getJSONObject(i);

						String field = condition.getString("field");

						if (customFieldName.equalsIgnoreCase(field)) {
							applicableRules.add(rule);

							break;
						}
					}
				}
			}
			catch (Exception exception) {
				_log.warn(
					"Error checking rule for custom field: " + customFieldName,
					exception);
			}
		}

		return applicableRules;
	}

	public static boolean matchesCondition(
		String recordFieldValue, String customFieldValue, String operator) {

		if (Validator.isNull(recordFieldValue) ||
			Validator.isNull(customFieldValue)) {

			return false;
		}

		recordFieldValue = recordFieldValue.trim(
		).toLowerCase();
		customFieldValue = customFieldValue.trim(
		).toLowerCase();

		switch (operator) {
			case "contains":
				if (recordFieldValue.contains(customFieldValue) ||
					customFieldValue.contains(recordFieldValue)) {

					return true;
				}

				return false;

			case "equal":
				return recordFieldValue.equals(customFieldValue);

			case "not-equal":
				return !recordFieldValue.equals(customFieldValue);

			default:
				_log.warn(
					"Unknown operator: " + operator +
						", defaulting to 'contains'");

				if (recordFieldValue.contains(customFieldValue) ||
					customFieldValue.contains(recordFieldValue)) {

					return true;
				}

				return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RuleFilterHelper.class);

}