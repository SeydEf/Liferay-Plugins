/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link FormCounterRuleLocalService}.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRuleLocalService
 * @generated
 */
public class FormCounterRuleLocalServiceWrapper
	implements FormCounterRuleLocalService,
			   ServiceWrapper<FormCounterRuleLocalService> {

	public FormCounterRuleLocalServiceWrapper(
		FormCounterRuleLocalService formCounterRuleLocalService) {

		_formCounterRuleLocalService = formCounterRuleLocalService;
	}

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
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
		addFormCounterRule(
			ir.seydef.plugin.formcounter.model.FormCounterRule
				formCounterRule) {

		return _formCounterRuleLocalService.addFormCounterRule(formCounterRule);
	}

	/**
	 * Add a new form counter rule
	 *
	 * @param formCounterRuleId the primary key
	 * @param ruleName the rule name
	 * @param description the rule description
	 * @param ruleConditions JSON string representing the rule conditions
	 * @param logicOperator the logic operator (AND, OR)
	 * @param active whether the rule is active
	 * @param serviceContext the service context
	 * @return the new form counter rule
	 * @throws PortalException
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			addFormCounterRule(
				long formCounterRuleId, String ruleName, String description,
				String ruleConditions, String logicOperator, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleLocalService.addFormCounterRule(
			formCounterRuleId, ruleName, description, ruleConditions,
			logicOperator, active, serviceContext);
	}

	/**
	 * Creates a new form counter rule with the primary key. Does not add the form counter rule to the database.
	 *
	 * @param formCounterRuleId the primary key for the new form counter rule
	 * @return the new form counter rule
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
		createFormCounterRule(long formCounterRuleId) {

		return _formCounterRuleLocalService.createFormCounterRule(
			formCounterRuleId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleLocalService.createPersistedModel(primaryKeyObj);
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
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
		deleteFormCounterRule(
			ir.seydef.plugin.formcounter.model.FormCounterRule
				formCounterRule) {

		return _formCounterRuleLocalService.deleteFormCounterRule(
			formCounterRule);
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
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			deleteFormCounterRule(long formCounterRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleLocalService.deleteFormCounterRule(
			formCounterRuleId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _formCounterRuleLocalService.dynamicQuery();
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

		return _formCounterRuleLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _formCounterRuleLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _formCounterRuleLocalService.dynamicQuery(
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

		return _formCounterRuleLocalService.dynamicQueryCount(dynamicQuery);
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

		return _formCounterRuleLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
		fetchFormCounterRule(long formCounterRuleId) {

		return _formCounterRuleLocalService.fetchFormCounterRule(
			formCounterRuleId);
	}

	/**
	 * Find form counter rules by active status
	 *
	 * @param active whether rules are active
	 * @return list of matching rules
	 */
	@Override
	public java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
		findByActive(boolean active) {

		return _formCounterRuleLocalService.findByActive(active);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _formCounterRuleLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the form counter rule with the primary key.
	 *
	 * @param formCounterRuleId the primary key of the form counter rule
	 * @return the form counter rule
	 * @throws PortalException if a form counter rule with the primary key could not be found
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			getFormCounterRule(long formCounterRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleLocalService.getFormCounterRule(
			formCounterRuleId);
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
	@Override
	public java.util.List<ir.seydef.plugin.formcounter.model.FormCounterRule>
		getFormCounterRules(int start, int end) {

		return _formCounterRuleLocalService.getFormCounterRules(start, end);
	}

	/**
	 * Returns the number of form counter rules.
	 *
	 * @return the number of form counter rules
	 */
	@Override
	public int getFormCounterRulesCount() {
		return _formCounterRuleLocalService.getFormCounterRulesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _formCounterRuleLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _formCounterRuleLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
		updateFormCounterRule(
			ir.seydef.plugin.formcounter.model.FormCounterRule
				formCounterRule) {

		return _formCounterRuleLocalService.updateFormCounterRule(
			formCounterRule);
	}

	/**
	 * Update an existing form counter rule
	 *
	 * @param formCounterRuleId the primary key
	 * @param ruleName the rule name
	 * @param description the rule description
	 * @param ruleConditions JSON string representing the rule conditions
	 * @param logicOperator the logic operator (AND, OR)
	 * @param active whether the rule is active
	 * @param serviceContext the service context
	 * @return the updated form counter rule
	 * @throws PortalException
	 */
	@Override
	public ir.seydef.plugin.formcounter.model.FormCounterRule
			updateFormCounterRule(
				long formCounterRuleId, String ruleName, String description,
				String ruleConditions, String logicOperator, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _formCounterRuleLocalService.updateFormCounterRule(
			formCounterRuleId, ruleName, description, ruleConditions,
			logicOperator, active, serviceContext);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _formCounterRuleLocalService.getBasePersistence();
	}

	@Override
	public FormCounterRuleLocalService getWrappedService() {
		return _formCounterRuleLocalService;
	}

	@Override
	public void setWrappedService(
		FormCounterRuleLocalService formCounterRuleLocalService) {

		_formCounterRuleLocalService = formCounterRuleLocalService;
	}

	private FormCounterRuleLocalService _formCounterRuleLocalService;

}