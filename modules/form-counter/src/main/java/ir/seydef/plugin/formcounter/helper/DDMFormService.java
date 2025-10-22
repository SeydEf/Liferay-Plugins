package ir.seydef.plugin.formcounter.helper;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.dao.orm.*;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.model.SearchCriteria;
import ir.seydef.plugin.formcounter.service.FormCounterRuleLocalServiceUtil;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;
import ir.seydef.plugin.formcounter.util.PersianTextUtil;

import java.util.*;

/**
 * @author S.Abolfazl Eftekhari
 */
public class DDMFormService {

    private static final Log _log = LogFactoryUtil.getLog(DDMFormService.class);

    public static List<DDMFormInstance> getFormInstancesForUser(Map<String, List<String>> userCustomFields, long groupId) {
        List<DDMFormInstance> matchingFormInstances = new ArrayList<>();

        try {
            if (userCustomFields == null || userCustomFields.isEmpty()) {
                return matchingFormInstances;
            }

            List<FormCounterRule> activeRules = FormCounterRuleLocalServiceUtil.findByActive(true);
            if (activeRules.isEmpty()) {
                return matchingFormInstances;
            }

            Map<String, Set<String>> referenceFieldsMap = RuleFilterHelper.getReferenceFieldsForCustomFields(
                    activeRules, userCustomFields.keySet());

            if (referenceFieldsMap.isEmpty()) {
                return matchingFormInstances;
            }

            Set<String> allReferenceFields = new HashSet<>();
            for (Set<String> refs : referenceFieldsMap.values()) {
                allReferenceFields.addAll(refs);
            }

            List<DDMFormInstance> allFormInstances = DDMFormInstanceLocalServiceUtil.
                    getFormInstances(groupId);

            for (DDMFormInstance formInstance : allFormInstances) {
                try {
                    DDMStructure structure = formInstance.getStructure();
                    if (structure != null) {
                        if (hasAnyReferenceField(structure, allReferenceFields)) {
                            matchingFormInstances.add(formInstance);
                        }
                    }
                } catch (Exception e) {
                    _log.warn("Error checking form structure for form instance: " +
                            formInstance.getFormInstanceId(), e);
                }
            }

        } catch (Exception e) {
            _log.error("Error retrieving form instances for user custom fields", e);
        }

        return matchingFormInstances;
    }

    public static List<DDMFormInstanceRecord> getFilteredFormRecords(
            long formInstanceId, Map<String, List<String>> userCustomFields, int start, int end,
            String orderByCol, String orderByType) {

        if (userCustomFields == null || userCustomFields.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            List<Long> approvedRecordIds = getApprovedRecordIds();

            if (approvedRecordIds.isEmpty()) {
                return new ArrayList<>();
            }

            DynamicQuery dynamicQuery = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

            dynamicQuery.add(RestrictionsFactoryUtil.in("formInstanceRecordId", approvedRecordIds));

            if (formInstanceId > 0) {
                dynamicQuery.add(PropertyFactoryUtil.forName("formInstanceId").eq(formInstanceId));
            }

            if (Validator.isNotNull(orderByCol)) {
                if ("desc".equalsIgnoreCase(orderByType)) {
                    dynamicQuery.addOrder(OrderFactoryUtil.desc(orderByCol));
                } else {
                    dynamicQuery.addOrder(OrderFactoryUtil.asc(orderByCol));
                }
            }

            List<DDMFormInstanceRecord> allRecords = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery(dynamicQuery);

            List<DDMFormInstanceRecord> filteredRecords = new ArrayList<>();
            for (DDMFormInstanceRecord record : allRecords) {
                try {
                    if (matchesUserCustomFields(record, userCustomFields)) {
                        filteredRecords.add(record);
                    }
                } catch (Exception e) {
                    _log.warn("Error filtering record: " + record.getFormInstanceRecordId(), e);
                }
            }

            int fromIndex = Math.min(start, filteredRecords.size());
            int toIndex = Math.min(end, filteredRecords.size());

            return filteredRecords.subList(fromIndex, toIndex);

        } catch (Exception e) {
            _log.error("Error retrieving filtered form records", e);
            return new ArrayList<>();
        }
    }

    public static int getFilteredFormRecordsCount(long formInstanceId, Map<String, List<String>> userCustomFields) {
        if (userCustomFields == null || userCustomFields.isEmpty()) {
            return 0;
        }

        try {
            List<Long> approvedRecordIds = getApprovedRecordIds();

            if (approvedRecordIds.isEmpty()) {
                return 0;
            }

            DynamicQuery dynamicQuery = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

            dynamicQuery.add(RestrictionsFactoryUtil.in("formInstanceRecordId", approvedRecordIds));

            if (formInstanceId > 0) {
                dynamicQuery.add(RestrictionsFactoryUtil.eq("formInstanceId", formInstanceId));
            }

            List<DDMFormInstanceRecord> allRecords = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery(dynamicQuery);

            int count = 0;
            for (DDMFormInstanceRecord record : allRecords) {
                try {
                    if (matchesUserCustomFields(record, userCustomFields)) {
                        count++;
                    }
                } catch (Exception e) {
                    _log.warn("Error checking record match: " + record.getFormInstanceRecordId(), e);
                }
            }

            return count;

        } catch (Exception e) {
            _log.error("Error counting filtered form records", e);
            return 0;
        }
    }

    private static boolean matchesUserCustomFields(DDMFormInstanceRecord record, Map<String, List<String>> userCustomFields) {
        try {
            List<FormCounterRule> activeRules = FormCounterRuleLocalServiceUtil.findByActive(true);
            if (activeRules.isEmpty()) {
                return false;
            }

            DDMFormValues formValues = record.getDDMFormValues();
            if (formValues == null) {
                return false;
            }

            List<DDMFormFieldValue> formFieldValues = formValues.getDDMFormFieldValues();
            if (formFieldValues == null || formFieldValues.isEmpty()) {
                return false;
            }

            for (FormCounterRule rule : activeRules) {
                if (recordMatchesCompleteRule(record, formFieldValues, rule, userCustomFields)) {
                    return true;
                }
            }

            return false;

        } catch (Exception e) {
            _log.error("Error checking if record matches user custom fields: " + record.getFormInstanceRecordId(), e);
            return false;
        }
    }

    private static boolean recordMatchesCompleteRule(DDMFormInstanceRecord record,
                                                     List<DDMFormFieldValue> formFieldValues,
                                                     FormCounterRule rule,
                                                     Map<String, List<String>> userCustomFields) {
        try {
            String ruleConditions = rule.getRuleConditions();
            if (Validator.isNull(ruleConditions)) {
                return false;
            }

            JSONObject jsonConditions = JSONFactoryUtil.createJSONObject(ruleConditions);
            JSONArray conditions = jsonConditions.getJSONArray("conditions");
            String logicOperator = jsonConditions.has("logic") ? jsonConditions.getString("logic") : "AND";

            if (conditions == null || conditions.length() == 0) {
                return false;
            }

            int matchedConditions = 0;
            int totalConditions = conditions.length();

            for (int i = 0; i < conditions.length(); i++) {
                JSONObject condition = conditions.getJSONObject(i);
                String customFieldName = condition.getString("field");
                String operator = condition.has("operator") ? condition.getString("operator") : "contains";
                String referenceField = condition.getString("reference");

                if (!userCustomFields.containsKey(customFieldName)) {
                    continue;
                }

                List<String> customFieldValues = userCustomFields.get(customFieldName);

                boolean conditionMatched = false;
                for (String customFieldValue : customFieldValues) {
                    if (hasMatchingFieldValue(record, formFieldValues, referenceField, customFieldValue, operator)) {
                        conditionMatched = true;
                        break;
                    }
                }

                if (conditionMatched) {
                    matchedConditions++;
                }

                if ("OR".equalsIgnoreCase(logicOperator) && conditionMatched) {
                    return true;
                }

                if ("AND".equalsIgnoreCase(logicOperator) && !conditionMatched) {
                    return false;
                }
            }

            if ("AND".equalsIgnoreCase(logicOperator)) {
                return matchedConditions == totalConditions;
            } else {
                return matchedConditions > 0;
            }

        } catch (Exception e) {
            _log.warn("Error evaluating rule: " + rule.getFormCounterRuleId(), e);
            return false;
        }
    }


    private static boolean hasAnyReferenceField(DDMStructure structure, Set<String> referenceFields) {
        try {
            String definition = structure.getDefinition();
            if (definition != null) {
                JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(definition);
                JSONArray fields = jsonDefinition.getJSONArray("fields");

                if (fields != null) {
                    return hasAnyReferenceFieldRecursively(fields, referenceFields);
                }
            }
        } catch (Exception e) {
            _log.error("Error checking form reference fields", e);
        }

        return false;
    }


    private static boolean hasAnyReferenceFieldRecursively(JSONArray fields, Set<String> referenceFields) {
        if (fields == null || referenceFields == null || referenceFields.isEmpty()) {
            return false;
        }

        for (int i = 0; i < fields.length(); i++) {
            try {
                JSONObject field = fields.getJSONObject(i);
                String fieldReference = field.getString("fieldReference");

                for (String refField : referenceFields) {
                    if (refField.equalsIgnoreCase(fieldReference)) {
                        return true;
                    }
                }

                if (field.has("nestedFields")) {
                    JSONArray nestedFields = field.getJSONArray("nestedFields");
                    if (nestedFields != null && nestedFields.length() > 0) {
                        if (hasAnyReferenceFieldRecursively(nestedFields, referenceFields)) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                _log.warn("Error processing field in reference field search", e);
            }
        }

        return false;
    }

    private static boolean hasMatchingFieldValue(DDMFormInstanceRecord record, List<DDMFormFieldValue> fieldValues,
                                                 String referenceField, String customFieldValue, String operator) {
        if (fieldValues == null || fieldValues.isEmpty()) {
            return false;
        }

        for (DDMFormFieldValue fieldValue : fieldValues) {
            try {
                String fieldRef = fieldValue.getFieldReference();

                if (referenceField.equalsIgnoreCase(fieldRef)) {
                    if (fieldValue.getValue() != null && fieldValue.getValue().getDefaultLocale() != null) {
                        String recordFieldValue = GetterUtil.getString(
                                fieldValue.getValue().getString(fieldValue.getValue().getDefaultLocale()));

                        recordFieldValue = extractDisplayValueFromStructure(
                                record, fieldValue, recordFieldValue, referenceField);

                        if (Validator.isNotNull(recordFieldValue)) {
                            if (matchesCondition(recordFieldValue, customFieldValue, operator)) {
                                return true;
                            }
                        }
                    }
                }

                List<DDMFormFieldValue> nestedFieldValues = fieldValue.getNestedDDMFormFieldValues();
                if (nestedFieldValues != null && !nestedFieldValues.isEmpty()) {
                    if (hasMatchingFieldValue(record, nestedFieldValues, referenceField, customFieldValue, operator)) {
                        return true;
                    }
                }

            } catch (Exception e) {
                _log.warn("Error checking field value match", e);
            }
        }

        return false;
    }

    private static boolean matchesCondition(String recordValue, String customFieldValue, String operator) {
        if (Validator.isNull(recordValue) || Validator.isNull(customFieldValue)) {
            return false;
        }

        String recordValueLower = recordValue.toLowerCase().trim();
        String customFieldValueLower = customFieldValue.toLowerCase().trim();

        switch (operator.toLowerCase()) {
            case "contains":
                return recordValueLower.contains(customFieldValueLower);
            case "equal":
                return recordValueLower.equals(customFieldValueLower);
            case "not-equal":
                return !recordValueLower.equals(customFieldValueLower);
            default:
                _log.warn("Unknown operator: " + operator + ", defaulting to 'contains'");
                return recordValueLower.contains(customFieldValueLower);
        }
    }

    private static String extractDisplayValueFromStructure(DDMFormInstanceRecord record,
                                                           DDMFormFieldValue fieldValue,
                                                           String optionValue, String referenceField) {
        try {
            DDMFormInstance formInstance = DDMFormInstanceLocalServiceUtil
                    .fetchDDMFormInstance(record.getFormInstanceId());
            if (formInstance != null) {
                DDMStructure structure = formInstance.getStructure();
                if (structure != null) {
                    String definition = structure.getDefinition().toLowerCase();

                    if (definition.contains(referenceField.toLowerCase())) {
                        return parseOptionValueFromDefinition(definition, fieldValue.getFieldReference(), optionValue);
                    }
                }
            }
        } catch (Exception e) {
            _log.error("Error extracting display value from structure for option: " + optionValue, e);
        }

        return optionValue;
    }

    private static String parseOptionValueFromDefinition(String definition, String fieldReference, String optionValue) {
        try {
            JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(definition);

            JSONArray fields = jsonDefinition.getJSONArray("fields");
            if (fields != null) {
                String result = parseOptionValueRecursively(fields, fieldReference.toLowerCase(), optionValue);
                if (result != null && !result.equals(optionValue)) {
                    return result;
                }
            }
        } catch (Exception e) {
            _log.error("Error parsing option value from definition using JSON: " + optionValue, e);
        }

        return optionValue;
    }

    private static String parseOptionValueRecursively(JSONArray fields, String fieldReference, String optionValue) {
        if (fields == null) {
            return null;
        }

        for (int i = 0; i < fields.length(); i++) {
            try {
                JSONObject field = fields.getJSONObject(i);
                String currentFieldReference = field.getString("fieldreference");

                if (fieldReference.contains(currentFieldReference)) {
                    JSONArray options = field.getJSONArray("options");
                    if (options != null) {
                        String label = findOptionLabel(options, optionValue);
                        if (label != null && !label.equals(optionValue)) {
                            return label;
                        }
                    }
                }

                if (field.has("nestedfields")) {
                    JSONArray nestedFields = field.getJSONArray("nestedfields");
                    if (nestedFields != null && nestedFields.length() > 0) {
                        String result = parseOptionValueRecursively(nestedFields, fieldReference, optionValue);
                        if (result != null && !result.equals(optionValue)) {
                            return result;
                        }
                    }
                }
            } catch (Exception e) {
                _log.warn("Error processing field in option value parsing", e);
            }
        }

        return null;
    }

    private static String findOptionLabel(JSONArray options, String optionValue) {
        try {
            for (int i = 0; i < options.length(); i++) {
                JSONObject option = options.getJSONObject(i);
                String value = option.getString("value");
                optionValue = optionValue.toLowerCase();

                if (optionValue.contains(value)) {
                    JSONObject label = option.getJSONObject("label");
                    if (label != null) {
                        String labelText = null;

                        Iterator<String> keys = label.keys();
                        if (keys.hasNext()) {
                            String firstKey = keys.next();
                            labelText = label.getString(firstKey);
                        }

                        if (Validator.isNotNull(labelText)) {
                            _log.debug("Found option label '" + labelText + "' for value: " + optionValue);
                            return labelText;
                        }
                    }
                }
            }
        } catch (Exception e) {
            _log.error("Error finding option label for value: " + optionValue, e);
        }

        _log.debug("No label found for option value: " + optionValue + ", returning original value");
        return optionValue;
    }

    public static DDMFormInstance getFormInstance(long formInstanceId) {
        try {
            return DDMFormInstanceLocalServiceUtil.fetchDDMFormInstance(formInstanceId);
        } catch (Exception e) {
            _log.error("Error retrieving form instance: " + formInstanceId, e);
            return null;
        }
    }

    public static List<DDMFormInstanceRecord> searchFormRecords(
            SearchCriteria searchCriteria, int start, int end,
            String orderByCol, String orderByType) {

        Map<String, List<String>> userCustomFields = searchCriteria.getUserCustomFields();
        if (userCustomFields == null || userCustomFields.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            List<Long> approvedRecordIds = getApprovedRecordIds();

            if (approvedRecordIds.isEmpty()) {
                return new ArrayList<>();
            }

            DynamicQuery dynamicQuery = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

            dynamicQuery.add(RestrictionsFactoryUtil.in("formInstanceRecordId", approvedRecordIds));

            if (searchCriteria.getFormInstanceId() > 0) {
                dynamicQuery.add(PropertyFactoryUtil.forName("formInstanceId").
                        eq(searchCriteria.getFormInstanceId()));
            }

            if (searchCriteria.getStartDate() != null) {
                dynamicQuery.add(PropertyFactoryUtil.forName("createDate").
                        ge(searchCriteria.getStartDate()));
            }

            if (searchCriteria.getEndDate() != null) {
                Date endDate = new Date(searchCriteria.getEndDate().getTime() + 24 * 60 * 60 * 1000);
                dynamicQuery.add(PropertyFactoryUtil.forName("createDate").lt(endDate));
            }

            if (Validator.isNotNull(orderByCol)) {
                if ("desc".equalsIgnoreCase(orderByType)) {
                    dynamicQuery.addOrder(OrderFactoryUtil.desc(orderByCol));
                } else {
                    dynamicQuery.addOrder(OrderFactoryUtil.asc(orderByCol));
                }
            }

            List<DDMFormInstanceRecord> allRecords = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery(dynamicQuery);

            List<DDMFormInstanceRecord> filteredRecords = new ArrayList<>();
            for (DDMFormInstanceRecord record : allRecords) {
                try {
                    if (!matchesUserCustomFields(record, userCustomFields)) {
                        continue;
                    }

                    if (!matchesSeenStatus(record, searchCriteria.getStatus())) {
                        continue;
                    }

                    if (matchesTextCriteria(record, searchCriteria)) {
                        filteredRecords.add(record);
                    }
                } catch (Exception e) {
                    _log.warn("Error filtering record: " + record.getFormInstanceRecordId(), e);
                }
            }

            int fromIndex = Math.min(start, filteredRecords.size());
            int toIndex = Math.min(end, filteredRecords.size());

            return filteredRecords.subList(fromIndex, toIndex);

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static List<Long> getApprovedRecordIds() {
        DynamicQuery versionQuery = DDMFormInstanceRecordVersionLocalServiceUtil.dynamicQuery();

        versionQuery.add(PropertyFactoryUtil.forName("status").eq(WorkflowConstants.STATUS_APPROVED));
        versionQuery.setProjection(ProjectionFactoryUtil.distinct(
                ProjectionFactoryUtil.property("formInstanceRecordId")));

        return DDMFormInstanceRecordVersionLocalServiceUtil.dynamicQuery(versionQuery);
    }

    private static boolean matchesSeenStatus(DDMFormInstanceRecord record, String statusCriteria) {
        if (Validator.isNull(statusCriteria) || "all".equals(statusCriteria)) {
            return true;
        }

        try {
            FormSubmissionStatus submissionStatus = FormSubmissionStatusLocalServiceUtil
                    .getByFormInstanceRecordId(record.getFormInstanceRecordId());

            if (submissionStatus == null) {
                return "unseen".equals(statusCriteria);
            }

            boolean isSeen = submissionStatus.isSeen();

            if ("seen".equals(statusCriteria)) {
                return isSeen;
            } else if ("unseen".equals(statusCriteria)) {
                return !isSeen;
            }
        } catch (Exception e) {
            _log.warn("Error checking seen status for record: " + record.getFormInstanceRecordId(), e);
            return "unseen".equals(statusCriteria);
        }

        return true;
    }

    private static boolean matchesTextCriteria(DDMFormInstanceRecord record, SearchCriteria searchCriteria) {
        try {
            DDMFormValues formValues = record.getDDMFormValues();
            if (formValues == null) {
                return !searchCriteria.hasSearchCriteria();
            }

            DDMFormInstance formInstance = getFormInstance(record.getFormInstanceId());

            if (Validator.isNotNull(searchCriteria.getFormName()) && formInstance != null) {
                String formName = formInstance.getName(formValues.getDefaultLocale());
                if (!PersianTextUtil.contains(formName, searchCriteria.getFormName())) {
                    return false;
                }
            }

            List<DDMFormFieldValue> fieldValues = formValues.getDDMFormFieldValues();

            boolean registrantNameMatch = true;
            if (Validator.isNotNull(searchCriteria.getRegistrantName())) {
                String submitterName = extractSubmitterNameFromRecord(record);
                registrantNameMatch = Validator.isNotNull(submitterName) &&
                        PersianTextUtil.contains(submitterName, searchCriteria.getRegistrantName());
            }

            boolean formNumberMatch = true;
            if (Validator.isNotNull(searchCriteria.getFormNumber())) {
                formNumberMatch = record.getFormInstanceRecordId() == GetterUtil.getLong(searchCriteria.getFormNumber())
                        ||
                        PersianTextUtil.contains(String.valueOf(record.getFormInstanceRecordId()),
                                searchCriteria.getFormNumber());
            }

            boolean trackingCodeMatch = checkFieldMatch(fieldValues, "trackingCode", searchCriteria.getTrackingCode());

            return registrantNameMatch && formNumberMatch && trackingCodeMatch;

        } catch (Exception e) {
            _log.warn("Error matching text criteria for record: " + record.getFormInstanceRecordId(), e);
            return false;
        }
    }

    private static boolean checkFieldMatch(List<DDMFormFieldValue> fieldValues, String fieldName, String searchValue) {
        if (Validator.isNull(searchValue)) {
            return true; // No search criteria for this field
        }

        return checkFieldMatchRecursively(fieldValues, fieldName, searchValue);
    }

    private static boolean checkFieldMatchRecursively(List<DDMFormFieldValue> fieldValues, String fieldName,
                                                      String searchValue) {
        if (fieldValues == null || fieldValues.isEmpty()) {
            return false;
        }

        for (DDMFormFieldValue fieldValue : fieldValues) {
            try {
                if (fieldName.equals(fieldValue.getFieldReference()) ||
                        fieldName.equals(fieldValue.getName())) {

                    if (fieldValue.getValue() != null && fieldValue.getValue().getDefaultLocale() != null) {
                        String fieldValueString = GetterUtil.getString(
                                fieldValue.getValue().getString(fieldValue.getValue().getDefaultLocale()));

                        if (PersianTextUtil.contains(fieldValueString, searchValue)) {
                            return true;
                        }
                    }
                }

                List<DDMFormFieldValue> nestedFieldValues = fieldValue.getNestedDDMFormFieldValues();
                if (nestedFieldValues != null && !nestedFieldValues.isEmpty()) {
                    if (checkFieldMatchRecursively(nestedFieldValues, fieldName, searchValue)) {
                        return true;
                    }
                }

            } catch (Exception e) {
                _log.warn("Error checking field match for field: " + fieldName, e);
            }
        }

        return false;
    }

    public static String extractSubmitterNameFromRecord(DDMFormInstanceRecord record) {
        try {
            if (record != null) {
                DDMFormValues formValues = record.getDDMFormValues();
                if (formValues != null) {
                    List<DDMFormFieldValue> formFieldValues = formValues.getDDMFormFieldValues();

                    String firstName;
                    String lastName;

                    Map<String, String> nameFields = extractNameFieldsRecursively(formFieldValues);

                    firstName = nameFields.getOrDefault("firstName", "");
                    lastName = nameFields.getOrDefault("lastName", "");

                    String fullName = (firstName + " " + lastName).trim();
                    return Validator.isNotNull(fullName) ? fullName : "";
                }
            }
        } catch (Exception e) {
            _log.error("Error extracting submitter name from record", e);
        }
        return "";
    }

    private static Map<String, String> extractNameFieldsRecursively(List<DDMFormFieldValue> formFieldValues) {
        Map<String, String> nameFields = new HashMap<>();
        nameFields.put("firstName", "");
        nameFields.put("lastName", "");

        if (formFieldValues == null || formFieldValues.isEmpty()) {
            return nameFields;
        }

        for (DDMFormFieldValue fieldValue : formFieldValues) {
            try {
                String fieldRef = fieldValue.getFieldReference() != null
                        ? fieldValue.getFieldReference().toLowerCase()
                        : "";

                if (fieldValue.getValue() != null && fieldValue.getValue().getDefaultLocale() != null) {
                    String value = GetterUtil.getString(
                            fieldValue.getValue().getString(fieldValue.getValue().getDefaultLocale()));

                    if (Validator.isNotNull(value)) {
                        if (fieldRef.contains("fullname") || fieldRef.equals("full_name") ||
                                fieldRef.equals("name") || fieldRef.equals("personname") ||
                                fieldRef.equals("person_name")) {

                            String[] nameParts = value.trim().split("\\s+", 2);
                            if (nameParts.length >= 1 && Validator.isNull(nameFields.get("firstName"))) {
                                nameFields.put("firstName", nameParts[0]);
                            }
                            if (nameParts.length >= 2 && Validator.isNull(nameFields.get("lastName"))) {
                                nameFields.put("lastName", nameParts[1]);
                            }
                        } else if (fieldRef.contains("first") || fieldRef.equals("firstname") ||
                                fieldRef.equals("fname") || fieldRef.equals("first_name")) {
                            if (Validator.isNull(nameFields.get("firstName"))) {
                                nameFields.put("firstName", value);
                            }
                        } else if (fieldRef.contains("last") || fieldRef.equals("lastname") ||
                                fieldRef.equals("lname") || fieldRef.equals("surname") ||
                                fieldRef.equals("last_name")) {
                            if (Validator.isNull(nameFields.get("lastName"))) {
                                nameFields.put("lastName", value);
                            }
                        }
                    }
                }

                List<DDMFormFieldValue> nestedFieldValues = fieldValue.getNestedDDMFormFieldValues();
                if (nestedFieldValues != null && !nestedFieldValues.isEmpty()) {
                    Map<String, String> nestedNameFields = extractNameFieldsRecursively(nestedFieldValues);

                    if (Validator.isNull(nameFields.get("firstName")) &&
                            Validator.isNotNull(nestedNameFields.get("firstName"))) {
                        nameFields.put("firstName", nestedNameFields.get("firstName"));
                    }
                    if (Validator.isNull(nameFields.get("lastName")) &&
                            Validator.isNotNull(nestedNameFields.get("lastName"))) {
                        nameFields.put("lastName", nestedNameFields.get("lastName"));
                    }
                }

                if (Validator.isNotNull(nameFields.get("firstName")) &&
                        Validator.isNotNull(nameFields.get("lastName"))) {
                    break;
                }

            } catch (Exception e) {
                _log.warn("Error extracting name field value", e);
            }
        }

        return nameFields;
    }

    public static boolean recordHasRuleReferenceFields(DDMFormInstanceRecord record) {
        try {
            List<FormCounterRule> activeRules = FormCounterRuleLocalServiceUtil.findByActive(true);
            if (activeRules.isEmpty()) {
                return false;
            }

            Set<String> allReferenceFields = new HashSet<>();
            for (FormCounterRule rule : activeRules) {
                try {
                    JSONObject jsonConditions = JSONFactoryUtil.createJSONObject(rule.getRuleConditions());
                    JSONArray conditions = jsonConditions.getJSONArray("conditions");
                    for (int i = 0; i < conditions.length(); i++) {
                        JSONObject condition = conditions.getJSONObject(i);
                        String referenceField = condition.getString("reference");
                        if (Validator.isNotNull(referenceField)) {
                            allReferenceFields.add(referenceField.toLowerCase().trim());
                        }
                    }
                } catch (Exception e) {
                    _log.warn("Error parsing rule conditions: " + rule.getFormCounterRuleId(), e);
                }
            }

            if (allReferenceFields.isEmpty()) {
                return false;
            }

            DDMFormValues formValues = record.getDDMFormValues();
            if (formValues == null) {
                return false;
            }

            List<DDMFormFieldValue> formFieldValues = formValues.getDDMFormFieldValues();
            if (formFieldValues == null || formFieldValues.isEmpty()) {
                return false;
            }

            return hasAnyReferenceFieldInValues(formFieldValues, allReferenceFields);

        } catch (Exception e) {
            _log.error("Error checking if record has rule reference fields", e);
            return false;
        }
    }

    private static boolean hasAnyReferenceFieldInValues(
            List<DDMFormFieldValue> fieldValues, Set<String> referenceFields) {

        for (DDMFormFieldValue fieldValue : fieldValues) {
            String fieldRef = fieldValue.getFieldReference().toLowerCase().trim();

            if (referenceFields.contains(fieldRef)) {
                return true;
            }

            List<DDMFormFieldValue> nestedFieldValues = fieldValue.getNestedDDMFormFieldValues();
            if (nestedFieldValues != null && !nestedFieldValues.isEmpty()) {
                if (hasAnyReferenceFieldInValues(nestedFieldValues, referenceFields)) {
                    return true;
                }
            }
        }

        return false;
    }
}
