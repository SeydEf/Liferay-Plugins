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
	public static FormSubmissionStatus addFormSubmissionStatus(
			long formInstanceRecordId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFormSubmissionStatus(
			formInstanceRecordId, serviceContext);
	}

	public static FormSubmissionStatus createOrUpdate(
			long formInstanceRecordId, boolean seen,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().createOrUpdate(
			formInstanceRecordId, seen, serviceContext);
	}

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

	public static List<FormSubmissionStatus> getSeenByGroupId(long groupId)
		throws PortalException {

		return getService().getSeenByGroupId(groupId);
	}

	public static int getSeenCountByGroupId(long groupId)
		throws PortalException {

		return getService().getSeenCountByGroupId(groupId);
	}

	public static List<FormSubmissionStatus> getUnseenByFormInstanceId(
			long formInstanceId, long groupId)
		throws PortalException {

		return getService().getUnseenByFormInstanceId(formInstanceId, groupId);
	}

	public static List<FormSubmissionStatus> getUnseenByGroupId(long groupId)
		throws PortalException {

		return getService().getUnseenByGroupId(groupId);
	}

	public static int getUnseenCountByGroupId(long groupId)
		throws PortalException {

		return getService().getUnseenCountByGroupId(groupId);
	}

	public static boolean isSeen(long formInstanceRecordId)
		throws PortalException {

		return getService().isSeen(formInstanceRecordId);
	}

	public static FormSubmissionStatus markAsSeen(
			long formInstanceRecordId, long userId)
		throws PortalException {

		return getService().markAsSeen(formInstanceRecordId, userId);
	}

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