package ir.seydef.plugin.formcounter.portlet;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import ir.seydef.plugin.formcounter.constants.FormCounterPortletKeys;
import ir.seydef.plugin.formcounter.model.FormInstanceDisplayDTO;
import ir.seydef.plugin.formcounter.model.FormRecordDisplayDTO;
import ir.seydef.plugin.formcounter.service.DDMFormService;
import ir.seydef.plugin.formcounter.util.UserBranchUtil;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(property = {
        "com.liferay.portlet.add-default-resource=true",
        "com.liferay.portlet.display-category=category.hidden",
        "com.liferay.portlet.header-portlet-css=/css/main.css",
        "com.liferay.portlet.layout-cacheable=true",
        "com.liferay.portlet.private-request-attributes=false",
        "com.liferay.portlet.private-session-attributes=false",
        "com.liferay.portlet.render-weight=50",
        "com.liferay.portlet.use-default-template=true",
        "javax.portlet.display-name=DDM Form Records Viewer",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.name=" + FormCounterPortletKeys.FORMCOUNTER,
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user",
        "javax.portlet.supported-locale=en,fa"
}, service = Portlet.class)
public class FormCounterPortlet extends MVCPortlet {

    private static final Log _log = LogFactoryUtil.getLog(FormCounterPortlet.class);

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        try {
            ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
            Locale locale = themeDisplay.getLocale();
            long userId = themeDisplay.getUserId();
            long groupId = themeDisplay.getScopeGroupId();

            String userBranchId = UserBranchUtil.getUserBranchId(userId);

            long selectedFormInstanceId = ParamUtil.getLong(renderRequest,
                    FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID, 0);
            int delta = ParamUtil.getInteger(renderRequest, FormCounterPortletKeys.PARAM_DELTA,
                    FormCounterPortletKeys.DEFAULT_DELTA);
            int cur = ParamUtil.getInteger(renderRequest, "cur", 1);
            String orderByCol = ParamUtil.getString(renderRequest, "orderByCol",
                    FormCounterPortletKeys.DEFAULT_ORDER_BY_COL);
            String orderByType = ParamUtil.getString(renderRequest, "orderByType",
                    FormCounterPortletKeys.DEFAULT_ORDER_BY_TYPE);

            renderRequest.setAttribute("userBranchId", userBranchId);
            renderRequest.setAttribute("hasValidBranchId", UserBranchUtil.hasValidBranchId(userId));
            renderRequest.setAttribute("selectedFormInstanceId", selectedFormInstanceId);
            renderRequest.setAttribute("delta", delta);
            renderRequest.setAttribute("cur", cur);
            renderRequest.setAttribute("orderByCol", orderByCol);
            renderRequest.setAttribute("orderByType", orderByType);

            List<DDMFormInstance> formInstances = DDMFormService.getFormInstancesWithBranchId(groupId, locale);
            List<FormInstanceDisplayDTO> formInstanceDTOs = convertToFormInstanceDTOs(formInstances, locale,
                    userBranchId);
            renderRequest.setAttribute("formInstances", formInstanceDTOs);

            List<FormRecordDisplayDTO> formRecords = new ArrayList<>();
            int totalCount = 0;

            if (UserBranchUtil.hasValidBranchId(userId)) {
                int start = (cur - 1) * delta;
                int end = start + delta;

                List<DDMFormInstanceRecord> records = DDMFormService.getFilteredFormRecords(
                        selectedFormInstanceId, userBranchId, start, end, orderByCol, orderByType);

                formRecords = convertToFormRecordDTOs(records, locale);

                totalCount = DDMFormService.getFilteredFormRecordsCount(selectedFormInstanceId, userBranchId);
            }

            renderRequest.setAttribute("formRecords", formRecords);
            renderRequest.setAttribute("totalCount", totalCount);

            int totalPages = (int) Math.ceil((double) totalCount / delta);
            renderRequest.setAttribute("totalPages", totalPages);
            renderRequest.setAttribute("currentPage", cur);

        } catch (Exception e) {
            renderRequest.setAttribute("errorMessage", "An error occurred while loading the form records.");
        }

        super.doView(renderRequest, renderResponse);
    }

    private List<FormInstanceDisplayDTO> convertToFormInstanceDTOs(List<DDMFormInstance> formInstances,
                                                                   Locale locale, String userBranchId) {
        List<FormInstanceDisplayDTO> dtos = new ArrayList<>();

        for (DDMFormInstance formInstance : formInstances) {
            FormInstanceDisplayDTO dto = new FormInstanceDisplayDTO(formInstance, locale, true);

            if (Validator.isNotNull(userBranchId)) {
                int count = DDMFormService.getFilteredFormRecordsCount(formInstance.getFormInstanceId(), userBranchId);
                dto.setRecordCount(count);
            }

            dtos.add(dto);
        }

        return dtos;
    }

    private List<FormRecordDisplayDTO> convertToFormRecordDTOs(List<DDMFormInstanceRecord> records, Locale locale) {
        List<FormRecordDisplayDTO> dtos = new ArrayList<>();

        for (DDMFormInstanceRecord record : records) {
            try {
                DDMFormInstance formInstance = DDMFormService.getFormInstance(record.getFormInstanceId());

                String branchId = DDMFormService.extractBranchIdFromRecord(record);

                FormRecordDisplayDTO dto = new FormRecordDisplayDTO(record, formInstance, branchId, locale);
                dtos.add(dto);

            } catch (Exception e) {
                _log.warn("Error converting record to DTO: " + record.getFormInstanceRecordId(), e);
            }
        }
        return dtos;
    }
}