/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the form submission status service. This utility wraps <code>ir.seydef.plugin.formcounter.service.persistence.impl.FormSubmissionStatusPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusPersistence
 * @generated
 */
public class FormSubmissionStatusUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(FormSubmissionStatus formSubmissionStatus) {
		getPersistence().clearCache(formSubmissionStatus);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, FormSubmissionStatus> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FormSubmissionStatus> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FormSubmissionStatus> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FormSubmissionStatus> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FormSubmissionStatus update(
		FormSubmissionStatus formSubmissionStatus) {

		return getPersistence().update(formSubmissionStatus);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FormSubmissionStatus update(
		FormSubmissionStatus formSubmissionStatus,
		ServiceContext serviceContext) {

		return getPersistence().update(formSubmissionStatus, serviceContext);
	}

	/**
	 * Returns the form submission status where formInstanceRecordId = &#63; or throws a <code>NoSuchFormSubmissionStatusException</code> if it could not be found.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus findByFormInstanceRecordId(
			long formInstanceRecordId)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByFormInstanceRecordId(
			formInstanceRecordId);
	}

	/**
	 * Returns the form submission status where formInstanceRecordId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchByFormInstanceRecordId(
		long formInstanceRecordId) {

		return getPersistence().fetchByFormInstanceRecordId(
			formInstanceRecordId);
	}

	/**
	 * Returns the form submission status where formInstanceRecordId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchByFormInstanceRecordId(
		long formInstanceRecordId, boolean useFinderCache) {

		return getPersistence().fetchByFormInstanceRecordId(
			formInstanceRecordId, useFinderCache);
	}

	/**
	 * Removes the form submission status where formInstanceRecordId = &#63; from the database.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the form submission status that was removed
	 */
	public static FormSubmissionStatus removeByFormInstanceRecordId(
			long formInstanceRecordId)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().removeByFormInstanceRecordId(
			formInstanceRecordId);
	}

	/**
	 * Returns the number of form submission statuses where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the number of matching form submission statuses
	 */
	public static int countByFormInstanceRecordId(long formInstanceRecordId) {
		return getPersistence().countByFormInstanceRecordId(
			formInstanceRecordId);
	}

	/**
	 * Returns all the form submission statuses where seen = &#63;.
	 *
	 * @param seen the seen
	 * @return the matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findBySeen(boolean seen) {
		return getPersistence().findBySeen(seen);
	}

	/**
	 * Returns a range of all the form submission statuses where seen = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param seen the seen
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @return the range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findBySeen(
		boolean seen, int start, int end) {

		return getPersistence().findBySeen(seen, start, end);
	}

	/**
	 * Returns an ordered range of all the form submission statuses where seen = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param seen the seen
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findBySeen(
		boolean seen, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().findBySeen(seen, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the form submission statuses where seen = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param seen the seen
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findBySeen(
		boolean seen, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBySeen(
			seen, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus findBySeen_First(
			boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findBySeen_First(seen, orderByComparator);
	}

	/**
	 * Returns the first form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchBySeen_First(
		boolean seen,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().fetchBySeen_First(seen, orderByComparator);
	}

	/**
	 * Returns the last form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus findBySeen_Last(
			boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findBySeen_Last(seen, orderByComparator);
	}

	/**
	 * Returns the last form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchBySeen_Last(
		boolean seen,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().fetchBySeen_Last(seen, orderByComparator);
	}

	/**
	 * Returns the form submission statuses before and after the current form submission status in the ordered set where seen = &#63;.
	 *
	 * @param formSubmissionStatusId the primary key of the current form submission status
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public static FormSubmissionStatus[] findBySeen_PrevAndNext(
			long formSubmissionStatusId, boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findBySeen_PrevAndNext(
			formSubmissionStatusId, seen, orderByComparator);
	}

	/**
	 * Removes all the form submission statuses where seen = &#63; from the database.
	 *
	 * @param seen the seen
	 */
	public static void removeBySeen(boolean seen) {
		getPersistence().removeBySeen(seen);
	}

	/**
	 * Returns the number of form submission statuses where seen = &#63;.
	 *
	 * @param seen the seen
	 * @return the number of matching form submission statuses
	 */
	public static int countBySeen(boolean seen) {
		return getPersistence().countBySeen(seen);
	}

	/**
	 * Returns all the form submission statuses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the form submission statuses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @return the range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the form submission statuses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the form submission statuses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus findByCompanyId_First(
			long companyId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchByCompanyId_First(
		long companyId,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus findByCompanyId_Last(
			long companyId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the form submission statuses before and after the current form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param formSubmissionStatusId the primary key of the current form submission status
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public static FormSubmissionStatus[] findByCompanyId_PrevAndNext(
			long formSubmissionStatusId, long companyId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByCompanyId_PrevAndNext(
			formSubmissionStatusId, companyId, orderByComparator);
	}

	/**
	 * Removes all the form submission statuses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of form submission statuses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching form submission statuses
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the form submission statuses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the form submission statuses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @return the range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the form submission statuses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the form submission statuses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus findByGroupId_First(
			long groupId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchByGroupId_First(
		long groupId,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus findByGroupId_Last(
			long groupId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchByGroupId_Last(
		long groupId,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the form submission statuses before and after the current form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param formSubmissionStatusId the primary key of the current form submission status
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public static FormSubmissionStatus[] findByGroupId_PrevAndNext(
			long formSubmissionStatusId, long groupId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByGroupId_PrevAndNext(
			formSubmissionStatusId, groupId, orderByComparator);
	}

	/**
	 * Removes all the form submission statuses where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of form submission statuses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching form submission statuses
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the form submission statuses where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @return the matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen) {

		return getPersistence().findByG_S(groupId, seen);
	}

	/**
	 * Returns a range of all the form submission statuses where groupId = &#63; and seen = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @return the range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen, int start, int end) {

		return getPersistence().findByG_S(groupId, seen, start, end);
	}

	/**
	 * Returns an ordered range of all the form submission statuses where groupId = &#63; and seen = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().findByG_S(
			groupId, seen, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the form submission statuses where groupId = &#63; and seen = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching form submission statuses
	 */
	public static List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_S(
			groupId, seen, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus findByG_S_First(
			long groupId, boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByG_S_First(
			groupId, seen, orderByComparator);
	}

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchByG_S_First(
		long groupId, boolean seen,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().fetchByG_S_First(
			groupId, seen, orderByComparator);
	}

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus findByG_S_Last(
			long groupId, boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByG_S_Last(
			groupId, seen, orderByComparator);
	}

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public static FormSubmissionStatus fetchByG_S_Last(
		long groupId, boolean seen,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().fetchByG_S_Last(
			groupId, seen, orderByComparator);
	}

	/**
	 * Returns the form submission statuses before and after the current form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param formSubmissionStatusId the primary key of the current form submission status
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public static FormSubmissionStatus[] findByG_S_PrevAndNext(
			long formSubmissionStatusId, long groupId, boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByG_S_PrevAndNext(
			formSubmissionStatusId, groupId, seen, orderByComparator);
	}

	/**
	 * Removes all the form submission statuses where groupId = &#63; and seen = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 */
	public static void removeByG_S(long groupId, boolean seen) {
		getPersistence().removeByG_S(groupId, seen);
	}

	/**
	 * Returns the number of form submission statuses where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @return the number of matching form submission statuses
	 */
	public static int countByG_S(long groupId, boolean seen) {
		return getPersistence().countByG_S(groupId, seen);
	}

	/**
	 * Caches the form submission status in the entity cache if it is enabled.
	 *
	 * @param formSubmissionStatus the form submission status
	 */
	public static void cacheResult(FormSubmissionStatus formSubmissionStatus) {
		getPersistence().cacheResult(formSubmissionStatus);
	}

	/**
	 * Caches the form submission statuses in the entity cache if it is enabled.
	 *
	 * @param formSubmissionStatuses the form submission statuses
	 */
	public static void cacheResult(
		List<FormSubmissionStatus> formSubmissionStatuses) {

		getPersistence().cacheResult(formSubmissionStatuses);
	}

	/**
	 * Creates a new form submission status with the primary key. Does not add the form submission status to the database.
	 *
	 * @param formSubmissionStatusId the primary key for the new form submission status
	 * @return the new form submission status
	 */
	public static FormSubmissionStatus create(long formSubmissionStatusId) {
		return getPersistence().create(formSubmissionStatusId);
	}

	/**
	 * Removes the form submission status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status that was removed
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public static FormSubmissionStatus remove(long formSubmissionStatusId)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().remove(formSubmissionStatusId);
	}

	public static FormSubmissionStatus updateImpl(
		FormSubmissionStatus formSubmissionStatus) {

		return getPersistence().updateImpl(formSubmissionStatus);
	}

	/**
	 * Returns the form submission status with the primary key or throws a <code>NoSuchFormSubmissionStatusException</code> if it could not be found.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public static FormSubmissionStatus findByPrimaryKey(
			long formSubmissionStatusId)
		throws ir.seydef.plugin.formcounter.exception.
			NoSuchFormSubmissionStatusException {

		return getPersistence().findByPrimaryKey(formSubmissionStatusId);
	}

	/**
	 * Returns the form submission status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status, or <code>null</code> if a form submission status with the primary key could not be found
	 */
	public static FormSubmissionStatus fetchByPrimaryKey(
		long formSubmissionStatusId) {

		return getPersistence().fetchByPrimaryKey(formSubmissionStatusId);
	}

	/**
	 * Returns all the form submission statuses.
	 *
	 * @return the form submission statuses
	 */
	public static List<FormSubmissionStatus> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the form submission statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @return the range of form submission statuses
	 */
	public static List<FormSubmissionStatus> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the form submission statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of form submission statuses
	 */
	public static List<FormSubmissionStatus> findAll(
		int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the form submission statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormSubmissionStatusModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of form submission statuses
	 * @param end the upper bound of the range of form submission statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of form submission statuses
	 */
	public static List<FormSubmissionStatus> findAll(
		int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the form submission statuses from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of form submission statuses.
	 *
	 * @return the number of form submission statuses
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FormSubmissionStatusPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		FormSubmissionStatusPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile FormSubmissionStatusPersistence _persistence;

}