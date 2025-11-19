package ir.seydef.plugin.formcounter.util;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import ir.seydef.plugin.formcounter.model.DDMFieldInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

			JSONObject definitionJsonObj = JSONFactoryUtil.createJSONObject(
				definition);

			JSONArray fieldsArray = definitionJsonObj.getJSONArray("fields");

			if (fieldsArray == null) {
				return fields;
			}

			for (int i = 0; i < fieldsArray.length(); i++) {
				JSONObject fieldJsonObj = fieldsArray.getJSONObject(i);

				String name = fieldJsonObj.getString("name");
				String type = fieldJsonObj.getString("type");

				JSONObject labelJsonObj = fieldJsonObj.getJSONObject("label");

				String label = _getLocalizedValue(labelJsonObj, locale);

				if (Validator.isNull(label)) {
					label = name;
				}

				DDMFieldInfo fieldInfo = new DDMFieldInfo(name, label, type);

				if (type.equals("select") || type.equals("radio") ||
					type.equals("checkbox_multiple")) {

					JSONArray optionsArray = fieldJsonObj.getJSONArray(
						"options");

					if (optionsArray != null) {
						for (int j = 0; j < optionsArray.length(); j++) {
							JSONObject optionJsonObj =
								optionsArray.getJSONObject(j);

							JSONObject optionLabelJsonObj =
								optionJsonObj.getJSONObject("label");

							String optionLabel = _getLocalizedValue(
								optionLabelJsonObj, locale);

							String optionValue = optionJsonObj.getString(
								"value");

							if (Validator.isNull(optionLabel)) {
								optionLabel = optionValue;
							}

							fieldInfo.addOption(
								new DDMFieldInfo.FieldOption(
									optionLabel, optionValue));
						}
					}
				}

				fields.add(fieldInfo);
			}
		}
		catch (Exception exception) {
			_log.error("Error extracting form fields");
		}

		return fields;
	}

	public static boolean matchesFilter(
		DDMFormValues formValues, String fieldName, String fieldValue) {

		try {
			List<DDMFormFieldValue> fieldValues =
				formValues.getDDMFormFieldValues();

			for (DDMFormFieldValue fieldFieldValue : fieldValues) {
				if (Objects.equals(fieldFieldValue.getName(), fieldName)) {
					Value value = fieldFieldValue.getValue();

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

				if (!fieldFieldValue.getNestedDDMFormFieldValues(
					).isEmpty() &&
					_matchesFilterNested(
						fieldFieldValue.getNestedDDMFormFieldValues(),
						fieldName, fieldValue)) {

					return true;
				}
			}
		}
		catch (Exception exception) {
			_log.warn("Error matching filter");
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
			if (Objects.equals(fieldFieldValue.getName(), fieldName)) {
				Value value = fieldFieldValue.getValue();

				String actualValue = value.getString(value.getDefaultLocale());

				if (Validator.isNotNull(actualValue) &&
					actualValue.contains(fieldValue)) {

					return true;
				}
			}

			if (!fieldFieldValue.getNestedDDMFormFieldValues(
				).isEmpty() &&
				_matchesFilterNested(
					fieldFieldValue.getNestedDDMFormFieldValues(), fieldName,
					fieldValue)) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureFieldUtil.class);

}