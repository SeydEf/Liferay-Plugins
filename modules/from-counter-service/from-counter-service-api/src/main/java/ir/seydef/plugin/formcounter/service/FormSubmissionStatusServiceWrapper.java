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

	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			addFormSubmissionStatus(
				long formInstanceRecordId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.addFormSubmissionStatus(
			formInstanceRecordId, serviceContext);
	}

	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			createOrUpdate(
				long formInstanceRecordId, boolean seen,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.createOrUpdate(
			formInstanceRecordId, seen, serviceContext);
	}

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

	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
				getSeenByGroupId(long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getSeenByGroupId(groupId);
	}

	@Override
	public int getSeenCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getSeenCountByGroupId(groupId);
	}

	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
				getUnseenByFormInstanceId(long formInstanceId, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getUnseenByFormInstanceId(
			formInstanceId, groupId);
	}

	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
				getUnseenByGroupId(long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getUnseenByGroupId(groupId);
	}

	@Override
	public int getUnseenCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.getUnseenCountByGroupId(groupId);
	}

	@Override
	public boolean isSeen(long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.isSeen(formInstanceRecordId);
	}

	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus markAsSeen(
			long formInstanceRecordId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusService.markAsSeen(
			formInstanceRecordId, userId);
	}

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