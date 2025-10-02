/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.exception.PortalException;

import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;

import java.util.List;

/**
 * Provides the remote service utility for FormSubmissionStatus. This utility wraps
 * <code>ir.seydef.plugin.formcounter.service.impl.FormSubmissionStatusServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusService
 * @generated
 */
public class FormSubmissionStatusServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>ir.seydef.plugin.formcounter.service.impl.FormSubmissionStatusServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Creates a new FormSubmissionStatus entry for a form instance record
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param serviceContext       the service context
	 * @return the created FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public static FormSubmissionStatus addFormSubmissionStatus(
			long formInstanceRecordId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFormSubmissionStatus(
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
	public static FormSubmissionStatus createOrUpdate(
			long formInstanceRecordId, boolean seen,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().createOrUpdate(
			formInstanceRecordId, seen, serviceContext);
	}

	/**
	 * Gets the FormSubmissionStatus by form instance record ID
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the FormSubmissionStatus, or null if not found
	 * @throws PortalException if a portal exception occurred
	 */
	public static FormSubmissionStatus getByFormInstanceRecordId(
			long formInstanceRecordId)
		throws PortalException {

		return getService().getByFormInstanceRecordId(formInstanceRecordId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * Gets all seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of seen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	public static List<FormSubmissionStatus> getSeenByGroupId(long groupId)
		throws PortalException {

		return getService().getSeenByGroupId(groupId);
	}

	/**
	 * Gets count of seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of seen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getSeenCountByGroupId(long groupId)
		throws PortalException {

		return getService().getSeenCountByGroupId(groupId);
	}

	/**
	 * Gets unseen form submissions by form instance ID
	 *
	 * @param formInstanceId the form instance ID
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries for the specific form instance
	 * @throws PortalException if a portal exception occurred
	 */
	public static List<FormSubmissionStatus> getUnseenByFormInstanceId(
			long formInstanceId, long groupId)
		throws PortalException {

		return getService().getUnseenByFormInstanceId(formInstanceId, groupId);
	}

	/**
	 * Gets all unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	public static List<FormSubmissionStatus> getUnseenByGroupId(long groupId)
		throws PortalException {

		return getService().getUnseenByGroupId(groupId);
	}

	/**
	 * Gets count of unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of unseen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getUnseenCountByGroupId(long groupId)
		throws PortalException {

		return getService().getUnseenCountByGroupId(groupId);
	}

	/**
	 * Checks if a form submission is seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return true if seen, false otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	public static boolean isSeen(long formInstanceRecordId)
		throws PortalException {

		return getService().isSeen(formInstanceRecordId);
	}

	/**
	 * Marks a form submission as seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param userId               the user ID who marked it as seen
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public static FormSubmissionStatus markAsSeen(
			long formInstanceRecordId, long userId)
		throws PortalException {

		return getService().markAsSeen(formInstanceRecordId, userId);
	}

	/**
	 * Marks a form submission as unseen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public static FormSubmissionStatus markAsUnseen(long formInstanceRecordId)
		throws PortalException {

		return getService().markAsUnseen(formInstanceRecordId);
	}

	public static FormSubmissionStatusService getService() {
		return _service;
	}

	public static void setService(FormSubmissionStatusService service) {
		_service = service;
	}

	private static volatile FormSubmissionStatusService _service;

}