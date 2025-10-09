package ir.seydef.plugin.formcounter.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.List;

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
                    String label = findFieldLabelRecursively(fields, fieldName);
                    if (label != null) {
                        return label;
                    }
                }
            }
        } catch (Exception e) {
            _log.error("Error getting field label for field: " + fieldName, e);
        }
        return fieldName;
    }

    private static String findFieldLabelRecursively(JSONArray fields, String fieldName) {
        if (fields == null) {
            return null;
        }

        for (int i = 0; i < fields.length(); i++) {
            try {
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

                if (field.has("nestedFields")) {
                    JSONArray nestedFields = field.getJSONArray("nestedFields");
                    if (nestedFields != null && nestedFields.length() > 0) {
                        String nestedLabel = findFieldLabelRecursively(nestedFields, fieldName);
                        if (nestedLabel != null) {
                            return nestedLabel;
                        }
                    }
                }
            } catch (Exception e) {
                _log.warn("Error processing field in label search", e);
            }
        }

        return null;
    }

    public static String getDisplayValue(DDMStructure structure, DDMFormFieldValue fieldValue, String rawValue) {
        try {
            String cleanValue = rawValue;
            if (rawValue.startsWith("[\"") && rawValue.endsWith("\"]")) {
                cleanValue = rawValue.substring(2, rawValue.length() - 2);
            } else if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
                cleanValue = rawValue.substring(1, rawValue.length() - 1).replaceAll("\"", "");
            }

            String definition = structure.getDefinition();
            if (definition != null) {
                JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(definition);
                JSONArray fields = jsonDefinition.getJSONArray("fields");

                if (fields != null) {
                    // Search all fields recursively (both top-level and nested)
                    String optionLabel = findOptionLabelRecursively(fields, fieldValue.getName(), cleanValue);
                    if (optionLabel != null) {
                        return optionLabel;
                    }
                }
            }

            return cleanValue;
        } catch (Exception e) {
            _log.error("Error getting display value for field: " + fieldValue.getName() + ", value: " + rawValue, e);
            return rawValue;
        }
    }

    private static String findOptionLabelRecursively(JSONArray fields, String fieldName, String cleanValue) {
        if (fields == null) {
            return null;
        }

        for (int i = 0; i < fields.length(); i++) {
            try {
                JSONObject field = fields.getJSONObject(i);
                String currentFieldName = field.getString("name");

                // Check if this is the field we're looking for
                if (fieldName.equals(currentFieldName) && field.has("options")) {
                    JSONArray options = field.getJSONArray("options");
                    String label = findOptionLabel(options, cleanValue);
                    if (label != null && !label.equals(cleanValue)) {
                        return label;
                    }
                }

                // Check nested fields recursively
                if (field.has("nestedFields")) {
                    JSONArray nestedFields = field.getJSONArray("nestedFields");
                    String result = findOptionLabelRecursively(nestedFields, fieldName, cleanValue);
                    if (result != null) {
                        return result;
                    }
                }
            } catch (Exception e) {
                _log.warn("Error processing field in option search: " + fieldName, e);
            }
        }

        return null;
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

    public static String renderFormFieldsAsHtml(List<DDMFormFieldValue> formFieldValues, DDMStructure structure,
                                                int level) {
        StringBuilder html = new StringBuilder();

        if (formFieldValues == null || formFieldValues.isEmpty()) {
            return html.toString();
        }

        String indentClass = level > 0 ? " nested-level-" + level : "";

        for (DDMFormFieldValue fieldValue : formFieldValues) {
            try {
                String fieldName = fieldValue.getName();
                String fieldLabel = fieldName;

                if (structure != null) {
                    fieldLabel = getFieldLabel(structure, fieldName);
                }

                String value = "";
                boolean isMultiline = false;
                boolean hasValue = false;

                try {
                    if (fieldValue.getValue() != null && fieldValue.getValue().getDefaultLocale() != null) {
                        String rawValue = GetterUtil.getString(
                                fieldValue.getValue().getString(fieldValue.getValue().getDefaultLocale()));

                        if (Validator.isNotNull(rawValue)) {
                            hasValue = true;
                            String type = fieldValue.getType();
                            if (structure != null && type.equals("select")) {
                                value = getDisplayValue(structure, fieldValue, rawValue);
                            } else {
                                value = rawValue;
                            }

                            isMultiline = value.contains("\n") || value.length() > 100;
                        }
                    }
                } catch (Exception e) {
                    value = "N/A";
                    hasValue = true;
                }

                List<DDMFormFieldValue> nestedFields = fieldValue.getNestedDDMFormFieldValues();
                boolean hasNestedFields = nestedFields != null && !nestedFields.isEmpty();

                if (hasValue || hasNestedFields) {
                    html.append("<div class='form-field-group").append(indentClass).append("'>");

                    if (hasValue) {
                        html.append("<label class='form-field-label'>").append(escapeHtml(fieldLabel))
                                .append("</label>");

                        if (isMultiline) {
                            html.append(
                                    "<textarea class='form-control form-field-input form-field-textarea' disabled readonly rows='4'>");
                            html.append(escapeHtml(Validator.isNotNull(value) ? value : "N/A"));
                            html.append("</textarea>");
                        } else {
                            html.append("<input type='text' class='form-control form-field-input' value='")
                                    .append(escapeHtml(Validator.isNotNull(value) ? value : "N/A"))
                                    .append("' disabled readonly />");
                        }
                    }

                    // Recursively render nested fields
                    if (hasNestedFields) {
                        if (hasValue) {
                            html.append("<div class='nested-fields-container'>");
                        }
                        html.append(renderFormFieldsAsHtml(nestedFields, structure, level + 1));
                        if (hasValue) {
                            html.append("</div>");
                        }
                    }

                    html.append("</div>");
                }
            } catch (Exception e) {
                _log.error("Error rendering field", e);
            }
        }

        return html.toString();
    }

    private static String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
