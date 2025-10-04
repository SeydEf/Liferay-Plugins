package ir.seydef.plugin.formcounter.portlet;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import ir.seydef.plugin.formcounter.constants.FormCounterPortletKeys;
import ir.seydef.plugin.formcounter.model.FormInstanceDisplayDTO;
import ir.seydef.plugin.formcounter.model.FormRecordDisplayDTO;
import ir.seydef.plugin.formcounter.model.SearchCriteria;
import ir.seydef.plugin.formcounter.serviceHelper.DDMFormService;
import ir.seydef.plugin.formcounter.serviceHelper.FormStatusSyncService;
import ir.seydef.plugin.formcounter.util.UserBranchUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Reference
    protected FormStatusSyncService formStatusSyncService;

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        try {
            ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
            Locale locale = themeDisplay.getLocale();
            long userId = themeDisplay.getUserId();
            long groupId = themeDisplay.getScopeGroupId();

            String userBranchId = UserBranchUtil.getUserBranchId(userId);
            List<DDMFormInstance> formInstances = DDMFormService.getFormInstancesWithBranchId(groupId, locale);

            try {
                ServiceContext serviceContext = ServiceContextFactory.getInstance(renderRequest);
                formStatusSyncService.syncFormSubmissionStatuses(formInstances, serviceContext);
            } catch (Exception e) {
                _log.warn("Error during background form status synchronization", e);
            }

            PortletSession session = renderRequest.getPortletSession();
            SearchCriteria searchCriteria = (SearchCriteria) session.getAttribute("searchCriteria");

            if (searchCriteria == null) {
                searchCriteria = new SearchCriteria();
                searchCriteria.setUserBranchId(userBranchId);
                long selectedFormInstanceId = ParamUtil.getLong(renderRequest,
                        FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID, 0);
                searchCriteria.setFormInstanceId(selectedFormInstanceId);
            }

            int delta = ParamUtil.getInteger(renderRequest, FormCounterPortletKeys.PARAM_DELTA,
                    FormCounterPortletKeys.DEFAULT_DELTA);
            int cur = ParamUtil.getInteger(renderRequest, "cur", 1);
            String orderByCol = ParamUtil.getString(renderRequest, "orderByCol",
                    FormCounterPortletKeys.DEFAULT_ORDER_BY_COL);
            String orderByType = ParamUtil.getString(renderRequest, "orderByType",
                    FormCounterPortletKeys.DEFAULT_ORDER_BY_TYPE);

            renderRequest.setAttribute("userBranchId", userBranchId);
            renderRequest.setAttribute("hasValidBranchId", UserBranchUtil.hasValidBranchId(userId));
            renderRequest.setAttribute("selectedFormInstanceId", searchCriteria.getFormInstanceId());
            renderRequest.setAttribute("searchCriteria", searchCriteria);
            renderRequest.setAttribute("delta", delta);
            renderRequest.setAttribute("cur", cur);
            renderRequest.setAttribute("orderByCol", orderByCol);
            renderRequest.setAttribute("orderByType", orderByType);

            List<FormInstanceDisplayDTO> formInstanceDTOs = convertToFormInstanceDTOs(formInstances, locale,
                    userBranchId);
            renderRequest.setAttribute("formInstances", formInstanceDTOs);

            List<FormRecordDisplayDTO> formRecords = new ArrayList<>();
            int totalCount = 0;

            if (UserBranchUtil.hasValidBranchId(userId)) {
                if (searchCriteria.hasSearchCriteria() || searchCriteria.getFormInstanceId() > 0) {
                    totalCount = DDMFormService.getSearchFormRecordsCount(searchCriteria);
                } else {
                    totalCount = DDMFormService.getFilteredFormRecordsCount(
                            searchCriteria.getFormInstanceId(), userBranchId);
                }

                int start = (cur - 1) * delta;
                int end = start + delta;

                if (searchCriteria.hasSearchCriteria() || searchCriteria.getFormInstanceId() > 0) {
                    List<DDMFormInstanceRecord> records = DDMFormService.searchFormRecords(
                            searchCriteria, start, end, orderByCol, orderByType);
                    formRecords = convertToFormRecordDTOs(records, locale);
                } else {
                    List<DDMFormInstanceRecord> records = DDMFormService.getFilteredFormRecords(
                            searchCriteria.getFormInstanceId(), userBranchId, start, end, orderByCol, orderByType);
                    formRecords = convertToFormRecordDTOs(records, locale);
                }
            }

            renderRequest.setAttribute("formRecords", formRecords);
            renderRequest.setAttribute("totalCount", totalCount);

            long unseenCount = formRecords.stream().filter(record -> !record.isSeen()).count();
            renderRequest.setAttribute("unseenCount", unseenCount);

            int totalPages = (int) Math.ceil((double) totalCount / delta);
            renderRequest.setAttribute("totalPages", totalPages);
            renderRequest.setAttribute("currentPage", cur);

        } catch (Exception e) {
            renderRequest.setAttribute("errorMessage", "An error occurred while loading the form records.");
        }

        super.doView(renderRequest, renderResponse);
    }

    public void searchRecords(ActionRequest actionRequest, ActionResponse actionResponse)
            throws IOException, PortletException {

        try {
            ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
            long userId = themeDisplay.getUserId();
            String userBranchId = UserBranchUtil.getUserBranchId(userId);

            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setUserBranchId(userBranchId);

            long formInstanceId = ParamUtil.getLong(actionRequest, FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID, 0);
            searchCriteria.setFormInstanceId(formInstanceId);

            String registrantName = ParamUtil.getString(actionRequest, FormCounterPortletKeys.PARAM_REGISTRANT_NAME);
            String formNumber = ParamUtil.getString(actionRequest, FormCounterPortletKeys.PARAM_FORM_NUMBER);
            String trackingCode = ParamUtil.getString(actionRequest, FormCounterPortletKeys.PARAM_TRACKING_CODE);
            String formName = ParamUtil.getString(actionRequest, FormCounterPortletKeys.PARAM_FORM_NAME);

            if (Validator.isNotNull(registrantName)) {
                searchCriteria.setRegistrantName(registrantName.trim());
            }
            if (Validator.isNotNull(formNumber)) {
                searchCriteria.setFormNumber(formNumber.trim());
            }
            if (Validator.isNotNull(trackingCode)) {
                searchCriteria.setTrackingCode(trackingCode.trim());
            }
            if (Validator.isNotNull(formName)) {
                searchCriteria.setFormName(formName.trim());
            }

            String startDateStr = ParamUtil.getString(actionRequest, FormCounterPortletKeys.PARAM_START_DATE);
            String endDateStr = ParamUtil.getString(actionRequest, FormCounterPortletKeys.PARAM_END_DATE);

            if (Validator.isNotNull(startDateStr)) {
                try {
                    Date startDate = DATE_FORMAT.parse(startDateStr);
                    searchCriteria.setStartDate(startDate);
                } catch (ParseException e) {
                    _log.warn("Invalid start date format: " + startDateStr, e);
                }
            }

            if (Validator.isNotNull(endDateStr)) {
                try {
                    Date endDate = DATE_FORMAT.parse(endDateStr);
                    searchCriteria.setEndDate(endDate);
                } catch (ParseException e) {
                    _log.warn("Invalid end date format: " + endDateStr, e);
                }
            }

            String status = ParamUtil.getString(actionRequest, FormCounterPortletKeys.PARAM_STATUS);
            if (Validator.isNotNull(status) && !"all".equals(status)) {
                searchCriteria.setStatus(status);
            }

            PortletSession session = actionRequest.getPortletSession();
            session.setAttribute("searchCriteria", searchCriteria);

        } catch (Exception e) {
            _log.error("Error processing search action", e);
            actionRequest.setAttribute("errorMessage", "search.error.occurred");
        }
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

        dtos.sort((dto1, dto2) -> {
            if (dto1.isSeen() == dto2.isSeen()) {
                return dto2.getCreateDate().compareTo(dto1.getCreateDate());
            }
            return dto1.isSeen() ? 1 : -1;
        });

        return dtos;
    }
}
