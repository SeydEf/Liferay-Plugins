package ir.seydef.plugin.formcounter.rules.admin.util;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import ir.seydef.plugin.formcounter.rules.admin.model.ExpandoFieldInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author S.Abolfazl Eftekhari
 */
public class ExpandoFieldUtil {

	public static List<ExpandoFieldInfo> getAllCustomFields(long companyId) {
		List<ExpandoFieldInfo> customFields = new ArrayList<>();

		try {
			ExpandoTable table = ExpandoTableLocalServiceUtil.getTable(
				companyId, User.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);

			if (table == null) {
				return customFields;
			}

			List<ExpandoColumn> columns =
				ExpandoColumnLocalServiceUtil.getColumns(table.getTableId());

			for (ExpandoColumn column : columns) {
				ExpandoFieldInfo fieldInfo = new ExpandoFieldInfo();

				fieldInfo.setName(column.getName());
				fieldInfo.setDisplayName(column.getName());
				fieldInfo.setType("String");

				customFields.add(fieldInfo);
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error fetching custom fields. Maybe no custom fields exist " +
					"yet.");
		}

		return customFields;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoFieldUtil.class);

}