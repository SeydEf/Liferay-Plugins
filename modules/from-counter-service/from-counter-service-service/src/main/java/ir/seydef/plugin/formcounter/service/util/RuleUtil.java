/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.util;

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
public class RuleUtil {

    private static final Log _log = LogFactoryUtil.getLog(RuleUtil.class);

    /**
     * Extract condition fields from rule JSON
     * 
     * @param ruleConditions JSON string of rule conditions
     * @return list of field names
     */
    public static List<String> extractFieldNames(String ruleConditions) {
        List<String> fieldNames = new ArrayList<>();

        if (Validator.isNull(ruleConditions)) {
            return fieldNames;
        }

        try {
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject(ruleConditions);
            JSONArray conditionsArray = jsonObject.getJSONArray("conditions");

            if (conditionsArray != null) {
                for (int i = 0; i < conditionsArray.length(); i++) {
                    JSONObject condition = conditionsArray.getJSONObject(i);
                    String fieldName = condition.getString("field");

                    if (Validator.isNotNull(fieldName)) {
                        fieldNames.add(fieldName);
                    }
                }
            }
        } catch (JSONException e) {
            _log.error("Error parsing rule conditions JSON", e);
        }

        return fieldNames;
    }

    /**
     * Extract reference fields from rule JSON
     * 
     * @param ruleConditions JSON string of rule conditions
     * @return list of reference field names
     */
    public static List<String> extractReferenceNames(String ruleConditions) {
        List<String> referenceNames = new ArrayList<>();

        if (Validator.isNull(ruleConditions)) {
            return referenceNames;
        }

        try {
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject(ruleConditions);
            JSONArray conditionsArray = jsonObject.getJSONArray("conditions");

            if (conditionsArray != null) {
                for (int i = 0; i < conditionsArray.length(); i++) {
                    JSONObject condition = conditionsArray.getJSONObject(i);
                    String referenceName = condition.getString("reference");

                    if (Validator.isNotNull(referenceName)) {
                        referenceNames.add(referenceName);
                    }
                }
            }
        } catch (JSONException e) {
            _log.error("Error parsing rule conditions JSON", e);
        }

        return referenceNames;
    }

    /**
     * Check if a value meets a condition based on operator
     * 
     * @param value     the value to check
     * @param operator  the operator (contains, equal, not-equal)
     * @param reference the reference value
     * @return true if the condition is met, false otherwise
     */
    public static boolean checkCondition(String value, String operator, String reference) {
        if (value == null) {
            value = "";
        }

        if (reference == null) {
            reference = "";
        }

        switch (operator) {
            case "contains":
                return value.contains(reference);
            case "equal":
                return value.equals(reference);
            case "not-equal":
                return !value.equals(reference);
            default:
                return false;
        }
    }
}