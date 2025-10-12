/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.service.base.FormSubmissionStatusServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = {
		"json.web.service.context.name=formcounter",
		"json.web.service.context.path=FormSubmissionStatus"
	},
	service = AopService.class
)
public class FormSubmissionStatusServiceImpl
	extends FormSubmissionStatusServiceBaseImpl {

	public FormSubmissionStatus addFormSubmissionStatus(
			long formInstanceRecordId, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		return formSubmissionStatusLocalService.addFormSubmissionStatus(
			formInstanceRecordId, serviceContext);
	}

	public FormSubmissionStatus createOrUpdate(
			long formInstanceRecordId, boolean seen,
			ServiceContext serviceContext)
		throws PortalException {

		FormSubmissionStatus existing =
			formSubmissionStatusLocalService.getByFormInstanceRecordId(
				formInstanceRecordId);

		if (existing != null) {
			_formSubmissionStatusModelResourcePermission.check(
				getPermissionChecker(), existing, ActionKeys.UPDATE);
		}
		else {
			_portletResourcePermission.check(
				getPermissionChecker(), serviceContext.getScopeGroupId(),
				ActionKeys.ADD_ENTRY);
		}

		return formSubmissionStatusLocalService.createOrUpdate(
			formInstanceRecordId, seen, serviceContext);
	}

	public FormSubmissionStatus getByFormInstanceRecordId(
			long formInstanceRecordId)
		throws PortalException {

		FormSubmissionStatus formSubmissionStatus =
			formSubmissionStatusLocalService.getByFormInstanceRecordId(
				formInstanceRecordId);

		if (formSubmissionStatus != null) {
			_formSubmissionStatusModelResourcePermission.check(
				getPermissionChecker(), formSubmissionStatus, ActionKeys.VIEW);
		}

		return formSubmissionStatus;
	}

	public List<FormSubmissionStatus> getSeenByGroupId(long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getSeenByGroupId(groupId);
	}

	public int getSeenCountByGroupId(long groupId) throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getSeenCountByGroupId(groupId);
	}

	public List<FormSubmissionStatus> getUnseenByFormInstanceId(
			long formInstanceId, long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getUnseenByFormInstanceId(
			formInstanceId, groupId);
	}

	public List<FormSubmissionStatus> getUnseenByGroupId(long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getUnseenByGroupId(groupId);
	}

	public int getUnseenCountByGroupId(long groupId) throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getUnseenCountByGroupId(
			groupId);
	}

	public boolean isSeen(long formInstanceRecordId) throws PortalException {
		FormSubmissionStatus formSubmissionStatus =
			formSubmissionStatusLocalService.getByFormInstanceRecordId(
				formInstanceRecordId);

		if (formSubmissionStatus != null) {
			_formSubmissionStatusModelResourcePermission.check(
				getPermissionChecker(), formSubmissionStatus, ActionKeys.VIEW);
		}

		return formSubmissionStatusLocalService.isSeen(formInstanceRecordId);
	}

	public FormSubmissionStatus markAsSeen(
			long formInstanceRecordId, long userId)
		throws PortalException {

		FormSubmissionStatus formSubmissionStatus =
			formSubmissionStatusLocalService.getByFormInstanceRecordId(
				formInstanceRecordId);

		if (formSubmissionStatus != null) {
			_formSubmissionStatusModelResourcePermission.check(
				getPermissionChecker(), formSubmissionStatus,
				ActionKeys.UPDATE);
		}

		return formSubmissionStatusLocalService.markAsSeen(
			formInstanceRecordId, userId);
	}

	public FormSubmissionStatus markAsUnseen(long formInstanceRecordId)
		throws PortalException {

		FormSubmissionStatus formSubmissionStatus =
			formSubmissionStatusLocalService.getByFormInstanceRecordId(
				formInstanceRecordId);

		if (formSubmissionStatus != null) {
			_formSubmissionStatusModelResourcePermission.check(
				getPermissionChecker(), formSubmissionStatus,
				ActionKeys.UPDATE);
		}

		return formSubmissionStatusLocalService.markAsUnseen(
			formInstanceRecordId);
	}

	@Reference
	private ModelResourcePermission<FormSubmissionStatus>
		_formSubmissionStatusModelResourcePermission;

	@Reference
	private PortletResourcePermission _portletResourcePermission;

}