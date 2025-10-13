/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import ir.seydef.plugin.formcounter.model.FormCounterRule;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for FormCounterRule. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRuleServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface FormCounterRuleService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>ir.seydef.plugin.formcounter.service.impl.FormCounterRuleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the form counter rule remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link FormCounterRuleServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Add a new FormCounterRule
	 *
	 * @param ruleName the rule name
	 * @param description the rule description
	 * @param ruleConditions the rule conditions as JSON string
	 * @param logicOperator the logic operator (AND/OR)
	 * @param active whether the rule is active
	 * @param serviceContext the service context
	 * @return the new FormCounterRule
	 * @throws PortalException
	 */
	public FormCounterRule addFormCounterRule(
			String ruleName, String description, String ruleConditions,
			String logicOperator, boolean active, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Delete a FormCounterRule
	 *
	 * @param formCounterRuleId the primary key
	 * @return the deleted FormCounterRule
	 * @throws PortalException
	 */
	public FormCounterRule deleteFormCounterRule(long formCounterRuleId)
		throws PortalException;

	/**
	 * Get FormCounterRule by primary key
	 *
	 * @param formCounterRuleId the primary key
	 * @return FormCounterRule entity
	 * @throws PortalException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FormCounterRule getFormCounterRule(long formCounterRuleId)
		throws PortalException;

	/**
	 * Get all FormCounterRule entities
	 *
	 * @param start the start index
	 * @param end the end index
	 * @return list of FormCounterRule entities
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormCounterRule> getFormCounterRules(int start, int end);

	/**
	 * Get all FormCounterRule entities with ordering
	 *
	 * @param start the start index
	 * @param end the end index
	 * @param orderByComparator the order by comparator
	 * @return list of FormCounterRule entities
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormCounterRule> getFormCounterRules(
		int start, int end,
		OrderByComparator<FormCounterRule> orderByComparator);

	/**
	 * Get FormCounterRules by active status
	 *
	 * @param active the active status
	 * @return list of active/inactive FormCounterRule entities
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormCounterRule> getFormCounterRulesByActive(boolean active);

	/**
	 * Count all FormCounterRules
	 *
	 * @return the count of all FormCounterRules
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFormCounterRulesCount();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Update an existing FormCounterRule
	 *
	 * @param formCounterRuleId the primary key
	 * @param ruleName the rule name
	 * @param description the rule description
	 * @param ruleConditions the rule conditions as JSON string
	 * @param logicOperator the logic operator (AND/OR)
	 * @param active whether the rule is active
	 * @param serviceContext the service context
	 * @return the updated FormCounterRule
	 * @throws PortalException
	 */
	public FormCounterRule updateFormCounterRule(
			long formCounterRuleId, String ruleName, String description,
			String ruleConditions, String logicOperator, boolean active,
			ServiceContext serviceContext)
		throws PortalException;

}