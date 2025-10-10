package ir.seydef.plugin.formcounter.util;

import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import ir.seydef.plugin.formcounter.constants.FormCounterPortletKeys;

import java.util.List;

/**
 * @author S.Abolfazl Eftekhari
 */
public class UserBranchUtil {

    private static final Log _log = LogFactoryUtil.getLog(UserBranchUtil.class);

    public static String getUserBranchId(long userId) {
        if (userId <= 0) {
            return null;
        }

        try {
            User user = UserLocalServiceUtil.fetchUser(userId);
            if (user == null || user.isDefaultUser()) {
                return null;
            }

            ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.getValue(
                    user.getCompanyId(),
                    User.class.getName(),
                    "CUSTOM_FIELDS",
                    FormCounterPortletKeys.USER_EXPANDO_BRANCH_ID,
                    user.getUserId());

            String branchId;
            if (expandoValue != null) {
                branchId = GetterUtil.getString(expandoValue.getData());
            } else {
                branchId = getUserBranchIdWithDynamicQuery(user.getUserId());
            }
            if (Validator.isNotNull(branchId)) {
                return branchId;
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    public static String getUserBranchIdWithDynamicQuery(long userId) {
        if (userId <= 0) {
            return null;
        }

        try {
            User user = UserLocalServiceUtil.fetchUser(userId);
            if (user == null || user.isDefaultUser()) {
                return null;
            }

            DynamicQuery tableQuery = DynamicQueryFactoryUtil.forClass(
                    com.liferay.expando.kernel.model.ExpandoTable.class,
                    ExpandoValueLocalServiceUtil.class.getClassLoader());

            tableQuery.add(RestrictionsFactoryUtil.eq("companyId", user.getCompanyId()));
            tableQuery.add(RestrictionsFactoryUtil.eq("classNameId",
                    com.liferay.portal.kernel.util.PortalUtil.getClassNameId(User.class)));
            tableQuery.add(RestrictionsFactoryUtil.eq("name", "CUSTOM_FIELDS"));

            List<com.liferay.expando.kernel.model.ExpandoTable> tables =
                    com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil.dynamicQuery(tableQuery);

            if (tables == null || tables.isEmpty()) {
                return null;
            }

            long tableId = tables.get(0).getTableId();

            DynamicQuery columnQuery = DynamicQueryFactoryUtil.forClass(
                    com.liferay.expando.kernel.model.ExpandoColumn.class,
                    ExpandoValueLocalServiceUtil.class.getClassLoader());

            columnQuery.add(RestrictionsFactoryUtil.eq("tableId", tableId));
            columnQuery.add(RestrictionsFactoryUtil.eq("name",
                    FormCounterPortletKeys.USER_EXPANDO_BRANCH_ID));

            List<com.liferay.expando.kernel.model.ExpandoColumn> columns =
                    com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil.dynamicQuery(columnQuery);

            if (columns == null || columns.isEmpty()) {
                return null;
            }

            long columnId = columns.get(0).getColumnId();

            DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
                    ExpandoValue.class,
                    ExpandoValueLocalServiceUtil.class.getClassLoader());

            dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", user.getCompanyId()));
            dynamicQuery.add(RestrictionsFactoryUtil.eq("tableId", tableId));
            dynamicQuery.add(RestrictionsFactoryUtil.eq("columnId", columnId));
            dynamicQuery.add(RestrictionsFactoryUtil.eq("classPK", userId));

            ProjectionList projectionList = ProjectionFactoryUtil.projectionList();
            projectionList.add(ProjectionFactoryUtil.property("data"));
            dynamicQuery.setProjection(projectionList);

            List<String> results = ExpandoValueLocalServiceUtil.dynamicQuery(dynamicQuery);

            if (results != null && !results.isEmpty()) {
                String branchId = GetterUtil.getString(results.get(0));
                if (Validator.isNotNull(branchId)) {
                    return branchId;
                }
            }

            return null;

        } catch (Exception e) {
            _log.error("Error getting branch ID with DynamicQuery for user: " + userId, e);
            return null;
        }
    }

    public static boolean hasValidBranchId(long userId) {
        String branchId = getUserBranchId(userId);
        return Validator.isNotNull(branchId);
    }
}
