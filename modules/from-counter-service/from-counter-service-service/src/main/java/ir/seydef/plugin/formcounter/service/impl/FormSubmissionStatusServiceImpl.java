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

	/**
	 * Creates a new FormSubmissionStatus entry for a form instance record
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param serviceContext       the service context
	 * @return the created FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public FormSubmissionStatus addFormSubmissionStatus(
			long formInstanceRecordId, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		return formSubmissionStatusLocalService.addFormSubmissionStatus(
			formInstanceRecordId, serviceContext);
	}

	/**
	 * Creates or updates FormSubmissionStatus for a form instance record
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param seen                 the seen status
	 * @param serviceContext       the service context
	 * @return the FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
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

	/**
	 * Gets the FormSubmissionStatus by form instance record ID
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the FormSubmissionStatus, or null if not found
	 * @throws PortalException if a portal exception occurred
	 */
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

	/**
	 * Gets all seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of seen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	public List<FormSubmissionStatus> getSeenByGroupId(long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getSeenByGroupId(groupId);
	}

	/**
	 * Gets count of seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of seen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	public int getSeenCountByGroupId(long groupId) throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getSeenCountByGroupId(groupId);
	}

	/**
	 * Gets unseen form submissions by form instance ID
	 *
	 * @param formInstanceId the form instance ID
	 * @param groupId        the group ID
	 * @return list of unseen FormSubmissionStatus entries for the specific form instance
	 * @throws PortalException if a portal exception occurred
	 */
	public List<FormSubmissionStatus> getUnseenByFormInstanceId(
			long formInstanceId, long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getUnseenByFormInstanceId(
			formInstanceId, groupId);
	}

	/**
	 * Gets all unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	public List<FormSubmissionStatus> getUnseenByGroupId(long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getUnseenByGroupId(groupId);
	}

	/**
	 * Gets count of unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of unseen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	public int getUnseenCountByGroupId(long groupId) throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return formSubmissionStatusLocalService.getUnseenCountByGroupId(
			groupId);
	}

	/**
	 * Checks if a form submission is seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return true if seen, false otherwise
	 * @throws PortalException if a portal exception occurred
	 */
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

	/**
	 * Marks a form submission as seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param userId               the user ID who marked it as seen
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
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

	/**
	 * Marks a form submission as unseen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
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