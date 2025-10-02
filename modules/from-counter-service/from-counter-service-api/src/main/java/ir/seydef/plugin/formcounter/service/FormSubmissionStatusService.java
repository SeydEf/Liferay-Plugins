/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for FormSubmissionStatus. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface FormSubmissionStatusService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>ir.seydef.plugin.formcounter.service.impl.FormSubmissionStatusServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the form submission status remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link FormSubmissionStatusServiceUtil} if injection and service tracking are not available.
	 */

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
		throws PortalException;

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
		throws PortalException;

	/**
	 * Gets the FormSubmissionStatus by form instance record ID
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the FormSubmissionStatus, or null if not found
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FormSubmissionStatus getByFormInstanceRecordId(
			long formInstanceRecordId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Gets all seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of seen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormSubmissionStatus> getSeenByGroupId(long groupId)
		throws PortalException;

	/**
	 * Gets count of seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of seen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSeenCountByGroupId(long groupId) throws PortalException;

	/**
	 * Gets unseen form submissions by form instance ID
	 *
	 * @param formInstanceId the form instance ID
	 * @param groupId        the group ID
	 * @return list of unseen FormSubmissionStatus entries for the specific form instance
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormSubmissionStatus> getUnseenByFormInstanceId(
			long formInstanceId, long groupId)
		throws PortalException;

	/**
	 * Gets all unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormSubmissionStatus> getUnseenByGroupId(long groupId)
		throws PortalException;

	/**
	 * Gets count of unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of unseen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUnseenCountByGroupId(long groupId) throws PortalException;

	/**
	 * Checks if a form submission is seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return true if seen, false otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isSeen(long formInstanceRecordId) throws PortalException;

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
		throws PortalException;

	/**
	 * Marks a form submission as unseen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public FormSubmissionStatus markAsUnseen(long formInstanceRecordId)
		throws PortalException;

}