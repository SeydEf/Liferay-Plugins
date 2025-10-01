/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import ir.seydef.plugin.formcounter.exception.NoSuchFormSubmissionStatusException;
import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.model.impl.FormSubmissionStatusImpl;
import ir.seydef.plugin.formcounter.model.impl.FormSubmissionStatusModelImpl;
import ir.seydef.plugin.formcounter.service.persistence.FormSubmissionStatusPersistence;
import ir.seydef.plugin.formcounter.service.persistence.FormSubmissionStatusUtil;
import ir.seydef.plugin.formcounter.service.persistence.impl.constants.FormCounterPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the form submission status service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @generated
 */
@Component(service = FormSubmissionStatusPersistence.class)
public class FormSubmissionStatusPersistenceImpl
	extends BasePersistenceImpl<FormSubmissionStatus>
	implements FormSubmissionStatusPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FormSubmissionStatusUtil</code> to access the form submission status persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FormSubmissionStatusImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByFormInstanceRecordId;
	private FinderPath _finderPathCountByFormInstanceRecordId;

	/**
	 * Returns the form submission status where formInstanceRecordId = &#63; or throws a <code>NoSuchFormSubmissionStatusException</code> if it could not be found.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus findByFormInstanceRecordId(
			long formInstanceRecordId)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchByFormInstanceRecordId(
			formInstanceRecordId);

		if (formSubmissionStatus == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("formInstanceRecordId=");
			sb.append(formInstanceRecordId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFormSubmissionStatusException(sb.toString());
		}

		return formSubmissionStatus;
	}

	/**
	 * Returns the form submission status where formInstanceRecordId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchByFormInstanceRecordId(
		long formInstanceRecordId) {

		return fetchByFormInstanceRecordId(formInstanceRecordId, true);
	}

	/**
	 * Returns the form submission status where formInstanceRecordId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchByFormInstanceRecordId(
		long formInstanceRecordId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {formInstanceRecordId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByFormInstanceRecordId, finderArgs, this);
		}

		if (result instanceof FormSubmissionStatus) {
			FormSubmissionStatus formSubmissionStatus =
				(FormSubmissionStatus)result;

			if (formInstanceRecordId !=
					formSubmissionStatus.getFormInstanceRecordId()) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(
				_FINDER_COLUMN_FORMINSTANCERECORDID_FORMINSTANCERECORDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceRecordId);

				List<FormSubmissionStatus> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByFormInstanceRecordId, finderArgs,
							list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									formInstanceRecordId
								};
							}

							_log.warn(
								"FormSubmissionStatusPersistenceImpl.fetchByFormInstanceRecordId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					FormSubmissionStatus formSubmissionStatus = list.get(0);

					result = formSubmissionStatus;

					cacheResult(formSubmissionStatus);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (FormSubmissionStatus)result;
		}
	}

	/**
	 * Removes the form submission status where formInstanceRecordId = &#63; from the database.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the form submission status that was removed
	 */
	@Override
	public FormSubmissionStatus removeByFormInstanceRecordId(
			long formInstanceRecordId)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = findByFormInstanceRecordId(
			formInstanceRecordId);

		return remove(formSubmissionStatus);
	}

	/**
	 * Returns the number of form submission statuses where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the number of matching form submission statuses
	 */
	@Override
	public int countByFormInstanceRecordId(long formInstanceRecordId) {
		FinderPath finderPath = _finderPathCountByFormInstanceRecordId;

		Object[] finderArgs = new Object[] {formInstanceRecordId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(
				_FINDER_COLUMN_FORMINSTANCERECORDID_FORMINSTANCERECORDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceRecordId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_FORMINSTANCERECORDID_FORMINSTANCERECORDID_2 =
			"formSubmissionStatus.formInstanceRecordId = ?";

	private FinderPath _finderPathWithPaginationFindBySeen;
	private FinderPath _finderPathWithoutPaginationFindBySeen;
	private FinderPath _finderPathCountBySeen;

	/**
	 * Returns all the form submission statuses where seen = &#63;.
	 *
	 * @param seen the seen
	 * @return the matching form submission statuses
	 */
	@Override
	public List<FormSubmissionStatus> findBySeen(boolean seen) {
		return findBySeen(seen, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<FormSubmissionStatus> findBySeen(
		boolean seen, int start, int end) {

		return findBySeen(seen, start, end, null);
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
	@Override
	public List<FormSubmissionStatus> findBySeen(
		boolean seen, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return findBySeen(seen, start, end, orderByComparator, true);
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
	@Override
	public List<FormSubmissionStatus> findBySeen(
		boolean seen, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindBySeen;
				finderArgs = new Object[] {seen};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySeen;
			finderArgs = new Object[] {seen, start, end, orderByComparator};
		}

		List<FormSubmissionStatus> list = null;

		if (useFinderCache) {
			list = (List<FormSubmissionStatus>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FormSubmissionStatus formSubmissionStatus : list) {
					if (seen != formSubmissionStatus.isSeen()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(_FINDER_COLUMN_SEEN_SEEN_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FormSubmissionStatusModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(seen);

				list = (List<FormSubmissionStatus>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus findBySeen_First(
			boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchBySeen_First(
			seen, orderByComparator);

		if (formSubmissionStatus != null) {
			return formSubmissionStatus;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("seen=");
		sb.append(seen);

		sb.append("}");

		throw new NoSuchFormSubmissionStatusException(sb.toString());
	}

	/**
	 * Returns the first form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchBySeen_First(
		boolean seen,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		List<FormSubmissionStatus> list = findBySeen(
			seen, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus findBySeen_Last(
			boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchBySeen_Last(
			seen, orderByComparator);

		if (formSubmissionStatus != null) {
			return formSubmissionStatus;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("seen=");
		sb.append(seen);

		sb.append("}");

		throw new NoSuchFormSubmissionStatusException(sb.toString());
	}

	/**
	 * Returns the last form submission status in the ordered set where seen = &#63;.
	 *
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchBySeen_Last(
		boolean seen,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		int count = countBySeen(seen);

		if (count == 0) {
			return null;
		}

		List<FormSubmissionStatus> list = findBySeen(
			seen, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public FormSubmissionStatus[] findBySeen_PrevAndNext(
			long formSubmissionStatusId, boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = findByPrimaryKey(
			formSubmissionStatusId);

		Session session = null;

		try {
			session = openSession();

			FormSubmissionStatus[] array = new FormSubmissionStatusImpl[3];

			array[0] = getBySeen_PrevAndNext(
				session, formSubmissionStatus, seen, orderByComparator, true);

			array[1] = formSubmissionStatus;

			array[2] = getBySeen_PrevAndNext(
				session, formSubmissionStatus, seen, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FormSubmissionStatus getBySeen_PrevAndNext(
		Session session, FormSubmissionStatus formSubmissionStatus,
		boolean seen, OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE);

		sb.append(_FINDER_COLUMN_SEEN_SEEN_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FormSubmissionStatusModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(seen);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						formSubmissionStatus)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FormSubmissionStatus> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the form submission statuses where seen = &#63; from the database.
	 *
	 * @param seen the seen
	 */
	@Override
	public void removeBySeen(boolean seen) {
		for (FormSubmissionStatus formSubmissionStatus :
				findBySeen(seen, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(formSubmissionStatus);
		}
	}

	/**
	 * Returns the number of form submission statuses where seen = &#63;.
	 *
	 * @param seen the seen
	 * @return the number of matching form submission statuses
	 */
	@Override
	public int countBySeen(boolean seen) {
		FinderPath finderPath = _finderPathCountBySeen;

		Object[] finderArgs = new Object[] {seen};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(_FINDER_COLUMN_SEEN_SEEN_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(seen);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SEEN_SEEN_2 =
		"formSubmissionStatus.seen = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the form submission statuses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching form submission statuses
	 */
	@Override
	public List<FormSubmissionStatus> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<FormSubmissionStatus> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
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
	@Override
	public List<FormSubmissionStatus> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
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
	@Override
	public List<FormSubmissionStatus> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<FormSubmissionStatus> list = null;

		if (useFinderCache) {
			list = (List<FormSubmissionStatus>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FormSubmissionStatus formSubmissionStatus : list) {
					if (companyId != formSubmissionStatus.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FormSubmissionStatusModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<FormSubmissionStatus>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus findByCompanyId_First(
			long companyId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (formSubmissionStatus != null) {
			return formSubmissionStatus;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFormSubmissionStatusException(sb.toString());
	}

	/**
	 * Returns the first form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchByCompanyId_First(
		long companyId,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		List<FormSubmissionStatus> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus findByCompanyId_Last(
			long companyId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (formSubmissionStatus != null) {
			return formSubmissionStatus;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFormSubmissionStatusException(sb.toString());
	}

	/**
	 * Returns the last form submission status in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<FormSubmissionStatus> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public FormSubmissionStatus[] findByCompanyId_PrevAndNext(
			long formSubmissionStatusId, long companyId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = findByPrimaryKey(
			formSubmissionStatusId);

		Session session = null;

		try {
			session = openSession();

			FormSubmissionStatus[] array = new FormSubmissionStatusImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, formSubmissionStatus, companyId, orderByComparator,
				true);

			array[1] = formSubmissionStatus;

			array[2] = getByCompanyId_PrevAndNext(
				session, formSubmissionStatus, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FormSubmissionStatus getByCompanyId_PrevAndNext(
		Session session, FormSubmissionStatus formSubmissionStatus,
		long companyId,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FormSubmissionStatusModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						formSubmissionStatus)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FormSubmissionStatus> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the form submission statuses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (FormSubmissionStatus formSubmissionStatus :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(formSubmissionStatus);
		}
	}

	/**
	 * Returns the number of form submission statuses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching form submission statuses
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"formSubmissionStatus.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the form submission statuses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching form submission statuses
	 */
	@Override
	public List<FormSubmissionStatus> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<FormSubmissionStatus> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
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
	@Override
	public List<FormSubmissionStatus> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
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
	@Override
	public List<FormSubmissionStatus> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<FormSubmissionStatus> list = null;

		if (useFinderCache) {
			list = (List<FormSubmissionStatus>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FormSubmissionStatus formSubmissionStatus : list) {
					if (groupId != formSubmissionStatus.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FormSubmissionStatusModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<FormSubmissionStatus>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus findByGroupId_First(
			long groupId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchByGroupId_First(
			groupId, orderByComparator);

		if (formSubmissionStatus != null) {
			return formSubmissionStatus;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFormSubmissionStatusException(sb.toString());
	}

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchByGroupId_First(
		long groupId,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		List<FormSubmissionStatus> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status
	 * @throws NoSuchFormSubmissionStatusException if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus findByGroupId_Last(
			long groupId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (formSubmissionStatus != null) {
			return formSubmissionStatus;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFormSubmissionStatusException(sb.toString());
	}

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchByGroupId_Last(
		long groupId,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FormSubmissionStatus> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public FormSubmissionStatus[] findByGroupId_PrevAndNext(
			long formSubmissionStatusId, long groupId,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = findByPrimaryKey(
			formSubmissionStatusId);

		Session session = null;

		try {
			session = openSession();

			FormSubmissionStatus[] array = new FormSubmissionStatusImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, formSubmissionStatus, groupId, orderByComparator,
				true);

			array[1] = formSubmissionStatus;

			array[2] = getByGroupId_PrevAndNext(
				session, formSubmissionStatus, groupId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FormSubmissionStatus getByGroupId_PrevAndNext(
		Session session, FormSubmissionStatus formSubmissionStatus,
		long groupId, OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FormSubmissionStatusModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						formSubmissionStatus)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FormSubmissionStatus> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the form submission statuses where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FormSubmissionStatus formSubmissionStatus :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(formSubmissionStatus);
		}
	}

	/**
	 * Returns the number of form submission statuses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching form submission statuses
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"formSubmissionStatus.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns all the form submission statuses where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @return the matching form submission statuses
	 */
	@Override
	public List<FormSubmissionStatus> findByG_S(long groupId, boolean seen) {
		return findByG_S(
			groupId, seen, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen, int start, int end) {

		return findByG_S(groupId, seen, start, end, null);
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
	@Override
	public List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return findByG_S(groupId, seen, start, end, orderByComparator, true);
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
	@Override
	public List<FormSubmissionStatus> findByG_S(
		long groupId, boolean seen, int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_S;
				finderArgs = new Object[] {groupId, seen};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_S;
			finderArgs = new Object[] {
				groupId, seen, start, end, orderByComparator
			};
		}

		List<FormSubmissionStatus> list = null;

		if (useFinderCache) {
			list = (List<FormSubmissionStatus>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FormSubmissionStatus formSubmissionStatus : list) {
					if ((groupId != formSubmissionStatus.getGroupId()) ||
						(seen != formSubmissionStatus.isSeen())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_SEEN_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FormSubmissionStatusModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(seen);

				list = (List<FormSubmissionStatus>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public FormSubmissionStatus findByG_S_First(
			long groupId, boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchByG_S_First(
			groupId, seen, orderByComparator);

		if (formSubmissionStatus != null) {
			return formSubmissionStatus;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", seen=");
		sb.append(seen);

		sb.append("}");

		throw new NoSuchFormSubmissionStatusException(sb.toString());
	}

	/**
	 * Returns the first form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchByG_S_First(
		long groupId, boolean seen,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		List<FormSubmissionStatus> list = findByG_S(
			groupId, seen, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public FormSubmissionStatus findByG_S_Last(
			long groupId, boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchByG_S_Last(
			groupId, seen, orderByComparator);

		if (formSubmissionStatus != null) {
			return formSubmissionStatus;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", seen=");
		sb.append(seen);

		sb.append("}");

		throw new NoSuchFormSubmissionStatusException(sb.toString());
	}

	/**
	 * Returns the last form submission status in the ordered set where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form submission status, or <code>null</code> if a matching form submission status could not be found
	 */
	@Override
	public FormSubmissionStatus fetchByG_S_Last(
		long groupId, boolean seen,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		int count = countByG_S(groupId, seen);

		if (count == 0) {
			return null;
		}

		List<FormSubmissionStatus> list = findByG_S(
			groupId, seen, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public FormSubmissionStatus[] findByG_S_PrevAndNext(
			long formSubmissionStatusId, long groupId, boolean seen,
			OrderByComparator<FormSubmissionStatus> orderByComparator)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = findByPrimaryKey(
			formSubmissionStatusId);

		Session session = null;

		try {
			session = openSession();

			FormSubmissionStatus[] array = new FormSubmissionStatusImpl[3];

			array[0] = getByG_S_PrevAndNext(
				session, formSubmissionStatus, groupId, seen, orderByComparator,
				true);

			array[1] = formSubmissionStatus;

			array[2] = getByG_S_PrevAndNext(
				session, formSubmissionStatus, groupId, seen, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FormSubmissionStatus getByG_S_PrevAndNext(
		Session session, FormSubmissionStatus formSubmissionStatus,
		long groupId, boolean seen,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_SEEN_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FormSubmissionStatusModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(seen);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						formSubmissionStatus)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FormSubmissionStatus> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the form submission statuses where groupId = &#63; and seen = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 */
	@Override
	public void removeByG_S(long groupId, boolean seen) {
		for (FormSubmissionStatus formSubmissionStatus :
				findByG_S(
					groupId, seen, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(formSubmissionStatus);
		}
	}

	/**
	 * Returns the number of form submission statuses where groupId = &#63; and seen = &#63;.
	 *
	 * @param groupId the group ID
	 * @param seen the seen
	 * @return the number of matching form submission statuses
	 */
	@Override
	public int countByG_S(long groupId, boolean seen) {
		FinderPath finderPath = _finderPathCountByG_S;

		Object[] finderArgs = new Object[] {groupId, seen};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FORMSUBMISSIONSTATUS_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_SEEN_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(seen);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"formSubmissionStatus.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_SEEN_2 =
		"formSubmissionStatus.seen = ?";

	public FormSubmissionStatusPersistenceImpl() {
		setModelClass(FormSubmissionStatus.class);

		setModelImplClass(FormSubmissionStatusImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the form submission status in the entity cache if it is enabled.
	 *
	 * @param formSubmissionStatus the form submission status
	 */
	@Override
	public void cacheResult(FormSubmissionStatus formSubmissionStatus) {
		entityCache.putResult(
			FormSubmissionStatusImpl.class,
			formSubmissionStatus.getPrimaryKey(), formSubmissionStatus);

		finderCache.putResult(
			_finderPathFetchByFormInstanceRecordId,
			new Object[] {formSubmissionStatus.getFormInstanceRecordId()},
			formSubmissionStatus);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the form submission statuses in the entity cache if it is enabled.
	 *
	 * @param formSubmissionStatuses the form submission statuses
	 */
	@Override
	public void cacheResult(List<FormSubmissionStatus> formSubmissionStatuses) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (formSubmissionStatuses.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FormSubmissionStatus formSubmissionStatus :
				formSubmissionStatuses) {

			if (entityCache.getResult(
					FormSubmissionStatusImpl.class,
					formSubmissionStatus.getPrimaryKey()) == null) {

				cacheResult(formSubmissionStatus);
			}
		}
	}

	/**
	 * Clears the cache for all form submission statuses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FormSubmissionStatusImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the form submission status.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FormSubmissionStatus formSubmissionStatus) {
		entityCache.removeResult(
			FormSubmissionStatusImpl.class, formSubmissionStatus);
	}

	@Override
	public void clearCache(List<FormSubmissionStatus> formSubmissionStatuses) {
		for (FormSubmissionStatus formSubmissionStatus :
				formSubmissionStatuses) {

			entityCache.removeResult(
				FormSubmissionStatusImpl.class, formSubmissionStatus);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FormSubmissionStatusImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FormSubmissionStatusModelImpl formSubmissionStatusModelImpl) {

		Object[] args = new Object[] {
			formSubmissionStatusModelImpl.getFormInstanceRecordId()
		};

		finderCache.putResult(
			_finderPathCountByFormInstanceRecordId, args, Long.valueOf(1),
			false);
		finderCache.putResult(
			_finderPathFetchByFormInstanceRecordId, args,
			formSubmissionStatusModelImpl, false);
	}

	/**
	 * Creates a new form submission status with the primary key. Does not add the form submission status to the database.
	 *
	 * @param formSubmissionStatusId the primary key for the new form submission status
	 * @return the new form submission status
	 */
	@Override
	public FormSubmissionStatus create(long formSubmissionStatusId) {
		FormSubmissionStatus formSubmissionStatus =
			new FormSubmissionStatusImpl();

		formSubmissionStatus.setNew(true);
		formSubmissionStatus.setPrimaryKey(formSubmissionStatusId);

		formSubmissionStatus.setCompanyId(CompanyThreadLocal.getCompanyId());

		return formSubmissionStatus;
	}

	/**
	 * Removes the form submission status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status that was removed
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	@Override
	public FormSubmissionStatus remove(long formSubmissionStatusId)
		throws NoSuchFormSubmissionStatusException {

		return remove((Serializable)formSubmissionStatusId);
	}

	/**
	 * Removes the form submission status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the form submission status
	 * @return the form submission status that was removed
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	@Override
	public FormSubmissionStatus remove(Serializable primaryKey)
		throws NoSuchFormSubmissionStatusException {

		Session session = null;

		try {
			session = openSession();

			FormSubmissionStatus formSubmissionStatus =
				(FormSubmissionStatus)session.get(
					FormSubmissionStatusImpl.class, primaryKey);

			if (formSubmissionStatus == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFormSubmissionStatusException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(formSubmissionStatus);
		}
		catch (NoSuchFormSubmissionStatusException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected FormSubmissionStatus removeImpl(
		FormSubmissionStatus formSubmissionStatus) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(formSubmissionStatus)) {
				formSubmissionStatus = (FormSubmissionStatus)session.get(
					FormSubmissionStatusImpl.class,
					formSubmissionStatus.getPrimaryKeyObj());
			}

			if (formSubmissionStatus != null) {
				session.delete(formSubmissionStatus);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (formSubmissionStatus != null) {
			clearCache(formSubmissionStatus);
		}

		return formSubmissionStatus;
	}

	@Override
	public FormSubmissionStatus updateImpl(
		FormSubmissionStatus formSubmissionStatus) {

		boolean isNew = formSubmissionStatus.isNew();

		if (!(formSubmissionStatus instanceof FormSubmissionStatusModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(formSubmissionStatus.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					formSubmissionStatus);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in formSubmissionStatus proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FormSubmissionStatus implementation " +
					formSubmissionStatus.getClass());
		}

		FormSubmissionStatusModelImpl formSubmissionStatusModelImpl =
			(FormSubmissionStatusModelImpl)formSubmissionStatus;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (formSubmissionStatus.getCreateDate() == null)) {
			if (serviceContext == null) {
				formSubmissionStatus.setCreateDate(date);
			}
			else {
				formSubmissionStatus.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!formSubmissionStatusModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				formSubmissionStatus.setModifiedDate(date);
			}
			else {
				formSubmissionStatus.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(formSubmissionStatus);
			}
			else {
				formSubmissionStatus = (FormSubmissionStatus)session.merge(
					formSubmissionStatus);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FormSubmissionStatusImpl.class, formSubmissionStatusModelImpl,
			false, true);

		cacheUniqueFindersCache(formSubmissionStatusModelImpl);

		if (isNew) {
			formSubmissionStatus.setNew(false);
		}

		formSubmissionStatus.resetOriginalValues();

		return formSubmissionStatus;
	}

	/**
	 * Returns the form submission status with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the form submission status
	 * @return the form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	@Override
	public FormSubmissionStatus findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFormSubmissionStatusException {

		FormSubmissionStatus formSubmissionStatus = fetchByPrimaryKey(
			primaryKey);

		if (formSubmissionStatus == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFormSubmissionStatusException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return formSubmissionStatus;
	}

	/**
	 * Returns the form submission status with the primary key or throws a <code>NoSuchFormSubmissionStatusException</code> if it could not be found.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status
	 * @throws NoSuchFormSubmissionStatusException if a form submission status with the primary key could not be found
	 */
	@Override
	public FormSubmissionStatus findByPrimaryKey(long formSubmissionStatusId)
		throws NoSuchFormSubmissionStatusException {

		return findByPrimaryKey((Serializable)formSubmissionStatusId);
	}

	/**
	 * Returns the form submission status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formSubmissionStatusId the primary key of the form submission status
	 * @return the form submission status, or <code>null</code> if a form submission status with the primary key could not be found
	 */
	@Override
	public FormSubmissionStatus fetchByPrimaryKey(long formSubmissionStatusId) {
		return fetchByPrimaryKey((Serializable)formSubmissionStatusId);
	}

	/**
	 * Returns all the form submission statuses.
	 *
	 * @return the form submission statuses
	 */
	@Override
	public List<FormSubmissionStatus> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<FormSubmissionStatus> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<FormSubmissionStatus> findAll(
		int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<FormSubmissionStatus> findAll(
		int start, int end,
		OrderByComparator<FormSubmissionStatus> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<FormSubmissionStatus> list = null;

		if (useFinderCache) {
			list = (List<FormSubmissionStatus>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FORMSUBMISSIONSTATUS);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FORMSUBMISSIONSTATUS;

				sql = sql.concat(FormSubmissionStatusModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FormSubmissionStatus>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the form submission statuses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FormSubmissionStatus formSubmissionStatus : findAll()) {
			remove(formSubmissionStatus);
		}
	}

	/**
	 * Returns the number of form submission statuses.
	 *
	 * @return the number of form submission statuses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_FORMSUBMISSIONSTATUS);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "formSubmissionStatusId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FORMSUBMISSIONSTATUS;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FormSubmissionStatusModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the form submission status persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new FormSubmissionStatusModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", FormSubmissionStatus.class.getName()));

		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByFormInstanceRecordId = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByFormInstanceRecordId",
			new String[] {Long.class.getName()},
			new String[] {"formInstanceRecordId"}, true);

		_finderPathCountByFormInstanceRecordId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFormInstanceRecordId", new String[] {Long.class.getName()},
			new String[] {"formInstanceRecordId"}, false);

		_finderPathWithPaginationFindBySeen = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySeen",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"seen"}, true);

		_finderPathWithoutPaginationFindBySeen = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySeen",
			new String[] {Boolean.class.getName()}, new String[] {"seen"},
			true);

		_finderPathCountBySeen = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySeen",
			new String[] {Boolean.class.getName()}, new String[] {"seen"},
			false);

		_finderPathWithPaginationFindByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByGroupId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByG_S = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "seen"}, true);

		_finderPathWithoutPaginationFindByG_S = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "seen"}, true);

		_finderPathCountByG_S = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "seen"}, false);

		FormSubmissionStatusUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FormSubmissionStatusUtil.setPersistence(null);

		entityCache.removeCache(FormSubmissionStatusImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Override
	@Reference(
		target = FormCounterPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FormCounterPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FormCounterPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FORMSUBMISSIONSTATUS =
		"SELECT formSubmissionStatus FROM FormSubmissionStatus formSubmissionStatus";

	private static final String _SQL_SELECT_FORMSUBMISSIONSTATUS_WHERE =
		"SELECT formSubmissionStatus FROM FormSubmissionStatus formSubmissionStatus WHERE ";

	private static final String _SQL_COUNT_FORMSUBMISSIONSTATUS =
		"SELECT COUNT(formSubmissionStatus) FROM FormSubmissionStatus formSubmissionStatus";

	private static final String _SQL_COUNT_FORMSUBMISSIONSTATUS_WHERE =
		"SELECT COUNT(formSubmissionStatus) FROM FormSubmissionStatus formSubmissionStatus WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"formSubmissionStatus.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FormSubmissionStatus exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FormSubmissionStatus exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FormSubmissionStatusPersistenceImpl.class);

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();
	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class FormSubmissionStatusModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return new Object[0];
				}

				return null;
			}

			FormSubmissionStatusModelImpl formSubmissionStatusModelImpl =
				(FormSubmissionStatusModelImpl)baseModel;

			long columnBitmask =
				formSubmissionStatusModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					formSubmissionStatusModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						formSubmissionStatusModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					formSubmissionStatusModelImpl, columnNames, original);
			}

			return null;
		}

		private static Object[] _getValue(
			FormSubmissionStatusModelImpl formSubmissionStatusModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						formSubmissionStatusModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = formSubmissionStatusModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}