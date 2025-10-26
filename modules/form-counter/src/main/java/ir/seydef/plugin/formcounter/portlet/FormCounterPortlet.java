package ir.seydef.plugin.formcounter.portlet;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import ir.seydef.plugin.formcounter.constants.FormCounterPortletKeys;
import ir.seydef.plugin.formcounter.helper.DDMFormService;
import ir.seydef.plugin.formcounter.model.FormInstanceDisplayDTO;
import ir.seydef.plugin.formcounter.model.FormRecordDisplayDTO;
import ir.seydef.plugin.formcounter.model.SearchCriteria;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;
import ir.seydef.plugin.formcounter.util.UserCustomFieldUtil;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = {
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
	},
	service = Portlet.class
)
public class FormCounterPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			Locale locale = themeDisplay.getLocale();
			long groupId = themeDisplay.getScopeGroupId();

			Map<String, List<String>> userCustomFields =
				UserCustomFieldUtil.getUserCustomFieldsWithValues(
					themeDisplay.getUserId());

			List<DDMFormInstance> formInstances =
				DDMFormService.getFormInstancesForUser(
					userCustomFields, groupId);

			SearchCriteria searchCriteria;

			try {
				PortletSession session = renderRequest.getPortletSession();

				searchCriteria = (SearchCriteria)session.getAttribute(
					"searchCriteria");
			}
			catch (Exception exception) {
				searchCriteria = null;
			}

			if (searchCriteria == null) {
				searchCriteria = new SearchCriteria();

				searchCriteria.setUserCustomFields(userCustomFields);

				long selectedFormInstanceId = ParamUtil.getLong(
					renderRequest,
					FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID);

				searchCriteria.setFormInstanceId(selectedFormInstanceId);
			}

			int delta = ParamUtil.getInteger(
				renderRequest, FormCounterPortletKeys.PARAM_DELTA,
				FormCounterPortletKeys.DEFAULT_DELTA);

			int cur = ParamUtil.getInteger(renderRequest, "cur", 1);

			String orderByCol = ParamUtil.getString(
				renderRequest, "orderByCol",
				FormCounterPortletKeys.DEFAULT_ORDER_BY_COL);

			String orderByType = ParamUtil.getString(
				renderRequest, "orderByType",
				FormCounterPortletKeys.DEFAULT_ORDER_BY_TYPE);

			renderRequest.setAttribute("cur", cur);
			renderRequest.setAttribute("delta", delta);
			renderRequest.setAttribute(
				"hasValidCustomFields", !userCustomFields.isEmpty());
			renderRequest.setAttribute("orderByCol", orderByCol);
			renderRequest.setAttribute("orderByType", orderByType);
			renderRequest.setAttribute("searchCriteria", searchCriteria);
			renderRequest.setAttribute(
				"selectedFormInstanceId", searchCriteria.getFormInstanceId());

			List<FormInstanceDisplayDTO> formInstanceDTOs =
				_convertToFormInstanceDTOs(
					formInstances, locale, userCustomFields, groupId);

			renderRequest.setAttribute("formInstances", formInstanceDTOs);

			List<FormRecordDisplayDTO> formRecords = new ArrayList<>();
			int totalCount = 0;

			if (!userCustomFields.isEmpty()) {
				int start = (cur - 1) * delta;

				int end = start + delta;

				if (searchCriteria.hasSearchCriteria() ||
					(searchCriteria.getFormInstanceId() > 0)) {

					List<DDMFormInstanceRecord> records =
						DDMFormService.searchFormRecords(
							searchCriteria, start, end, orderByCol,
							orderByType);

					formRecords = _convertToFormRecordDTOs(records, locale);

					totalCount = records.size();

					if (totalCount == 0) {
						SessionErrors.add(renderRequest, "noRecordsFound");
					}
				}
				else {
					List<DDMFormInstanceRecord> records =
						DDMFormService.getFilteredFormRecords(
							searchCriteria.getFormInstanceId(),
							userCustomFields, start, end, orderByCol,
							orderByType);

					formRecords = _convertToFormRecordDTOs(records, locale);
					totalCount = records.size();
				}
			}

			renderRequest.setAttribute("formRecords", formRecords);
			renderRequest.setAttribute("totalCount", totalCount);

			long unseenCount = formRecords.stream(
			).filter(
				record -> !record.isSeen()
			).count();

			renderRequest.setAttribute("unseenCount", unseenCount);

			int totalPages = (int)Math.ceil((double)totalCount / delta);

			renderRequest.setAttribute("totalPages", totalPages);

			renderRequest.setAttribute("currentPage", cur);

			SessionMessages.add(
				renderRequest,
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		}
		catch (Exception exception) {
			renderRequest.setAttribute(
				"errorMessage",
				"An error occurred while loading the form records.");
		}

		super.doView(renderRequest, renderResponse);
	}

	public void searchRecords(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			Map<String, List<String>> userCustomFields =
				UserCustomFieldUtil.getUserCustomFieldsWithValues(
					themeDisplay.getUserId());

			SearchCriteria searchCriteria = new SearchCriteria();

			searchCriteria.setUserCustomFields(userCustomFields);

			searchCriteria.setFormInstanceId(
				ParamUtil.getLong(
					actionRequest,
					FormCounterPortletKeys.PARAM_FORM_INSTANCE_ID));

			String registrantName = ParamUtil.getString(
				actionRequest, FormCounterPortletKeys.PARAM_REGISTRANT_NAME);
			String formNumber = ParamUtil.getString(
				actionRequest, FormCounterPortletKeys.PARAM_FORM_NUMBER);
			String trackingCode = ParamUtil.getString(
				actionRequest, FormCounterPortletKeys.PARAM_TRACKING_CODE);
			String formName = ParamUtil.getString(
				actionRequest, FormCounterPortletKeys.PARAM_FORM_NAME);

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

			String startDateStr = ParamUtil.getString(
				actionRequest, FormCounterPortletKeys.PARAM_START_DATE);
			String endDateStr = ParamUtil.getString(
				actionRequest, FormCounterPortletKeys.PARAM_END_DATE);

			if (Validator.isNotNull(startDateStr)) {
				try {
					searchCriteria.setStartDate(
						_DATE_FORMAT.parse(startDateStr));
				}
				catch (ParseException parseException) {
					_log.warn(
						"Invalid start date format: " + startDateStr,
						parseException);
				}
			}

			if (Validator.isNotNull(endDateStr)) {
				try {
					searchCriteria.setEndDate(_DATE_FORMAT.parse(endDateStr));
				}
				catch (ParseException parseException) {
					_log.warn(
						"Invalid end date format: " + endDateStr,
						parseException);
				}
			}

			String status = ParamUtil.getString(
				actionRequest, FormCounterPortletKeys.PARAM_STATUS);

			if (Validator.isNotNull(status) && !status.equals("all")) {
				searchCriteria.setStatus(status);
			}

			PortletSession session = actionRequest.getPortletSession();

			session.setAttribute("searchCriteria", searchCriteria);

			SessionMessages.add(
				actionRequest,
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}
		catch (Exception exception) {
			_log.error("Error processing search action", exception);
			actionRequest.setAttribute("errorMessage", "search.error.occurred");
		}
	}

	private List<FormInstanceDisplayDTO> _convertToFormInstanceDTOs(
		List<DDMFormInstance> formInstances, Locale locale,
		Map<String, List<String>> userCustomFields, long groupId) {

		List<FormInstanceDisplayDTO> dtos = new ArrayList<>();

		for (DDMFormInstance formInstance : formInstances) {
			FormInstanceDisplayDTO dto = new FormInstanceDisplayDTO(
				formInstance, locale);

			if ((userCustomFields != null) && !userCustomFields.isEmpty()) {
				int count = DDMFormService.getFilteredFormRecordsCount(
					formInstance.getFormInstanceId(), userCustomFields);

				if (count == 0) {
					continue;
				}

				dto.setRecordCount(count);
			}

			try {
				dto.setUnseenCount(
					FormSubmissionStatusLocalServiceUtil.
						getUnseenByFormInstanceId(
							formInstance.getFormInstanceId(), groupId
						).size());
			}
			catch (Exception exception) {
				dto.setUnseenCount(0);
			}

			dtos.add(dto);
		}

		return dtos;
	}

	private List<FormRecordDisplayDTO> _convertToFormRecordDTOs(
		List<DDMFormInstanceRecord> records, Locale locale) {

		List<FormRecordDisplayDTO> dtos = new ArrayList<>();

		for (DDMFormInstanceRecord record : records) {
			try {
				DDMFormInstance formInstance = DDMFormService.getFormInstance(
					record.getFormInstanceId());

				FormRecordDisplayDTO dto = new FormRecordDisplayDTO(
					record, formInstance, locale);

				dtos.add(dto);
			}
			catch (Exception exception) {
				_log.warn(
					"Error converting record to DTO: " +
						record.getFormInstanceRecordId(),
					exception);
			}
		}

		dtos.sort(
			(dto1, dto2) -> {
				if (dto1.isSeen() == dto2.isSeen()) {
					return dto2.getCreateDate(
					).compareTo(
						dto1.getCreateDate()
					);
				}

				if (dto1.isSeen()) {
					return 1;
				}

				return -1;
			});

		return dtos;
	}

	private static final SimpleDateFormat _DATE_FORMAT = new SimpleDateFormat(
		"MM/dd/yyyy");

	private static final Log _log = LogFactoryUtil.getLog(
		FormCounterPortlet.class);

}