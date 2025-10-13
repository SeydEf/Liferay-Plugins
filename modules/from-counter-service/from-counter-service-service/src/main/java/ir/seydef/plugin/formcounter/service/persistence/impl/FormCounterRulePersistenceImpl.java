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
import com.liferay.portal.kernel.util.SetUtil;

import ir.seydef.plugin.formcounter.exception.NoSuchRuleException;
import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.model.impl.FormCounterRuleImpl;
import ir.seydef.plugin.formcounter.model.impl.FormCounterRuleModelImpl;
import ir.seydef.plugin.formcounter.service.persistence.FormCounterRulePersistence;
import ir.seydef.plugin.formcounter.service.persistence.FormCounterRuleUtil;
import ir.seydef.plugin.formcounter.service.persistence.impl.constants.FormCounterPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
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
 * The persistence implementation for the form counter rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @generated
 */
@Component(service = FormCounterRulePersistence.class)
public class FormCounterRulePersistenceImpl
	extends BasePersistenceImpl<FormCounterRule>
	implements FormCounterRulePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FormCounterRuleUtil</code> to access the form counter rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FormCounterRuleImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByActive;
	private FinderPath _finderPathWithoutPaginationFindByActive;
	private FinderPath _finderPathCountByActive;

	/**
	 * Returns all the form counter rules where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching form counter rules
	 */
	@Override
	public List<FormCounterRule> findByActive(boolean active) {
		return findByActive(active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the form counter rules where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormCounterRuleModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of form counter rules
	 * @param end the upper bound of the range of form counter rules (not inclusive)
	 * @return the range of matching form counter rules
	 */
	@Override
	public List<FormCounterRule> findByActive(
		boolean active, int start, int end) {

		return findByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the form counter rules where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormCounterRuleModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of form counter rules
	 * @param end the upper bound of the range of form counter rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching form counter rules
	 */
	@Override
	public List<FormCounterRule> findByActive(
		boolean active, int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator) {

		return findByActive(active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the form counter rules where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormCounterRuleModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of form counter rules
	 * @param end the upper bound of the range of form counter rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching form counter rules
	 */
	@Override
	public List<FormCounterRule> findByActive(
		boolean active, int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByActive;
				finderArgs = new Object[] {active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByActive;
			finderArgs = new Object[] {active, start, end, orderByComparator};
		}

		List<FormCounterRule> list = null;

		if (useFinderCache) {
			list = (List<FormCounterRule>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FormCounterRule formCounterRule : list) {
					if (active != formCounterRule.isActive()) {
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

			sb.append(_SQL_SELECT_FORMCOUNTERRULE_WHERE);

			sb.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FormCounterRuleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				list = (List<FormCounterRule>)QueryUtil.list(
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
	 * Returns the first form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form counter rule
	 * @throws NoSuchRuleException if a matching form counter rule could not be found
	 */
	@Override
	public FormCounterRule findByActive_First(
			boolean active,
			OrderByComparator<FormCounterRule> orderByComparator)
		throws NoSuchRuleException {

		FormCounterRule formCounterRule = fetchByActive_First(
			active, orderByComparator);

		if (formCounterRule != null) {
			return formCounterRule;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchRuleException(sb.toString());
	}

	/**
	 * Returns the first form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form counter rule, or <code>null</code> if a matching form counter rule could not be found
	 */
	@Override
	public FormCounterRule fetchByActive_First(
		boolean active, OrderByComparator<FormCounterRule> orderByComparator) {

		List<FormCounterRule> list = findByActive(
			active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form counter rule
	 * @throws NoSuchRuleException if a matching form counter rule could not be found
	 */
	@Override
	public FormCounterRule findByActive_Last(
			boolean active,
			OrderByComparator<FormCounterRule> orderByComparator)
		throws NoSuchRuleException {

		FormCounterRule formCounterRule = fetchByActive_Last(
			active, orderByComparator);

		if (formCounterRule != null) {
			return formCounterRule;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchRuleException(sb.toString());
	}

	/**
	 * Returns the last form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form counter rule, or <code>null</code> if a matching form counter rule could not be found
	 */
	@Override
	public FormCounterRule fetchByActive_Last(
		boolean active, OrderByComparator<FormCounterRule> orderByComparator) {

		int count = countByActive(active);

		if (count == 0) {
			return null;
		}

		List<FormCounterRule> list = findByActive(
			active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the form counter rules before and after the current form counter rule in the ordered set where active = &#63;.
	 *
	 * @param formCounterRuleId the primary key of the current form counter rule
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next form counter rule
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	@Override
	public FormCounterRule[] findByActive_PrevAndNext(
			long formCounterRuleId, boolean active,
			OrderByComparator<FormCounterRule> orderByComparator)
		throws NoSuchRuleException {

		FormCounterRule formCounterRule = findByPrimaryKey(formCounterRuleId);

		Session session = null;

		try {
			session = openSession();

			FormCounterRule[] array = new FormCounterRuleImpl[3];

			array[0] = getByActive_PrevAndNext(
				session, formCounterRule, active, orderByComparator, true);

			array[1] = formCounterRule;

			array[2] = getByActive_PrevAndNext(
				session, formCounterRule, active, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FormCounterRule getByActive_PrevAndNext(
		Session session, FormCounterRule formCounterRule, boolean active,
		OrderByComparator<FormCounterRule> orderByComparator,
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

		sb.append(_SQL_SELECT_FORMCOUNTERRULE_WHERE);

		sb.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

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
			sb.append(FormCounterRuleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						formCounterRule)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FormCounterRule> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the form counter rules where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	@Override
	public void removeByActive(boolean active) {
		for (FormCounterRule formCounterRule :
				findByActive(
					active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(formCounterRule);
		}
	}

	/**
	 * Returns the number of form counter rules where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching form counter rules
	 */
	@Override
	public int countByActive(boolean active) {
		FinderPath finderPath = _finderPathCountByActive;

		Object[] finderArgs = new Object[] {active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FORMCOUNTERRULE_WHERE);

			sb.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

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

	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2 =
		"formCounterRule.active = ?";

	public FormCounterRulePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FormCounterRule.class);

		setModelImplClass(FormCounterRuleImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the form counter rule in the entity cache if it is enabled.
	 *
	 * @param formCounterRule the form counter rule
	 */
	@Override
	public void cacheResult(FormCounterRule formCounterRule) {
		entityCache.putResult(
			FormCounterRuleImpl.class, formCounterRule.getPrimaryKey(),
			formCounterRule);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the form counter rules in the entity cache if it is enabled.
	 *
	 * @param formCounterRules the form counter rules
	 */
	@Override
	public void cacheResult(List<FormCounterRule> formCounterRules) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (formCounterRules.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FormCounterRule formCounterRule : formCounterRules) {
			if (entityCache.getResult(
					FormCounterRuleImpl.class,
					formCounterRule.getPrimaryKey()) == null) {

				cacheResult(formCounterRule);
			}
		}
	}

	/**
	 * Clears the cache for all form counter rules.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FormCounterRuleImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the form counter rule.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FormCounterRule formCounterRule) {
		entityCache.removeResult(FormCounterRuleImpl.class, formCounterRule);
	}

	@Override
	public void clearCache(List<FormCounterRule> formCounterRules) {
		for (FormCounterRule formCounterRule : formCounterRules) {
			entityCache.removeResult(
				FormCounterRuleImpl.class, formCounterRule);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FormCounterRuleImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new form counter rule with the primary key. Does not add the form counter rule to the database.
	 *
	 * @param formCounterRuleId the primary key for the new form counter rule
	 * @return the new form counter rule
	 */
	@Override
	public FormCounterRule create(long formCounterRuleId) {
		FormCounterRule formCounterRule = new FormCounterRuleImpl();

		formCounterRule.setNew(true);
		formCounterRule.setPrimaryKey(formCounterRuleId);

		formCounterRule.setCompanyId(CompanyThreadLocal.getCompanyId());

		return formCounterRule;
	}

	/**
	 * Removes the form counter rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule that was removed
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	@Override
	public FormCounterRule remove(long formCounterRuleId)
		throws NoSuchRuleException {

		return remove((Serializable)formCounterRuleId);
	}

	/**
	 * Removes the form counter rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the form counter rule
	 * @return the form counter rule that was removed
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	@Override
	public FormCounterRule remove(Serializable primaryKey)
		throws NoSuchRuleException {

		Session session = null;

		try {
			session = openSession();

			FormCounterRule formCounterRule = (FormCounterRule)session.get(
				FormCounterRuleImpl.class, primaryKey);

			if (formCounterRule == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRuleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(formCounterRule);
		}
		catch (NoSuchRuleException noSuchEntityException) {
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
	protected FormCounterRule removeImpl(FormCounterRule formCounterRule) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(formCounterRule)) {
				formCounterRule = (FormCounterRule)session.get(
					FormCounterRuleImpl.class,
					formCounterRule.getPrimaryKeyObj());
			}

			if (formCounterRule != null) {
				session.delete(formCounterRule);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (formCounterRule != null) {
			clearCache(formCounterRule);
		}

		return formCounterRule;
	}

	@Override
	public FormCounterRule updateImpl(FormCounterRule formCounterRule) {
		boolean isNew = formCounterRule.isNew();

		if (!(formCounterRule instanceof FormCounterRuleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(formCounterRule.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					formCounterRule);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in formCounterRule proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FormCounterRule implementation " +
					formCounterRule.getClass());
		}

		FormCounterRuleModelImpl formCounterRuleModelImpl =
			(FormCounterRuleModelImpl)formCounterRule;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (formCounterRule.getCreateDate() == null)) {
			if (serviceContext == null) {
				formCounterRule.setCreateDate(date);
			}
			else {
				formCounterRule.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!formCounterRuleModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				formCounterRule.setModifiedDate(date);
			}
			else {
				formCounterRule.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(formCounterRule);
			}
			else {
				formCounterRule = (FormCounterRule)session.merge(
					formCounterRule);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FormCounterRuleImpl.class, formCounterRuleModelImpl, false, true);

		if (isNew) {
			formCounterRule.setNew(false);
		}

		formCounterRule.resetOriginalValues();

		return formCounterRule;
	}

	/**
	 * Returns the form counter rule with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the form counter rule
	 * @return the form counter rule
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	@Override
	public FormCounterRule findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRuleException {

		FormCounterRule formCounterRule = fetchByPrimaryKey(primaryKey);

		if (formCounterRule == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRuleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return formCounterRule;
	}

	/**
	 * Returns the form counter rule with the primary key or throws a <code>NoSuchRuleException</code> if it could not be found.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	@Override
	public FormCounterRule findByPrimaryKey(long formCounterRuleId)
		throws NoSuchRuleException {

		return findByPrimaryKey((Serializable)formCounterRuleId);
	}

	/**
	 * Returns the form counter rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule, or <code>null</code> if a form counter rule with the primary key could not be found
	 */
	@Override
	public FormCounterRule fetchByPrimaryKey(long formCounterRuleId) {
		return fetchByPrimaryKey((Serializable)formCounterRuleId);
	}

	/**
	 * Returns all the form counter rules.
	 *
	 * @return the form counter rules
	 */
	@Override
	public List<FormCounterRule> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the form counter rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormCounterRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of form counter rules
	 * @param end the upper bound of the range of form counter rules (not inclusive)
	 * @return the range of form counter rules
	 */
	@Override
	public List<FormCounterRule> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the form counter rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormCounterRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of form counter rules
	 * @param end the upper bound of the range of form counter rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of form counter rules
	 */
	@Override
	public List<FormCounterRule> findAll(
		int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the form counter rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FormCounterRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of form counter rules
	 * @param end the upper bound of the range of form counter rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of form counter rules
	 */
	@Override
	public List<FormCounterRule> findAll(
		int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator,
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

		List<FormCounterRule> list = null;

		if (useFinderCache) {
			list = (List<FormCounterRule>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FORMCOUNTERRULE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FORMCOUNTERRULE;

				sql = sql.concat(FormCounterRuleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FormCounterRule>)QueryUtil.list(
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
	 * Removes all the form counter rules from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FormCounterRule formCounterRule : findAll()) {
			remove(formCounterRule);
		}
	}

	/**
	 * Returns the number of form counter rules.
	 *
	 * @return the number of form counter rules
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FORMCOUNTERRULE);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "formCounterRuleId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FORMCOUNTERRULE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FormCounterRuleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the form counter rule persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new FormCounterRuleModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", FormCounterRule.class.getName()));

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

		_finderPathWithPaginationFindByActive = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActive",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"active_"}, true);

		_finderPathWithoutPaginationFindByActive = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActive",
			new String[] {Boolean.class.getName()}, new String[] {"active_"},
			true);

		_finderPathCountByActive = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActive",
			new String[] {Boolean.class.getName()}, new String[] {"active_"},
			false);

		FormCounterRuleUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FormCounterRuleUtil.setPersistence(null);

		entityCache.removeCache(FormCounterRuleImpl.class.getName());

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

	private static final String _SQL_SELECT_FORMCOUNTERRULE =
		"SELECT formCounterRule FROM FormCounterRule formCounterRule";

	private static final String _SQL_SELECT_FORMCOUNTERRULE_WHERE =
		"SELECT formCounterRule FROM FormCounterRule formCounterRule WHERE ";

	private static final String _SQL_COUNT_FORMCOUNTERRULE =
		"SELECT COUNT(formCounterRule) FROM FormCounterRule formCounterRule";

	private static final String _SQL_COUNT_FORMCOUNTERRULE_WHERE =
		"SELECT COUNT(formCounterRule) FROM FormCounterRule formCounterRule WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "formCounterRule.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FormCounterRule exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FormCounterRule exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FormCounterRulePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"active"});

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

	private static class FormCounterRuleModelArgumentsResolver
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

			FormCounterRuleModelImpl formCounterRuleModelImpl =
				(FormCounterRuleModelImpl)baseModel;

			long columnBitmask = formCounterRuleModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					formCounterRuleModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						formCounterRuleModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					formCounterRuleModelImpl, columnNames, original);
			}

			return null;
		}

		private static Object[] _getValue(
			FormCounterRuleModelImpl formCounterRuleModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						formCounterRuleModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = formCounterRuleModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}