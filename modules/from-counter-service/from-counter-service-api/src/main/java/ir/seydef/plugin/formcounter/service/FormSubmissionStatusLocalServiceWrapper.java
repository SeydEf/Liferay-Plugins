/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link FormSubmissionStatusLocalService}.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusLocalService
 * @generated
 */
public class FormSubmissionStatusLocalServiceWrapper
	implements FormSubmissionStatusLocalService,
			   ServiceWrapper<FormSubmissionStatusLocalService> {

	public FormSubmissionStatusLocalServiceWrapper(
		FormSubmissionStatusLocalService formSubmissionStatusLocalService) {

		_formSubmissionStatusLocalService = formSubmissionStatusLocalService;
	}

	/**
	 * Adds the form submission status to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FormSubmissionStatusLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param formSubmissionStatus the form submission status
	 * @return the form submission status that was added
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
		addFormSubmissionStatus(
			ir.seydef.plugin.formcounter.model.FormSubmissionStatus
				formSubmissionStatus) {

		return _formSubmissionStatusLocalService.addFormSubmissionStatus(
			formSubmissionStatus);
	}

	/**
	 * Creates a new FormSubmissionStatus entry for a form instance record
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param serviceContext the service context
	 * @return the created FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			addFormSubmissionStatus(
				long formInstanceRecordId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusLocalService.addFormSubmissionStatus(
			formInstanceRecordId, serviceContext);
	}

	/**
	 * Creates a new form submission status with the primary key. Does not add the form submission status to the database.
	 *
	 * @param formSubmissionStatusId the primary key for the new form submission status
	 * @return the new form submission status
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
		createFormSubmissionStatus(long formSubmissionStatusId) {

		return _formSubmissionStatusLocalService.createFormSubmissionStatus(
			formSubmissionStatusId);
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
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			createOrUpdate(
				long formInstanceRecordId, boolean seen,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusLocalService.createOrUpdate(
			formInstanceRecordId, seen, serviceContext);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the form submission status from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FormSubmissionStatusLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param formSubmissionStatus the form submission status
	 * @return the form submission status that was removed
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
		deleteFormSubmissionStatus(
			ir.seydef.plugin.formcounter.model.FormSubmissionStatus
				formSubmissionStatus) {

		return _formSubmissionStatusLocalService.deleteFormSubmissionStatus(
			formSubmissionStatus);
	}

	/**
	 * Deletes the form submission status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FormSubmissionStatusLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status that was removed
	 * @throws PortalException if a form submission status with the primary key could not be found
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			deleteFormSubmissionStatus(long formSubmissionStatusId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusLocalService.deleteFormSubmissionStatus(
			formSubmissionStatusId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _formSubmissionStatusLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _formSubmissionStatusLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ir.seydef.plugin.formcounter.model.impl.FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _formSubmissionStatusLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ir.seydef.plugin.formcounter.model.impl.FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _formSubmissionStatusLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _formSubmissionStatusLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _formSubmissionStatusLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
		fetchFormSubmissionStatus(long formSubmissionStatusId) {

		return _formSubmissionStatusLocalService.fetchFormSubmissionStatus(
			formSubmissionStatusId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _formSubmissionStatusLocalService.getActionableDynamicQuery();
	}

	/**
	 * Gets the FormSubmissionStatus by form instance record ID
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the FormSubmissionStatus, or null if not found
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
		getByFormInstanceRecordId(long formInstanceRecordId) {

		return _formSubmissionStatusLocalService.getByFormInstanceRecordId(
			formInstanceRecordId);
	}

	/**
	 * Returns the form submission status with the primary key.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status
	 * @throws PortalException if a form submission status with the primary key could not be found
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			getFormSubmissionStatus(long formSubmissionStatusId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusLocalService.getFormSubmissionStatus(
			formSubmissionStatusId);
	}

	/**
	 * Returns a range of all the form submission statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ir.seydef.plugin.formcounter.model.impl.FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @return the range of form submission statuses
	 */
	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
			getFormSubmissionStatuses(int start, int end) {

		return _formSubmissionStatusLocalService.getFormSubmissionStatuses(
			start, end);
	}

	/**
	 * Returns the number of form submission statuses.
	 *
	 * @return the number of form submission statuses
	 */
	@Override
	public int getFormSubmissionStatusesCount() {
		return _formSubmissionStatusLocalService.
			getFormSubmissionStatusesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _formSubmissionStatusLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _formSubmissionStatusLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Gets all seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of seen FormSubmissionStatus entries
	 */
	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
			getSeenByGroupId(long groupId) {

		return _formSubmissionStatusLocalService.getSeenByGroupId(groupId);
	}

	/**
	 * Gets count of seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of seen submissions
	 */
	@Override
	public int getSeenCountByGroupId(long groupId) {
		return _formSubmissionStatusLocalService.getSeenCountByGroupId(groupId);
	}

	/**
	 * Gets unseen form submissions by form instance ID
	 *
	 * @param formInstanceId the form instance ID
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries for the specific form instance
	 */
	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
			getUnseenByFormInstanceId(long formInstanceId, long groupId) {

		return _formSubmissionStatusLocalService.getUnseenByFormInstanceId(
			formInstanceId, groupId);
	}

	/**
	 * Gets all unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries
	 */
	@Override
	public java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
			getUnseenByGroupId(long groupId) {

		return _formSubmissionStatusLocalService.getUnseenByGroupId(groupId);
	}

	/**
	 * Gets count of unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of unseen submissions
	 */
	@Override
	public int getUnseenCountByGroupId(long groupId) {
		return _formSubmissionStatusLocalService.getUnseenCountByGroupId(
			groupId);
	}

	/**
	 * Checks if a form submission is seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return true if seen, false otherwise
	 */
	@Override
	public boolean isSeen(long formInstanceRecordId) {
		return _formSubmissionStatusLocalService.isSeen(formInstanceRecordId);
	}

	/**
	 * Marks a form submission as seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param userId the user ID who marked it as seen
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus markAsSeen(
			long formInstanceRecordId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formSubmissionStatusLocalService.markAsSeen(
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

		return _formSubmissionStatusLocalService.markAsUnseen(
			formInstanceRecordId);
	}

	/**
	 * Updates the form submission status in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FormSubmissionStatusLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param formSubmissionStatus the form submission status
	 * @return the form submission status that was updated
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormSubmissionStatus
		updateFormSubmissionStatus(
			ir.seydef.plugin.formcounter.model.FormSubmissionStatus
				formSubmissionStatus) {

		return _formSubmissionStatusLocalService.updateFormSubmissionStatus(
			formSubmissionStatus);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _formSubmissionStatusLocalService.getBasePersistence();
	}

	@Override
	public FormSubmissionStatusLocalService getWrappedService() {
		return _formSubmissionStatusLocalService;
	}

	@Override
	public void setWrappedService(
		FormSubmissionStatusLocalService formSubmissionStatusLocalService) {

		_formSubmissionStatusLocalService = formSubmissionStatusLocalService;
	}

	private FormSubmissionStatusLocalService _formSubmissionStatusLocalService;

}