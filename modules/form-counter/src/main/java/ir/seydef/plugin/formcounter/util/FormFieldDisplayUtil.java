package ir.seydef.plugin.formcounter.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.Locale;

/**
 * @author S.Abolfazl Eftekhari
 */
public class FormFieldDisplayUtil {

    private static final Log _log = LogFactoryUtil.getLog(FormFieldDisplayUtil.class);

    public static String getFieldLabel(DDMStructure structure, String fieldName) {
        try {
            String definition = structure.getDefinition();
            if (definition != null) {
                JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(definition);
                JSONArray fields = jsonDefinition.getJSONArray("fields");

                if (fields != null) {
                    for (int i = 0; i < fields.length(); i++) {
                        JSONObject field = fields.getJSONObject(i);
                        String currentFieldName = field.getString("name");

                        if (fieldName.equals(currentFieldName)) {
                            JSONObject label = field.getJSONObject("label");
                            if (label != null) {
                                Iterator<String> keys = label.keys();
                                if (keys.hasNext()) {
                                    return label.getString(keys.next());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            _log.error("Error getting field label for field: " + fieldName, e);
        }
        return fieldName;
    }

    public static String getDisplayValue(DDMStructure structure, DDMFormFieldValue fieldValue, String rawValue) {
        try {
            String cleanValue = rawValue;
            if (rawValue.startsWith("[\"") && rawValue.endsWith("\"]")) {
                cleanValue = rawValue.substring(2, rawValue.length() - 2);
            } else if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
                cleanValue = rawValue.substring(1, rawValue.length() - 1).replaceAll("\"", "");
            }

            if (cleanValue.contains("Option")) {
                String definition = structure.getDefinition();
                if (definition != null) {
                    JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(definition);
                    JSONArray fields = jsonDefinition.getJSONArray("fields");

                    if (fields != null) {
                        for (int i = 0; i < fields.length(); i++) {
                            JSONObject field = fields.getJSONObject(i);
                            String currentFieldName = field.getString("name");

                            if (fieldValue.getName().equals(currentFieldName)) {
                                JSONArray options = field.getJSONArray("options");
                                if (options != null) {
                                    return findOptionLabel(options, cleanValue);
                                }
                            }
                        }
                    }
                }
            }

            return cleanValue;
        } catch (Exception e) {
            _log.error("Error getting display value", e);
            return rawValue;
        }
    }

    public static String findOptionLabel(JSONArray options, String optionValue) {
        try {
            for (int i = 0; i < options.length(); i++) {
                JSONObject option = options.getJSONObject(i);
                String value = option.getString("value");

                if (optionValue.contains(value)) {
                    JSONObject label = option.getJSONObject("label");
                    if (label != null) {
                        Iterator<String> keys = label.keys();
                        if (keys.hasNext()) {
                            return label.getString(keys.next());
                        }
                    }
                }
            }
        } catch (Exception e) {
            _log.error("Error finding option label for value: " + optionValue, e);
        }
        return optionValue;
    }
}
