package ir.seydef.plugin.formcounter.serviceHelper;

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
import ir.seydef.plugin.formcounter.constants.FormCounterPortletKeys;
import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.model.SearchCriteria;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;
import ir.seydef.plugin.formcounter.util.PersianTextUtil;

import java.util.*;

/**
 * @author S.Abolfazl Eftekhari
 */
public class DDMFormService {

    private static final Log _log = LogFactoryUtil.getLog(DDMFormService.class);

    public static List<DDMFormInstance> getFormInstancesWithBranchId(long groupId, Locale locale) {
        List<DDMFormInstance> formInstancesWithBranchId = new ArrayList<>();

        try {
            List<DDMFormInstance> allFormInstances = DDMFormInstanceLocalServiceUtil.getFormInstances(groupId);

            for (DDMFormInstance formInstance : allFormInstances) {
                try {
                    DDMStructure structure = formInstance.getStructure();
                    if (structure != null) {
                        if (hasFormField(structure)) {
                            formInstancesWithBranchId.add(formInstance);
                        }
                    }
                } catch (Exception e) {
                    _log.warn("Error checking form structure for form instance: " + formInstance.getFormInstanceId(),
                            e);
                }
            }

        } catch (Exception e) {
            _log.error("Error retrieving form instances with branchId field", e);
        }

        return formInstancesWithBranchId;
    }

    public static List<DDMFormInstanceRecord> getFilteredFormRecords(
            long formInstanceId, String userBranchId, int start, int end,
            String orderByCol, String orderByType) {

        if (Validator.isNull(userBranchId)) {
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

            List<DDMFormInstanceRecord> allRecords = DDMFormInstanceRecordLocalServiceUtil.dynamicQuery(
                    dynamicQuery, start, end);

            List<DDMFormInstanceRecord> filteredRecords = new ArrayList<>();
            for (DDMFormInstanceRecord record : allRecords) {
                try {
                    String recordBranchId = extractBranchIdFromRecord(record);
                    if (userBranchId.equals(recordBranchId)) {
                        filteredRecords.add(record);
                    }
                } catch (Exception e) {
                    _log.warn("Error extracting branch ID from record: " + record.getFormInstanceRecordId(), e);
                }
            }

            return filteredRecords;

        } catch (Exception e) {
            _log.error("Error retrieving filtered form records", e);
            return new ArrayList<>();
        }
    }

    public static int getFilteredFormRecordsCount(long formInstanceId, String userBranchId) {
        if (Validator.isNull(userBranchId)) {
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
                    String recordBranchId = extractBranchIdFromRecord(record);

                    if (userBranchId.equals(recordBranchId)) {
                        count++;
                    }
                } catch (Exception e) {
                    _log.warn("Error extracting branch ID from record: " + record.getFormInstanceRecordId(), e);
                }
            }

            return count;

        } catch (Exception e) {
            _log.error("Error counting filtered form records", e);
            return 0;
        }
    }

    public static String extractBranchIdFromRecord(DDMFormInstanceRecord record) {
        try {
            DDMFormValues formValues = record.getDDMFormValues();
            if (formValues != null) {
                List<DDMFormFieldValue> formFieldValues = formValues.getDDMFormFieldValues();

                for (DDMFormFieldValue fieldValue : formFieldValues) {
                    if (FormCounterPortletKeys.DDM_FIELD_BRANCH_ID
                            .contains(fieldValue.getFieldReference().toLowerCase())) {
                        if (fieldValue.getValue() != null &&
                                fieldValue.getValue().getDefaultLocale() != null) {

                            String optionValue = GetterUtil.getString(
                                    fieldValue.getValue().getString(fieldValue.getValue().getDefaultLocale()));

                            return extractDisplayValueFromStructure(record, fieldValue, optionValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            _log.error("Error extracting branch ID from record: " + record.getFormInstanceRecordId(), e);
        }

        return null;
    }

    private static String extractDisplayValueFromStructure(DDMFormInstanceRecord record,
            DDMFormFieldValue fieldValue,
            String optionValue) {
        try {
            DDMFormInstance formInstance = DDMFormInstanceLocalServiceUtil
                    .fetchDDMFormInstance(record.getFormInstanceId());
            if (formInstance != null) {
                DDMStructure structure = formInstance.getStructure();
                if (structure != null) {
                    String definition = structure.getDefinition().toLowerCase();

                    if (definition.contains(FormCounterPortletKeys.DDM_FIELD_BRANCH_ID)) {
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
                for (int i = 0; i < fields.length(); i++) {
                    JSONObject field = fields.getJSONObject(i);
                    String currentFieldReference = field.getString("fieldreference");
                    fieldReference = fieldReference.toLowerCase();

                    if (fieldReference.contains(currentFieldReference)) {
                        JSONArray options = field.getJSONArray("options");
                        if (options != null) {
                            return findOptionLabel(options, optionValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            _log.error("Error parsing option value from definition using JSON: " + optionValue, e);
        }

        return optionValue;
    }

    private static String findOptionLabel(JSONArray options, String optionValue) {
        try {
            for (int i = 0; i < options.length(); i++) {
                JSONObject option = options.getJSONObject(i);
                String value = option.getString("value");

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

    private static boolean hasFormField(DDMStructure structure) {
        try {
            String definition = structure.getDefinition();
            if (definition != null) {
                JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(definition);
                JSONArray fields = jsonDefinition.getJSONArray("fields");

                if (fields != null) {
                    for (int i = 0; i < fields.length(); i++) {
                        JSONObject field = fields.getJSONObject(i);
                        String fieldReference = field.getString("fieldReference");

                        if (FormCounterPortletKeys.DDM_FIELD_BRANCH_ID.contains(fieldReference.toLowerCase())) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            _log.error("Error checking form field: " + FormCounterPortletKeys.DDM_FIELD_BRANCH_ID, e);
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

    public static List<DDMFormInstanceRecord> searchFormRecords(
            SearchCriteria searchCriteria, int start, int end,
            String orderByCol, String orderByType) {

        if (Validator.isNull(searchCriteria.getUserBranchId())) {
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
                    String recordBranchId = extractBranchIdFromRecord(record);
                    if (!searchCriteria.getUserBranchId().equals(recordBranchId)) {
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

    public static int getSearchFormRecordsCount(SearchCriteria searchCriteria) {
        if (Validator.isNull(searchCriteria.getUserBranchId())) {
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
                    String recordBranchId = extractBranchIdFromRecord(record);
                    if (searchCriteria.getUserBranchId().equals(recordBranchId) &&
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
                formNumberMatch = record.getFormInstanceRecordId() == GetterUtil.getLong(searchCriteria.getFormNumber()) ||
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

        for (DDMFormFieldValue fieldValue : fieldValues) {
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
        }

        return false;
    }

    public static String extractSubmitterNameFromRecord(DDMFormInstanceRecord record) {
        try {
            if (record != null) {
                DDMFormValues formValues = record.getDDMFormValues();
                if (formValues != null) {
                    List<DDMFormFieldValue> formFieldValues = formValues.getDDMFormFieldValues();

                    String firstName = "";
                    String lastName = "";

                    for (DDMFormFieldValue fieldValue : formFieldValues) {
                        String fieldRef = fieldValue.getFieldReference() != null
                                ? fieldValue.getFieldReference().toLowerCase()
                                : "";

                        try {
                            if (fieldValue.getValue() != null && fieldValue.getValue().getDefaultLocale() != null) {
                                String value = GetterUtil.getString(
                                        fieldValue.getValue().getString(fieldValue.getValue().getDefaultLocale()));

                                if (fieldRef.contains("first") || fieldRef.equals("firstname") ||
                                        fieldRef.equals("fname") || fieldRef.equals("first_name")) {
                                    firstName = value;
                                } else if (fieldRef.contains("last") || fieldRef.equals("lastname") ||
                                        fieldRef.equals("lname") || fieldRef.equals("surname")
                                        || fieldRef.equals("last_name")) {
                                    lastName = value;
                                }
                            }
                        } catch (Exception e) {
                            _log.warn("Error extracting name field value", e);
                        }
                    }

                    String fullName = (firstName + " " + lastName).trim();
                    return Validator.isNotNull(fullName) ? fullName : "";
                }
            }
        } catch (Exception e) {
            _log.error("Error extracting submitter name from record", e);
        }
        return "";
    }
}
