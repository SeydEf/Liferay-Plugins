/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FormSubmissionStatusService}.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusService
 * @generated
 */
public class FormSubmissionStatusServiceWrapper
	implements FormSubmissionStatusService,
			   ServiceWrapper<FormSubmissionStatusService> {

	public FormSubmissionStatusServiceWrapper(
		FormSubmissionStatusService formSubmissionStatusService) {

		_formSubmissionStatusService = formSubmissionStatusService;
	}

	/**
	 * Creates a new FormSubmissionStatus entry for a form instance record
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param serviceContext       the service context
	 * @return the created FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			addFormSubmissionStatus(
				long formInstanceRecordId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.addFormSubmissionStatus(
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
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			createOrUpdate(
				long formInstanceRecordId, boolean seen,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.createOrUpdate(
			formInstanceRecordId, seen, serviceContext);
	}

	/**
	 * Gets the FormSubmissionStatus by form instance record ID
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the FormSubmissionStatus, or null if not found
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			getByFormInstanceRecordId(long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getByFormInstanceRecordId(
			formInstanceRecordId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _formSubmissionStatusService.getOSGiServiceIdentifier();
	}

	/**
	 * Gets all seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of seen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
				getSeenByGroupId(long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getSeenByGroupId(groupId);
	}

	/**
	 * Gets count of seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of seen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public int getSeenCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getSeenCountByGroupId(groupId);
	}

	/**
	 * Gets unseen form submissions by form instance ID
	 *
	 * @param formInstanceId the form instance ID
	 * @param groupId        the group ID
	 * @return list of unseen FormSubmissionStatus entries for the specific form instance
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
				getUnseenByFormInstanceId(long formInstanceId, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getUnseenByFormInstanceId(
			formInstanceId, groupId);
	}

	/**
	 * Gets all unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
				getUnseenByGroupId(long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getUnseenByGroupId(groupId);
	}

	/**
	 * Gets count of unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of unseen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public int getUnseenCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getUnseenCountByGroupId(groupId);
	}

	/**
	 * Checks if a form submission is seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return true if seen, false otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public boolean isSeen(long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.isSeen(formInstanceRecordId);
	}

	/**
	 * Marks a form submission as seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param userId               the user ID who marked it as seen
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus markAsSeen(
			long formInstanceRecordId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.markAsSeen(
			formInstanceRecordId, userId);
	}

	/**
	 * Marks a form submission as unseen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus markAsUnseen(
			long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.markAsUnseen(formInstanceRecordId);
	}

	@Override
	public FormSubmissionStatusService getWrappedService() {
		return _formSubmissionStatusService;
	}

	@Override
	public void setWrappedService(
		FormSubmissionStatusService formSubmissionStatusService) {

		_formSubmissionStatusService = formSubmissionStatusService;
	}

	private FormSubmissionStatusService _formSubmissionStatusService;

}