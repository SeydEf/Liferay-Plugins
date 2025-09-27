package ir.seydef.plugin.formcounter.service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
                        if (hasFormField(structure, locale)) {
                            formInstancesWithBranchId.add(formInstance);
                            _log.debug("Found form with branchId field: " + formInstance.getName(locale));
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
            _log.debug("No user branch ID provided, returning empty list");
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
                    if (FormCounterPortletKeys.DDM_FIELD_BRANCH_ID.equals(fieldValue.getFieldReference())) {
                        if (fieldValue.getValue() != null &&
                                fieldValue.getValue().getDefaultLocale() != null) {

                            String optionValue = GetterUtil.getString(
                                    fieldValue.getValue().getString(fieldValue.getValue().getDefaultLocale()));

                            if (optionValue.contains("Option")) {
                                return extractDisplayValueFromStructure(record, fieldValue, optionValue);
                            }

                            return optionValue;
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
                    String definition = structure.getDefinition();

                    if (definition != null && definition.contains(FormCounterPortletKeys.DDM_FIELD_BRANCH_ID)) {
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
                    String currentFieldReference = field.getString("fieldReference");

                    if (fieldReference.equals(currentFieldReference)) {
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

                        if (label.has("en_US")) {
                            labelText = label.getString("en_US");
                        } else if (label.has("en")) {
                            labelText = label.getString("en");
                        } else {
                            java.util.Iterator<String> keys = label.keys();
                            if (keys.hasNext()) {
                                String firstKey = keys.next();
                                labelText = label.getString(firstKey);
                            }
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

    private static boolean hasFormField(DDMStructure structure, Locale locale) {
        try {
            String definition = structure.getDefinition();
            if (definition != null) {
                JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(definition);
                JSONArray fields = jsonDefinition.getJSONArray("fields");

                if (fields != null) {
                    for (int i = 0; i < fields.length(); i++) {
                        JSONObject field = fields.getJSONObject(i);
                        String fieldReference = field.getString("fieldReference");

                        if (FormCounterPortletKeys.DDM_FIELD_BRANCH_ID.equals(fieldReference)) {
                            _log.debug("Found branchId field in form structure");
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
}