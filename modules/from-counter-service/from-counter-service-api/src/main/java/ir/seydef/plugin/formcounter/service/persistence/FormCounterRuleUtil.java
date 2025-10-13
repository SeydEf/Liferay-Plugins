/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import ir.seydef.plugin.formcounter.model.FormCounterRule;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the form counter rule service. This utility wraps <code>ir.seydef.plugin.formcounter.service.persistence.impl.FormCounterRulePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRulePersistence
 * @generated
 */
public class FormCounterRuleUtil {

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
	public static void clearCache(FormCounterRule formCounterRule) {
		getPersistence().clearCache(formCounterRule);
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
	public static Map<Serializable, FormCounterRule> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FormCounterRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FormCounterRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FormCounterRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FormCounterRule update(FormCounterRule formCounterRule) {
		return getPersistence().update(formCounterRule);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FormCounterRule update(
		FormCounterRule formCounterRule, ServiceContext serviceContext) {

		return getPersistence().update(formCounterRule, serviceContext);
	}

	/**
	 * Returns all the form counter rules where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching form counter rules
	 */
	public static List<FormCounterRule> findByActive(boolean active) {
		return getPersistence().findByActive(active);
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
	public static List<FormCounterRule> findByActive(
		boolean active, int start, int end) {

		return getPersistence().findByActive(active, start, end);
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
	public static List<FormCounterRule> findByActive(
		boolean active, int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator) {

		return getPersistence().findByActive(
			active, start, end, orderByComparator);
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
	public static List<FormCounterRule> findByActive(
		boolean active, int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByActive(
			active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form counter rule
	 * @throws NoSuchRuleException if a matching form counter rule could not be found
	 */
	public static FormCounterRule findByActive_First(
			boolean active,
			OrderByComparator<FormCounterRule> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.NoSuchRuleException {

		return getPersistence().findByActive_First(active, orderByComparator);
	}

	/**
	 * Returns the first form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form counter rule, or <code>null</code> if a matching form counter rule could not be found
	 */
	public static FormCounterRule fetchByActive_First(
		boolean active, OrderByComparator<FormCounterRule> orderByComparator) {

		return getPersistence().fetchByActive_First(active, orderByComparator);
	}

	/**
	 * Returns the last form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form counter rule
	 * @throws NoSuchRuleException if a matching form counter rule could not be found
	 */
	public static FormCounterRule findByActive_Last(
			boolean active,
			OrderByComparator<FormCounterRule> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.NoSuchRuleException {

		return getPersistence().findByActive_Last(active, orderByComparator);
	}

	/**
	 * Returns the last form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form counter rule, or <code>null</code> if a matching form counter rule could not be found
	 */
	public static FormCounterRule fetchByActive_Last(
		boolean active, OrderByComparator<FormCounterRule> orderByComparator) {

		return getPersistence().fetchByActive_Last(active, orderByComparator);
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
	public static FormCounterRule[] findByActive_PrevAndNext(
			long formCounterRuleId, boolean active,
			OrderByComparator<FormCounterRule> orderByComparator)
		throws ir.seydef.plugin.formcounter.exception.NoSuchRuleException {

		return getPersistence().findByActive_PrevAndNext(
			formCounterRuleId, active, orderByComparator);
	}

	/**
	 * Removes all the form counter rules where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	public static void removeByActive(boolean active) {
		getPersistence().removeByActive(active);
	}

	/**
	 * Returns the number of form counter rules where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching form counter rules
	 */
	public static int countByActive(boolean active) {
		return getPersistence().countByActive(active);
	}

	/**
	 * Caches the form counter rule in the entity cache if it is enabled.
	 *
	 * @param formCounterRule the form counter rule
	 */
	public static void cacheResult(FormCounterRule formCounterRule) {
		getPersistence().cacheResult(formCounterRule);
	}

	/**
	 * Caches the form counter rules in the entity cache if it is enabled.
	 *
	 * @param formCounterRules the form counter rules
	 */
	public static void cacheResult(List<FormCounterRule> formCounterRules) {
		getPersistence().cacheResult(formCounterRules);
	}

	/**
	 * Creates a new form counter rule with the primary key. Does not add the form counter rule to the database.
	 *
	 * @param formCounterRuleId the primary key for the new form counter rule
	 * @return the new form counter rule
	 */
	public static FormCounterRule create(long formCounterRuleId) {
		return getPersistence().create(formCounterRuleId);
	}

	/**
	 * Removes the form counter rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule that was removed
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	public static FormCounterRule remove(long formCounterRuleId)
		throws ir.seydef.plugin.formcounter.exception.NoSuchRuleException {

		return getPersistence().remove(formCounterRuleId);
	}

	public static FormCounterRule updateImpl(FormCounterRule formCounterRule) {
		return getPersistence().updateImpl(formCounterRule);
	}

	/**
	 * Returns the form counter rule with the primary key or throws a <code>NoSuchRuleException</code> if it could not be found.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	public static FormCounterRule findByPrimaryKey(long formCounterRuleId)
		throws ir.seydef.plugin.formcounter.exception.NoSuchRuleException {

		return getPersistence().findByPrimaryKey(formCounterRuleId);
	}

	/**
	 * Returns the form counter rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule, or <code>null</code> if a form counter rule with the primary key could not be found
	 */
	public static FormCounterRule fetchByPrimaryKey(long formCounterRuleId) {
		return getPersistence().fetchByPrimaryKey(formCounterRuleId);
	}

	/**
	 * Returns all the form counter rules.
	 *
	 * @return the form counter rules
	 */
	public static List<FormCounterRule> findAll() {
		return getPersistence().findAll();
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
	public static List<FormCounterRule> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<FormCounterRule> findAll(
		int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<FormCounterRule> findAll(
		int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the form counter rules from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of form counter rules.
	 *
	 * @return the number of form counter rules
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FormCounterRulePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(FormCounterRulePersistence persistence) {
		_persistence = persistence;
	}

	private static volatile FormCounterRulePersistence _persistence;

}