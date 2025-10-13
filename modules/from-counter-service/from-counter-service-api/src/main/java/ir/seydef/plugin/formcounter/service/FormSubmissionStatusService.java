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
	public FormSubmissionStatus addFormSubmissionStatus(
			long formInstanceRecordId, ServiceContext serviceContext)
		throws PortalException;

	public FormSubmissionStatus createOrUpdate(
			long formInstanceRecordId, boolean seen,
			ServiceContext serviceContext)
		throws PortalException;

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormSubmissionStatus> getSeenByGroupId(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSeenCountByGroupId(long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormSubmissionStatus> getUnseenByFormInstanceId(
			long formInstanceId, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormSubmissionStatus> getUnseenByGroupId(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUnseenCountByGroupId(long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isSeen(long formInstanceRecordId) throws PortalException;

	public FormSubmissionStatus markAsSeen(
			long formInstanceRecordId, long userId)
		throws PortalException;

	public FormSubmissionStatus markAsUnseen(long formInstanceRecordId)
		throws PortalException;

}