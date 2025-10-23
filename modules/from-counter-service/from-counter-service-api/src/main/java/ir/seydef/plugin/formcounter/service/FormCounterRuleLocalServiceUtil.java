/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import ir.seydef.plugin.formcounter.model.FormCounterRule;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for FormCounterRule. This utility wraps
 * <code>ir.seydef.plugin.formcounter.service.impl.FormCounterRuleLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRuleLocalService
 * @generated
 */
public class FormCounterRuleLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>ir.seydef.plugin.formcounter.service.impl.FormCounterRuleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the form counter rule to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FormCounterRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param formCounterRule the form counter rule
	 * @return the form counter rule that was added
	 */
	public static FormCounterRule addFormCounterRule(
		FormCounterRule formCounterRule) {

		return getService().addFormCounterRule(formCounterRule);
	}

	public static FormCounterRule addFormCounterRule(
			String ruleName, String description, String ruleConditions,
			String logicOperator, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFormCounterRule(
			ruleName, description, ruleConditions, logicOperator, active,
			serviceContext);
	}

	/**
	 * Creates a new form counter rule with the primary key. Does not add the form counter rule to the database.
	 *
	 * @param formCounterRuleId the primary key for the new form counter rule
	 * @return the new form counter rule
	 */
	public static FormCounterRule createFormCounterRule(
		long formCounterRuleId) {

		return getService().createFormCounterRule(formCounterRuleId);
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
	 * Deletes the form counter rule from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FormCounterRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param formCounterRule the form counter rule
	 * @return the form counter rule that was removed
	 */
	public static FormCounterRule deleteFormCounterRule(
		FormCounterRule formCounterRule) {

		return getService().deleteFormCounterRule(formCounterRule);
	}

	/**
	 * Deletes the form counter rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FormCounterRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule that was removed
	 * @throws PortalException if a form counter rule with the primary key could not be found
	 */
	public static FormCounterRule deleteFormCounterRule(long formCounterRuleId)
		throws PortalException {

		return getService().deleteFormCounterRule(formCounterRuleId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ir.seydef.plugin.formcounter.model.impl.FormCounterRuleModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ir.seydef.plugin.formcounter.model.impl.FormCounterRuleModelImpl</code>.
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

	public static FormCounterRule fetchFormCounterRule(long formCounterRuleId) {
		return getService().fetchFormCounterRule(formCounterRuleId);
	}

	public static List<FormCounterRule> findByActive(boolean active) {
		return getService().findByActive(active);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the form counter rule with the primary key.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule
	 * @throws PortalException if a form counter rule with the primary key could not be found
	 */
	public static FormCounterRule getFormCounterRule(long formCounterRuleId)
		throws PortalException {

		return getService().getFormCounterRule(formCounterRuleId);
	}

	/**
	 * Returns a range of all the form counter rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ir.seydef.plugin.formcounter.model.impl.FormCounterRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of form counter rules
	 * @param end the upper bound of the range of form counter rules (not inclusive)
	 * @return the range of form counter rules
	 */
	public static List<FormCounterRule> getFormCounterRules(
		int start, int end) {

		return getService().getFormCounterRules(start, end);
	}

	/**
	 * Returns the number of form counter rules.
	 *
	 * @return the number of form counter rules
	 */
	public static int getFormCounterRulesCount() {
		return getService().getFormCounterRulesCount();
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

	/**
	 * Updates the form counter rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FormCounterRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param formCounterRule the form counter rule
	 * @return the form counter rule that was updated
	 */
	public static FormCounterRule updateFormCounterRule(
		FormCounterRule formCounterRule) {

		return getService().updateFormCounterRule(formCounterRule);
	}

	public static FormCounterRule updateFormCounterRule(
			long formCounterRuleId, String ruleName, String description,
			String ruleConditions, String logicOperator, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFormCounterRule(
			formCounterRuleId, ruleName, description, ruleConditions,
			logicOperator, active, serviceContext);
	}

	public static FormCounterRuleLocalService getService() {
		return _service;
	}

	public static void setService(FormCounterRuleLocalService service) {
		_service = service;
	}

	private static volatile FormCounterRuleLocalService _service;

}