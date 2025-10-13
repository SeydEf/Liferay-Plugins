/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import ir.seydef.plugin.formcounter.exception.NoSuchRuleException;
import ir.seydef.plugin.formcounter.model.FormCounterRule;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the form counter rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRuleUtil
 * @generated
 */
@ProviderType
public interface FormCounterRulePersistence
	extends BasePersistence<FormCounterRule> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FormCounterRuleUtil} to access the form counter rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the form counter rules where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching form counter rules
	 */
	public java.util.List<FormCounterRule> findByActive(boolean active);

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
	public java.util.List<FormCounterRule> findByActive(
		boolean active, int start, int end);

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
	public java.util.List<FormCounterRule> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormCounterRule>
			orderByComparator);

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
	public java.util.List<FormCounterRule> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormCounterRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form counter rule
	 * @throws NoSuchRuleException if a matching form counter rule could not be found
	 */
	public FormCounterRule findByActive_First(
			boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<FormCounterRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Returns the first form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching form counter rule, or <code>null</code> if a matching form counter rule could not be found
	 */
	public FormCounterRule fetchByActive_First(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<FormCounterRule>
			orderByComparator);

	/**
	 * Returns the last form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form counter rule
	 * @throws NoSuchRuleException if a matching form counter rule could not be found
	 */
	public FormCounterRule findByActive_Last(
			boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<FormCounterRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Returns the last form counter rule in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching form counter rule, or <code>null</code> if a matching form counter rule could not be found
	 */
	public FormCounterRule fetchByActive_Last(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<FormCounterRule>
			orderByComparator);

	/**
	 * Returns the form counter rules before and after the current form counter rule in the ordered set where active = &#63;.
	 *
	 * @param formCounterRuleId the primary key of the current form counter rule
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next form counter rule
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	public FormCounterRule[] findByActive_PrevAndNext(
			long formCounterRuleId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<FormCounterRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Removes all the form counter rules where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	public void removeByActive(boolean active);

	/**
	 * Returns the number of form counter rules where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching form counter rules
	 */
	public int countByActive(boolean active);

	/**
	 * Caches the form counter rule in the entity cache if it is enabled.
	 *
	 * @param formCounterRule the form counter rule
	 */
	public void cacheResult(FormCounterRule formCounterRule);

	/**
	 * Caches the form counter rules in the entity cache if it is enabled.
	 *
	 * @param formCounterRules the form counter rules
	 */
	public void cacheResult(java.util.List<FormCounterRule> formCounterRules);

	/**
	 * Creates a new form counter rule with the primary key. Does not add the form counter rule to the database.
	 *
	 * @param formCounterRuleId the primary key for the new form counter rule
	 * @return the new form counter rule
	 */
	public FormCounterRule create(long formCounterRuleId);

	/**
	 * Removes the form counter rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule that was removed
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	public FormCounterRule remove(long formCounterRuleId)
		throws NoSuchRuleException;

	public FormCounterRule updateImpl(FormCounterRule formCounterRule);

	/**
	 * Returns the form counter rule with the primary key or throws a <code>NoSuchRuleException</code> if it could not be found.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule
	 * @throws NoSuchRuleException if a form counter rule with the primary key could not be found
	 */
	public FormCounterRule findByPrimaryKey(long formCounterRuleId)
		throws NoSuchRuleException;

	/**
	 * Returns the form counter rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule, or <code>null</code> if a form counter rule with the primary key could not be found
	 */
	public FormCounterRule fetchByPrimaryKey(long formCounterRuleId);

	/**
	 * Returns all the form counter rules.
	 *
	 * @return the form counter rules
	 */
	public java.util.List<FormCounterRule> findAll();

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
	public java.util.List<FormCounterRule> findAll(int start, int end);

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
	public java.util.List<FormCounterRule> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormCounterRule>
			orderByComparator);

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
	public java.util.List<FormCounterRule> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FormCounterRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the form counter rules from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of form counter rules.
	 *
	 * @return the number of form counter rules
	 */
	public int countAll();

}