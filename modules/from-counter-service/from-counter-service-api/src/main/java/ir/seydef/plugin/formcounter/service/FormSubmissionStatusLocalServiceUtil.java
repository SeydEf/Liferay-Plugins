/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for FormSubmissionStatus. This utility wraps
 * <code>ir.seydef.plugin.formcounter.service.impl.FormSubmissionStatusLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusLocalService
 * @generated
 */
public class FormSubmissionStatusLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>ir.seydef.plugin.formcounter.service.impl.FormSubmissionStatusLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static FormSubmissionStatus addFormSubmissionStatus(
		FormSubmissionStatus formSubmissionStatus) {

		return getService().addFormSubmissionStatus(formSubmissionStatus);
	}

	public static FormSubmissionStatus addFormSubmissionStatus(
			long formInstanceRecordId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFormSubmissionStatus(
			formInstanceRecordId, serviceContext);
	}

	/**
	 * Creates a new form submission status with the primary key. Does not add the form submission status to the database.
	 *
	 * @param formSubmissionStatusId the primary key for the new form submission status
	 * @return the new form submission status
	 */
	public static FormSubmissionStatus createFormSubmissionStatus(
		long formSubmissionStatusId) {

		return getService().createFormSubmissionStatus(formSubmissionStatusId);
	}

	public static FormSubmissionStatus createOrUpdate(
			long formInstanceRecordId, boolean seen,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().createOrUpdate(
			formInstanceRecordId, seen, serviceContext);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static FormSubmissionStatus deleteFormSubmissionStatus(
		FormSubmissionStatus formSubmissionStatus) {

		return getService().deleteFormSubmissionStatus(formSubmissionStatus);
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
	public static FormSubmissionStatus deleteFormSubmissionStatus(
			long formSubmissionStatusId)
		throws PortalException {

		return getService().deleteFormSubmissionStatus(formSubmissionStatusId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static FormSubmissionStatus fetchFormSubmissionStatus(
		long formSubmissionStatusId) {

		return getService().fetchFormSubmissionStatus(formSubmissionStatusId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static FormSubmissionStatus getByFormInstanceRecordId(
		long formInstanceRecordId) {

		return getService().getByFormInstanceRecordId(formInstanceRecordId);
	}

	/**
	 * Returns the form submission status with the primary key.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status
	 * @throws PortalException if a form submission status with the primary key could not be found
	 */
	public static FormSubmissionStatus getFormSubmissionStatus(
			long formSubmissionStatusId)
		throws PortalException {

		return getService().getFormSubmissionStatus(formSubmissionStatusId);
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
	public static List<FormSubmissionStatus> getFormSubmissionStatuses(
		int start, int end) {

		return getService().getFormSubmissionStatuses(start, end);
	}

	/**
	 * Returns the number of form submission statuses.
	 *
	 * @return the number of form submission statuses
	 */
	public static int getFormSubmissionStatusesCount() {
		return getService().getFormSubmissionStatusesCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
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
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static List<FormSubmissionStatus> getSeenByGroupId(long groupId) {
		return getService().getSeenByGroupId(groupId);
	}

	public static int getSeenCountByGroupId(long groupId) {
		return getService().getSeenCountByGroupId(groupId);
	}

	public static List<FormSubmissionStatus> getUnseenByFormInstanceId(
		long formInstanceId, long groupId) {

		return getService().getUnseenByFormInstanceId(formInstanceId, groupId);
	}

	public static List<FormSubmissionStatus> getUnseenByGroupId(long groupId) {
		return getService().getUnseenByGroupId(groupId);
	}

	public static int getUnseenCountByGroupId(long groupId) {
		return getService().getUnseenCountByGroupId(groupId);
	}

	public static boolean isSeen(long formInstanceRecordId) {
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
	public static FormSubmissionStatus updateFormSubmissionStatus(
		FormSubmissionStatus formSubmissionStatus) {

		return getService().updateFormSubmissionStatus(formSubmissionStatus);
	}

	public static FormSubmissionStatusLocalService getService() {
		return _service;
	}

	public static void setService(FormSubmissionStatusLocalService service) {
		_service = service;
	}

	private static volatile FormSubmissionStatusLocalService _service;

}