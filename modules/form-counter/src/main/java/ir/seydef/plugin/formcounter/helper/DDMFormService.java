package ir.seydef.plugin.formcounter.helper;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.model.SearchCriteria;
import ir.seydef.plugin.formcounter.service.FormCounterRuleLocalServiceUtil;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;
import ir.seydef.plugin.formcounter.util.PersianTextUtil;

import java.util.*;

/**
 * Service class for DDM Form operations with dynamic rule-based filtering
 *
 * @author S.Abolfazl Eftekhari
 */
public class DDMFormService {

    private static final Log _log = LogFactoryUtil.getLog(DDMFormService.class);

    /**
     * Get form instances that have fields matching the user's custom field rules
     *
     * @param userCustomFields map of user's custom field names to values
     * @return list of matching form instances
     */
    public static List<DDMFormInstance> getFormInstancesForUser(Map<String, List<String>> userCustomFields) {
        List<DDMFormInstance> matchingFormInstances = new ArrayList<>();

        try {
            if (userCustomFields == null || userCustomFields.isEmpty()) {
                return matchingFormInstances;
            }

            // Get all active rules
            List<FormCounterRule> activeRules = FormCounterRuleLocalServiceUtil.findByActive(true);
            if (activeRules.isEmpty()) {
                return matchingFormInstances;
            }

            // Get all reference fields for user's custom fields
            Map<String, Set<String>> referenceFieldsMap = RuleFilterHelper.getReferenceFieldsForCustomFields(
                    activeRules, userCustomFields.keySet());

            if (referenceFieldsMap.isEmpty()) {
                return matchingFormInstances;
            }

            // Collect all reference fields
            Set<String> allReferenceFields = new HashSet<>();
            for (Set<String> refs : referenceFieldsMap.values()) {
                allReferenceFields.addAll(refs);
            }

            // Get all form instances and check which have matching reference fields
            List<DDMFormInstance> allFormInstances = DDMFormInstanceLocalServiceUtil.getDDMFormInstances(-1, -1);

            for (DDMFormInstance formInstance : allFormInstances) {
                try {
                    DDMStructure structure = formInstance.getStructure();
                    if (structure != null) {
                        if (hasAnyReferenceField(structure, allReferenceFields)) {
                            matchingFormInstances.add(formInstance);
                        }
                    }
                } catch (Exception e) {
                    _log.warn("Error checking form structure for form instance: " + formInstance.getFormInstanceId(), e);
                }
            }

        } catch (Exception e) {
            _log.error("Error retrieving form instances for user custom fields", e);
        }

        return matchingFormInstances;
    }

    /**
     * Get filtered form records based on user's custom fields and rules
     *
     * @param formInstanceId   the form instance ID (0 for all forms)
     * @param userCustomFields map of user's custom field names to values
     * @param start            start index
     * @param end              end index
     * @param orderByCol       order by column
     * @param orderByType      order type (asc/desc)
     * @return list of filtered records
     */
    public static List<DDMFormInstanceRecord> getFilteredFormRecords(
            long formInstanceId, Map<String, List<String>> userCustomFields, int start, int end,
            String orderByCol, String orderByType) {

        if (userCustomFields == null || userCustomFields.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            DynamicQuery dynamicQuery = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

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

            // Apply pagination
            int fromIndex = Math.min(start, filteredRecords.size());
            int toIndex = Math.min(end, filteredRecords.size());

            return filteredRecords.subList(fromIndex, toIndex);

        } catch (Exception e) {
            _log.error("Error retrieving filtered form records", e);
            return new ArrayList<>();
        }
    }

    /**
     * Get count of filtered form records based on user's custom fields
     *
     * @param formInstanceId   the form instance ID (0 for all forms)
     * @param userCustomFields map of user's custom field names to values
     * @return count of filtered records
     */
    public static int getFilteredFormRecordsCount(long formInstanceId, Map<String, List<String>> userCustomFields) {
        if (userCustomFields == null || userCustomFields.isEmpty()) {
            return 0;
        }

        try {
            DynamicQuery dynamicQuery = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

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

    /**
     * Check if a record matches the user's custom fields based on active rules
     *
     * @param record           the form instance record
     * @param userCustomFields map of user's custom field names to values
     * @return true if record matches, false otherwise
     */
    private static boolean matchesUserCustomFields(DDMFormInstanceRecord record, Map<String, List<String>> userCustomFields) {
        try {
            // Get all active rules
            List<FormCounterRule> activeRules = FormCounterRuleLocalServiceUtil.findByActive(true);
            if (activeRules.isEmpty()) {
                return false;
            }

            // Get form field values from the record
            DDMFormValues formValues = record.getDDMFormValues();
            if (formValues == null) {
                return false;
            }

            List<DDMFormFieldValue> formFieldValues = formValues.getDDMFormFieldValues();
            if (formFieldValues == null || formFieldValues.isEmpty()) {
                return false;
            }

            // Check each custom field
            for (Map.Entry<String, List<String>> entry : userCustomFields.entrySet()) {
                String customFieldName = entry.getKey();
                List<String> customFieldValues = entry.getValue();

                // Get rules for this custom field
                List<FormCounterRule> applicableRules = RuleFilterHelper.getRulesForCustomField(activeRules, customFieldName);

                if (!applicableRules.isEmpty()) {
                    // Get reference fields from rules
                    Set<String> referenceFields = RuleFilterHelper.getReferenceFieldsForCustomField(applicableRules, customFieldName);

                    // Check if any of the record's fields match
                    for (String referenceField : referenceFields) {
                        for (String customFieldValue : customFieldValues) {
                            // Get the operator from the rule
                            String operator = "contains"; // default
                            for (FormCounterRule rule : applicableRules) {
                                String ruleOperator = RuleFilterHelper.getOperatorForCondition(rule, customFieldName, referenceField);
                                if (Validator.isNotNull(ruleOperator)) {
                                    operator = ruleOperator;
                                    break;
                                }
                            }

                            // Check if the record has this reference field with matching value
                            if (hasMatchingFieldValue(record, formFieldValues, referenceField, customFieldValue, operator)) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;

        } catch (Exception e) {
            _log.error("Error checking if record matches user custom fields: " + record.getFormInstanceRecordId(), e);
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

    private static boolean hasMatchingFieldValue(DDMFormInstanceRecord record, List<DDMFormFieldValue> fieldValues, String referenceField,
                                                 String customFieldValue, String operator) {
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

                        if (Validator.isNotNull(recordFieldValue)) {
                            if (RuleFilterHelper.matchesCondition(recordFieldValue, customFieldValue, operator)) {
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

    public static DDMFormInstance getFormInstance(long formInstanceId) {
        try {
            return DDMFormInstanceLocalServiceUtil.fetchDDMFormInstance(formInstanceId);
        } catch (Exception e) {
            _log.error("Error retrieving form instance: " + formInstanceId, e);
            return null;
        }
    }

    /**
     * Search form records based on search criteria and user custom fields
     */
    public static List<DDMFormInstanceRecord> searchFormRecords(
            SearchCriteria searchCriteria, int start, int end,
            String orderByCol, String orderByType) {

        Map<String, List<String>> userCustomFields = searchCriteria.getUserCustomFields();
        if (userCustomFields == null || userCustomFields.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            DynamicQuery dynamicQuery = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

            if (searchCriteria.getFormInstanceId() > 0) {
                dynamicQuery.add(PropertyFactoryUtil.forName("formInstanceId").eq(searchCriteria.getFormInstanceId()));
            }

            if (searchCriteria.getStartDate() != null) {
                dynamicQuery.add(PropertyFactoryUtil.forName("createDate").ge(searchCriteria.getStartDate()));
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

    /**
     * Get count of search results based on search criteria and user custom fields
     */
    public static int getSearchFormRecordsCount(SearchCriteria searchCriteria) {
        Map<String, List<String>> userCustomFields = searchCriteria.getUserCustomFields();
        if (userCustomFields == null || userCustomFields.isEmpty()) {
            return 0;
        }

        try {
            DynamicQuery dynamicQuery = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

            if (searchCriteria.getFormInstanceId() > 0) {
                dynamicQuery.add(PropertyFactoryUtil.forName("formInstanceId").eq(searchCriteria.getFormInstanceId()));
            }

            if (searchCriteria.getStartDate() != null) {
                dynamicQuery.add(PropertyFactoryUtil.forName("createDate").ge(searchCriteria.getStartDate()));
            }

            if (searchCriteria.getEndDate() != null) {
                Date endDate = new Date(searchCriteria.getEndDate().getTime() + 24 * 60 * 60 * 1000);
                dynamicQuery.add(PropertyFactoryUtil.forName("createDate").lt(endDate));
            }

            List<DDMFormInstanceRecord> allRecords = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery(dynamicQuery);

            int count = 0;
            for (DDMFormInstanceRecord record : allRecords) {
                try {
                    if (matchesUserCustomFields(record, userCustomFields) &&
                            matchesSeenStatus(record, searchCriteria.getStatus()) &&
                            matchesTextCriteria(record, searchCriteria)) {
                        count++;
                    }
                } catch (Exception e) {
                    _log.warn("Error counting record: " + record.getFormInstanceRecordId(), e);
                }
            }

            return count;

        } catch (Exception e) {
            return 0;
        }
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
                        if (fieldRef.contains("first") || fieldRef.equals("firstname") ||
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
}
