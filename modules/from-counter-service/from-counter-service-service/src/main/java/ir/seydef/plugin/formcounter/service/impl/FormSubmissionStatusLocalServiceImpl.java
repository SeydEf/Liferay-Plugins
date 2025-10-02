/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.service.base.FormSubmissionStatusLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = "model.class.name=ir.seydef.plugin.formcounter.model.FormSubmissionStatus",
	service = AopService.class
)
public class FormSubmissionStatusLocalServiceImpl
	extends FormSubmissionStatusLocalServiceBaseImpl {

	/**
	 * Creates a new FormSubmissionStatus entry for a form instance record
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param serviceContext the service context
	 * @return the created FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public FormSubmissionStatus addFormSubmissionStatus(
			long formInstanceRecordId, ServiceContext serviceContext)
		throws PortalException {

		// Generate primary key
		long formSubmissionStatusId = counterLocalService.increment();

		// Create the entity
		FormSubmissionStatus formSubmissionStatus =
			formSubmissionStatusPersistence.create(formSubmissionStatusId);

		// Set form instance record ID
		formSubmissionStatus.setFormInstanceRecordId(formInstanceRecordId);

		// Set status fields - initially unseen
		formSubmissionStatus.setSeen(false);
		formSubmissionStatus.setSeenDate(null);

		// Set audit fields
		User user = _userLocalService.getUser(serviceContext.getUserId());
		Date now = new Date();

		formSubmissionStatus.setCompanyId(serviceContext.getCompanyId());
		formSubmissionStatus.setGroupId(serviceContext.getScopeGroupId());
		formSubmissionStatus.setUserId(user.getUserId());
		formSubmissionStatus.setUserName(user.getFullName());
		formSubmissionStatus.setCreateDate(now);
		formSubmissionStatus.setModifiedDate(now);

		return formSubmissionStatusPersistence.update(formSubmissionStatus);
	}

	/**
	 * Marks a form submission as seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param userId the user ID who marked it as seen
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public FormSubmissionStatus markAsSeen(long formInstanceRecordId, long userId)
		throws PortalException {

		FormSubmissionStatus formSubmissionStatus =
			formSubmissionStatusPersistence.findByFormInstanceRecordId(formInstanceRecordId);

		formSubmissionStatus.setSeen(true);
		formSubmissionStatus.setSeenDate(new Date());
		formSubmissionStatus.setModifiedDate(new Date());

		return formSubmissionStatusPersistence.update(formSubmissionStatus);
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
			formSubmissionStatusPersistence.findByFormInstanceRecordId(formInstanceRecordId);

		formSubmissionStatus.setSeen(false);
		formSubmissionStatus.setSeenDate(null);
		formSubmissionStatus.setModifiedDate(new Date());

		return formSubmissionStatusPersistence.update(formSubmissionStatus);
	}

	/**
	 * Gets the FormSubmissionStatus by form instance record ID
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the FormSubmissionStatus, or null if not found
	 */
	public FormSubmissionStatus getByFormInstanceRecordId(long formInstanceRecordId) {
		try {
			return formSubmissionStatusPersistence.findByFormInstanceRecordId(formInstanceRecordId);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Checks if a form submission is seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return true if seen, false otherwise
	 */
	public boolean isSeen(long formInstanceRecordId) {
		FormSubmissionStatus status = getByFormInstanceRecordId(formInstanceRecordId);
		return status != null && status.isSeen();
	}

	/**
	 * Gets all unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries
	 */
	public List<FormSubmissionStatus> getUnseenByGroupId(long groupId) {
		return formSubmissionStatusPersistence.findByG_S(groupId, false);
	}

	/**
	 * Gets all seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of seen FormSubmissionStatus entries
	 */
	public List<FormSubmissionStatus> getSeenByGroupId(long groupId) {
		return formSubmissionStatusPersistence.findByG_S(groupId, true);
	}

	/**
	 * Gets count of unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of unseen submissions
	 */
	public int getUnseenCountByGroupId(long groupId) {
		return formSubmissionStatusPersistence.countByG_S(groupId, false);
	}

	/**
	 * Gets count of seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of seen submissions
	 */
	public int getSeenCountByGroupId(long groupId) {
		return formSubmissionStatusPersistence.countByG_S(groupId, true);
	}

	/**
	 * Gets unseen form submissions by form instance ID
	 *
	 * @param formInstanceId the form instance ID
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries for the specific form instance
	 */
	public List<FormSubmissionStatus> getUnseenByFormInstanceId(long formInstanceId, long groupId) {
		try {
			// Use dynamic query to filter by form instance ID
			com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery =
				dynamicQuery()
					.add(com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.eq("groupId", groupId))
					.add(com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.eq("seen", false));

			// Get all unseen submissions for the group
			List<FormSubmissionStatus> unseenSubmissions = dynamicQuery(dynamicQuery);

			// Filter by form instance ID by checking the form instance record
			List<FormSubmissionStatus> result = new java.util.ArrayList<>();
			for (FormSubmissionStatus status : unseenSubmissions) {
				try {
					// Get the form instance record and check its form instance ID
					com.liferay.dynamic.data.lists.model.DDLRecord record =
						com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil.getDDLRecord(
							status.getFormInstanceRecordId());

					if (record.getRecordSetId() == formInstanceId) {
						result.add(status);
					}
				} catch (Exception e) {
					// Skip records that can't be found or accessed
					continue;
				}
			}

			return result;
		} catch (Exception e) {
			return java.util.Collections.emptyList();
		}
	}

	/**
	 * Creates or updates FormSubmissionStatus for a form instance record
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param seen the seen status
	 * @param serviceContext the service context
	 * @return the FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public FormSubmissionStatus createOrUpdate(
			long formInstanceRecordId, boolean seen, ServiceContext serviceContext)
		throws PortalException {

		FormSubmissionStatus existing = getByFormInstanceRecordId(formInstanceRecordId);

		if (existing != null) {
			// Update existing
			existing.setSeen(seen);
			existing.setSeenDate(seen ? new Date() : null);
			existing.setModifiedDate(new Date());
			return formSubmissionStatusPersistence.update(existing);
		} else {
			// Create new
			FormSubmissionStatus newStatus = addFormSubmissionStatus(formInstanceRecordId, serviceContext);
			if (seen) {
				newStatus.setSeen(true);
				newStatus.setSeenDate(new Date());
				newStatus = formSubmissionStatusPersistence.update(newStatus);
			}
			return newStatus;
		}
	}

	@Reference
	private UserLocalService _userLocalService;
}