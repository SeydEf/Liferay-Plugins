package ir.seydef.plugin.formcounter.util;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author S.Abolfazl Eftekhari
 */
public class UserCustomFieldUtil {

	public static Map<String, List<String>> getUserCustomFieldsWithValues(
		long userId) {

		Map<String, List<String>> customFieldsMap = new HashMap<>();

		if (userId <= 0) {
			return customFieldsMap;
		}

		try {
			User user = UserLocalServiceUtil.fetchUser(userId);

			if ((user == null) || user.isDefaultUser()) {
				return customFieldsMap;
			}

			ExpandoTable table = ExpandoTableLocalServiceUtil.getTable(
				user.getCompanyId(), User.class.getName(), "CUSTOM_FIELDS");

			if (table == null) {
				return customFieldsMap;
			}

			List<ExpandoColumn> columns =
				ExpandoColumnLocalServiceUtil.getColumns(table.getTableId());

			for (ExpandoColumn column : columns) {
				try {
					ExpandoValue expandoValue =
						ExpandoValueLocalServiceUtil.getValue(
							user.getCompanyId(), User.class.getName(),
							"CUSTOM_FIELDS", column.getName(), userId);

					if (expandoValue != null) {
						String value = GetterUtil.getString(
							expandoValue.getData());

						if (Validator.isNotNull(value)) {
							List<String> values = splitCustomFieldValue(value);

							if (!values.isEmpty()) {
								customFieldsMap.put(column.getName(), values);
							}
						}
					}
				}
				catch (Exception exception) {
					_log.warn(
						"Error getting value for custom field: " +
							column.getName(),
						exception);
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error getting user custom fields for user: " + userId,
				exception);
		}

		return customFieldsMap;
	}

	public static List<String> splitCustomFieldValue(String value) {
		List<String> values = new ArrayList<>();

		if (Validator.isNull(value)) {
			return values;
		}

		if (value.contains(",")) {
			String[] parts = value.split(",");

			for (String part : parts) {
				String trimmed = part.trim();

				if (Validator.isNotNull(trimmed)) {
					values.add(trimmed);
				}
			}
		}
		else if (value.contains("-")) {
			String[] parts = value.split("-");

			for (String part : parts) {
				String trimmed = part.trim();

				if (Validator.isNotNull(trimmed)) {
					values.add(trimmed);
				}
			}
		}
		else {
			String trimmed = value.trim();

			if (Validator.isNotNull(trimmed)) {
				values.add(trimmed);
			}
		}

		return values;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserCustomFieldUtil.class);

}