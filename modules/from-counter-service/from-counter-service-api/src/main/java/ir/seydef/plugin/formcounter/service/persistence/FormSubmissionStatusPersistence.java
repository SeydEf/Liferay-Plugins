/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import ir.seydef.plugin.formcounter.exception.NoSuchFormSubmissionStatusException;
import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the form submission status service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusUtil
 * @generated
 */
@ProviderType
public interface FormSubmissionStatusPersistence
	extends BasePersistence<FormSubmissionStatus> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FormSubmissionStatusUtil} to access the form submission status persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the form submission status where formInstanceRecordId = &#63; or throws a <code>NoSuchFormSubmissionStatusException</code> if it could not be found.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public FormSubmissionStatus findByFormInstanceRecordId(
			long formInstanceRecordId)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the form submission status where formInstanceRecordId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchByFormInstanceRecordId(
		long formInstanceRecordId);

	/**
	 * Returns the form submission status where formInstanceRecordId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchByFormInstanceRecordId(
		long formInstanceRecordId, boolean useFinderCache);

	/**
	 * Removes the form submission status where formInstanceRecordId = &#63; from the database.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the form submission status that was removed
	 */
	public FormSubmissionStatus removeByFormInstanceRecordId(
			long formInstanceRecordId)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the number of form submission statuses where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the number of matching form submission statuses
	 */
	public int countByFormInstanceRecordId(long formInstanceRecordId);

	/**
	 * Returns all the form submission statuses where seen = &#63;.
	 *
	 * @param seen the seen
	 * @return the matching form submission statuses
	 */
	public java.util.List<FormSubmissionStatus> findBySeen(boolean seen);

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
	public java.util.List<FormSubmissionStatus> findBySeen(
		boolean seen, int start, int end);

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
	public java.util.List<FormSubmissionStatus> findBySeen(
		boolean seen, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

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
	public java.util.List<FormSubmissionStatus> findBySeen(
		boolean seen, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public FormSubmissionStatus findBySeen_First(
			boolean seen,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the first form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchBySeen_First(
		boolean seen,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

	/**
	 * Returns the last form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public FormSubmissionStatus findBySeen_Last(
			boolean seen,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the last form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchBySeen_Last(
		boolean seen,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

	/**
	 * Returns the form submission statuses before and after the current form submission status in the ordered set where seen = &#63;.
	 *
	 * @param formSubmissionStatusId the primary key of the current form submission status
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public FormSubmissionStatus[] findBySeen_PrevAndNext(
			long formSubmissionStatusId, boolean seen,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Removes all the form submission statuses where seen = &#63; from the database.
	 *
	 * @param seen the seen
	 */
	public void removeBySeen(boolean seen);

	/**
	 * Returns the number of form submission statuses where seen = &#63;.
	 *
	 * @param seen the seen
	 * @return the number of matching form submission statuses
	 */
	public int countBySeen(boolean seen);

	/**
	 * Returns all the form submission statuses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching form submission statuses
	 */
	public java.util.List<FormSubmissionStatus> findByCompanyId(long companyId);

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
	public java.util.List<FormSubmissionStatus> findByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<FormSubmissionStatus> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

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
	public java.util.List<FormSubmissionStatus> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public FormSubmissionStatus findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the first form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

	/**
	 * Returns the last form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public FormSubmissionStatus findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the last form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

	/**
	 * Returns the form submission statuses before and after the current form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param formSubmissionStatusId the primary key of the current form submission status
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public FormSubmissionStatus[] findByCompanyId_PrevAndNext(
			long formSubmissionStatusId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Removes all the form submission statuses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of form submission statuses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching form submission statuses
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the form submission statuses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching form submission statuses
	 */
	public java.util.List<FormSubmissionStatus> findByGroupId(long groupId);

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
	public java.util.List<FormSubmissionStatus> findByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<FormSubmissionStatus> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

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
	public java.util.List<FormSubmissionStatus> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public FormSubmissionStatus findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public FormSubmissionStatus findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

	/**
	 * Returns the form submission statuses before and after the current form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param formSubmissionStatusId the primary key of the current form submission status
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public FormSubmissionStatus[] findByGroupId_PrevAndNext(
			long formSubmissionStatusId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Removes all the form submission statuses where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of form submission statuses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching form submission statuses
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the form submission statuses where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @return the matching form submission statuses
	 */
	public java.util.List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen);

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
	public java.util.List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen, int start, int end);

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
	public java.util.List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

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
	public java.util.List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public FormSubmissionStatus findByG_S_First(
			long groupId, boolean seen,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchByG_S_First(
		long groupId, boolean seen,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	public FormSubmissionStatus findByG_S_Last(
			long groupId, boolean seen,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	public FormSubmissionStatus fetchByG_S_Last(
		long groupId, boolean seen,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

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
	public FormSubmissionStatus[] findByG_S_PrevAndNext(
			long formSubmissionStatusId, long groupId, boolean seen,
			com.liferay.portal.kernel.util.OrderByComparator
				<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Removes all the form submission statuses where groupId = &#63; and seen = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 */
	public void removeByG_S(long groupId, boolean seen);

	/**
	 * Returns the number of form submission statuses where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @return the number of matching form submission statuses
	 */
	public int countByG_S(long groupId, boolean seen);

	/**
	 * Caches the form submission status in the entity cache if it is enabled.
	 *
	 * @param formSubmissionStatus the form submission status
	 */
	public void cacheResult(FormSubmissionStatus formSubmissionStatus);

	/**
	 * Caches the form submission statuses in the entity cache if it is enabled.
	 *
	 * @param formSubmissionStatuses the form submission statuses
	 */
	public void cacheResult(
		java.util.List<FormSubmissionStatus> formSubmissionStatuses);

	/**
	 * Creates a new form submission status with the primary key. Does not add the form submission status to the database.
	 *
	 * @param formSubmissionStatusId the primary key for the new form submission status
	 * @return the new form submission status
	 */
	public FormSubmissionStatus create(long formSubmissionStatusId);

	/**
	 * Removes the form submission status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status that was removed
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public FormSubmissionStatus remove(long formSubmissionStatusId)
		throws NoSuchFormSubmissionStatusException;

	public FormSubmissionStatus updateImpl(
		FormSubmissionStatus formSubmissionStatus);

	/**
	 * Returns the form submission status with the primary key or throws a <code>NoSuchFormSubmissionStatusException</code> if it could not be found.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	public FormSubmissionStatus findByPrimaryKey(long formSubmissionStatusId)
		throws NoSuchFormSubmissionStatusException;

	/**
	 * Returns the form submission status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status, or <code>null</code> if a form submission status with the primary key could not be found
	 */
	public FormSubmissionStatus fetchByPrimaryKey(long formSubmissionStatusId);

	/**
	 * Returns all the form submission statuses.
	 *
	 * @return the form submission statuses
	 */
	public java.util.List<FormSubmissionStatus> findAll();

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
	public java.util.List<FormSubmissionStatus> findAll(int start, int end);

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
	public java.util.List<FormSubmissionStatus> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator);

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
	public java.util.List<FormSubmissionStatus> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormSubmissionStatus>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the form submission statuses from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of form submission statuses.
	 *
	 * @return the number of form submission statuses
	 */
	public int countAll();

}