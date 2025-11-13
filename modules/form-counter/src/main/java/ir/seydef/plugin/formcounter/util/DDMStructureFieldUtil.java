package ir.seydef.plugin.formcounter.util;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import ir.seydef.plugin.formcounter.model.DDMFieldInfo;
import ir.seydef.plugin.formcounter.model.DDMFieldInfo.FieldOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author S.Abolfazl Eftekhari
 */
public class DDMStructureFieldUtil {

    public static List<DDMFieldInfo> getFormFields(
            DDMFormInstance formInstance, Locale locale) {

        List<DDMFieldInfo> fields = new ArrayList<>();

        try {
            DDMStructure ddmStructure = formInstance.getStructure();

            String definition = ddmStructure.getDefinition();

            if (Validator.isNull(definition)) {
                return fields;
            }

            JSONObject definitionJSON = JSONFactoryUtil.createJSONObject(
                    definition);

            JSONArray fieldsArray = definitionJSON.getJSONArray("fields");

            if (fieldsArray == null) {
                return fields;
            }

            for (int i = 0; i < fieldsArray.length(); i++) {
                JSONObject fieldJSON = fieldsArray.getJSONObject(i);

                String name = fieldJSON.getString("name");
                String type = fieldJSON.getString("type");

                JSONObject labelJSON = fieldJSON.getJSONObject("label");

                String label = _getLocalizedValue(labelJSON, locale);

                if (Validator.isNull(label)) {
                    label = name;
                }

                DDMFieldInfo fieldInfo = new DDMFieldInfo(name, label, type);

                if (type.equals("select") || type.equals("radio") ||
                        type.equals("checkbox_multiple")) {

                    JSONArray optionsArray = fieldJSON.getJSONArray("options");

                    if (optionsArray != null) {
                        for (int j = 0; j < optionsArray.length(); j++) {
                            JSONObject optionJSON = optionsArray.getJSONObject(
                                    j);

                            JSONObject optionLabelJSON = optionJSON.getJSONObject("label");

                            String optionLabel = _getLocalizedValue(
                                    optionLabelJSON, locale);

                            String optionValue = optionJSON.getString("value");

                            if (Validator.isNull(optionLabel)) {
                                optionLabel = optionValue;
                            }

                            fieldInfo.addOption(
                                    new FieldOption(optionLabel, optionValue));
                        }
                    }
                }

                fields.add(fieldInfo);
            }
        } catch (Exception exception) {
            _log.error("Error extracting form fields", exception);
        }

        return fields;
    }

    public static boolean matchesFilter(
            DDMFormValues formValues, String fieldName, String fieldValue) {

        try {
            List<DDMFormFieldValue> fieldValues = formValues.getDDMFormFieldValues();

            for (DDMFormFieldValue fieldFieldValue : fieldValues) {
                if (fieldFieldValue.getName().equals(fieldName)) {
                    com.liferay.dynamic.data.mapping.model.Value value = fieldFieldValue.getValue();

                    String actualValue = value.getString(
                            value.getDefaultLocale());

                    if (Validator.isNull(actualValue)) {
                        return false;
                    }

                    if (actualValue.contains(fieldValue)) {
                        return true;
                    }

                    if (actualValue.equals(fieldValue)) {
                        return true;
                    }

                    JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
                            actualValue);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String val = jsonArray.getString(i);

                        if (val.equals(fieldValue)) {
                            return true;
                        }
                    }

                    return false;
                }

                if (!fieldFieldValue.getNestedDDMFormFieldValues().isEmpty()) {
                    if (_matchesFilterNested(
                            fieldFieldValue.getNestedDDMFormFieldValues(),
                            fieldName, fieldValue)) {

                        return true;
                    }
                }
            }
        } catch (Exception exception) {
            _log.warn("Error matching filter", exception);
        }

        return false;
    }

    private static String _getLocalizedValue(
            JSONObject jsonObject, Locale locale) {

        if (jsonObject == null) {
            return "";
        }

        String value = jsonObject.getString(locale.toString());

        if (Validator.isNull(value)) {
            value = jsonObject.getString("en_US");
        }

        if (Validator.isNull(value)) {
            for (String key : jsonObject.keySet()) {
                value = jsonObject.getString(key);

                if (Validator.isNotNull(value)) {
                    break;
                }
            }
        }

        return value;
    }

    private static boolean _matchesFilterNested(
            List<DDMFormFieldValue> fieldValues, String fieldName,
            String fieldValue) {

        for (DDMFormFieldValue fieldFieldValue : fieldValues) {
            if (fieldFieldValue.getName().equals(fieldName)) {
                com.liferay.dynamic.data.mapping.model.Value value = fieldFieldValue.getValue();

                String actualValue = value.getString(value.getDefaultLocale());

                if (Validator.isNotNull(actualValue) &&
                        actualValue.contains(fieldValue)) {

                    return true;
                }
            }

            if (!fieldFieldValue.getNestedDDMFormFieldValues().isEmpty()) {
                if (_matchesFilterNested(
                        fieldFieldValue.getNestedDDMFormFieldValues(), fieldName,
                        fieldValue)) {

                    return true;
                }
            }
        }

        return false;
    }

    private static final Log _log = LogFactoryUtil.getLog(
            DDMStructureFieldUtil.class);

}
