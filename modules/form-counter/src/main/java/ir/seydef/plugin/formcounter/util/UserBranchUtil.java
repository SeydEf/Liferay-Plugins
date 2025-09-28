package ir.seydef.plugin.formcounter.util;

import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import ir.seydef.plugin.formcounter.constants.FormCounterPortletKeys;

/**
 * @author S.Abolfazl Eftekhari
 */
public class UserBranchUtil {

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

            if (expandoValue != null) {
                String branchId = GetterUtil.getString(expandoValue.getData());
                if (Validator.isNotNull(branchId)) {
                    return branchId;
                }
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    public static boolean hasValidBranchId(long userId) {
        String branchId = getUserBranchId(userId);
        return Validator.isNotNull(branchId);
    }
}