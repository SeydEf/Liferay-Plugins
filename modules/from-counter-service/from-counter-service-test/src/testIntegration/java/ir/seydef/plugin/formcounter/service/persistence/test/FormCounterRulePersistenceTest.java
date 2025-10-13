/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import ir.seydef.plugin.formcounter.exception.NoSuchRuleException;
import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.service.FormCounterRuleLocalServiceUtil;
import ir.seydef.plugin.formcounter.service.persistence.FormCounterRulePersistence;
import ir.seydef.plugin.formcounter.service.persistence.FormCounterRuleUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class FormCounterRulePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "ir.seydef.plugin.formcounter.service"));

	@Before
	public void setUp() {
		_persistence = FormCounterRuleUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FormCounterRule> iterator = _formCounterRules.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FormCounterRule formCounterRule = _persistence.create(pk);

		Assert.assertNotNull(formCounterRule);

		Assert.assertEquals(formCounterRule.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FormCounterRule newFormCounterRule = addFormCounterRule();

		_persistence.remove(newFormCounterRule);

		FormCounterRule existingFormCounterRule =
			_persistence.fetchByPrimaryKey(newFormCounterRule.getPrimaryKey());

		Assert.assertNull(existingFormCounterRule);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFormCounterRule();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FormCounterRule newFormCounterRule = _persistence.create(pk);

		newFormCounterRule.setRuleName(RandomTestUtil.randomString());

		newFormCounterRule.setDescription(RandomTestUtil.randomString());

		newFormCounterRule.setRuleConditions(RandomTestUtil.randomString());

		newFormCounterRule.setLogicOperator(RandomTestUtil.randomString());

		newFormCounterRule.setActive(RandomTestUtil.randomBoolean());

		newFormCounterRule.setCompanyId(RandomTestUtil.nextLong());

		newFormCounterRule.setGroupId(RandomTestUtil.nextLong());

		newFormCounterRule.setUserId(RandomTestUtil.nextLong());

		newFormCounterRule.setUserName(RandomTestUtil.randomString());

		newFormCounterRule.setCreateDate(RandomTestUtil.nextDate());

		newFormCounterRule.setModifiedDate(RandomTestUtil.nextDate());

		_formCounterRules.add(_persistence.update(newFormCounterRule));

		FormCounterRule existingFormCounterRule = _persistence.findByPrimaryKey(
			newFormCounterRule.getPrimaryKey());

		Assert.assertEquals(
			existingFormCounterRule.getFormCounterRuleId(),
			newFormCounterRule.getFormCounterRuleId());
		Assert.assertEquals(
			existingFormCounterRule.getRuleName(),
			newFormCounterRule.getRuleName());
		Assert.assertEquals(
			existingFormCounterRule.getDescription(),
			newFormCounterRule.getDescription());
		Assert.assertEquals(
			existingFormCounterRule.getRuleConditions(),
			newFormCounterRule.getRuleConditions());
		Assert.assertEquals(
			existingFormCounterRule.getLogicOperator(),
			newFormCounterRule.getLogicOperator());
		Assert.assertEquals(
			existingFormCounterRule.isActive(), newFormCounterRule.isActive());
		Assert.assertEquals(
			existingFormCounterRule.getCompanyId(),
			newFormCounterRule.getCompanyId());
		Assert.assertEquals(
			existingFormCounterRule.getGroupId(),
			newFormCounterRule.getGroupId());
		Assert.assertEquals(
			existingFormCounterRule.getUserId(),
			newFormCounterRule.getUserId());
		Assert.assertEquals(
			existingFormCounterRule.getUserName(),
			newFormCounterRule.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFormCounterRule.getCreateDate()),
			Time.getShortTimestamp(newFormCounterRule.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingFormCounterRule.getModifiedDate()),
			Time.getShortTimestamp(newFormCounterRule.getModifiedDate()));
	}

	@Test
	public void testCountByActive() throws Exception {
		_persistence.countByActive(RandomTestUtil.randomBoolean());

		_persistence.countByActive(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FormCounterRule newFormCounterRule = addFormCounterRule();

		FormCounterRule existingFormCounterRule = _persistence.findByPrimaryKey(
			newFormCounterRule.getPrimaryKey());

		Assert.assertEquals(existingFormCounterRule, newFormCounterRule);
	}

	@Test(expected = NoSuchRuleException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FormCounterRule> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FormCounterRule", "formCounterRuleId", true, "ruleName", true,
			"description", true, "ruleConditions", true, "logicOperator", true,
			"active", true, "companyId", true, "groupId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FormCounterRule newFormCounterRule = addFormCounterRule();

		FormCounterRule existingFormCounterRule =
			_persistence.fetchByPrimaryKey(newFormCounterRule.getPrimaryKey());

		Assert.assertEquals(existingFormCounterRule, newFormCounterRule);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FormCounterRule missingFormCounterRule = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingFormCounterRule);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FormCounterRule newFormCounterRule1 = addFormCounterRule();
		FormCounterRule newFormCounterRule2 = addFormCounterRule();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFormCounterRule1.getPrimaryKey());
		primaryKeys.add(newFormCounterRule2.getPrimaryKey());

		Map<Serializable, FormCounterRule> formCounterRules =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, formCounterRules.size());
		Assert.assertEquals(
			newFormCounterRule1,
			formCounterRules.get(newFormCounterRule1.getPrimaryKey()));
		Assert.assertEquals(
			newFormCounterRule2,
			formCounterRules.get(newFormCounterRule2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FormCounterRule> formCounterRules =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(formCounterRules.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FormCounterRule newFormCounterRule = addFormCounterRule();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFormCounterRule.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FormCounterRule> formCounterRules =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, formCounterRules.size());
		Assert.assertEquals(
			newFormCounterRule,
			formCounterRules.get(newFormCounterRule.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FormCounterRule> formCounterRules =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(formCounterRules.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FormCounterRule newFormCounterRule = addFormCounterRule();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFormCounterRule.getPrimaryKey());

		Map<Serializable, FormCounterRule> formCounterRules =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, formCounterRules.size());
		Assert.assertEquals(
			newFormCounterRule,
			formCounterRules.get(newFormCounterRule.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FormCounterRuleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<FormCounterRule>() {

				@Override
				public void performAction(FormCounterRule formCounterRule) {
					Assert.assertNotNull(formCounterRule);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FormCounterRule newFormCounterRule = addFormCounterRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FormCounterRule.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"formCounterRuleId",
				newFormCounterRule.getFormCounterRuleId()));

		List<FormCounterRule> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FormCounterRule existingFormCounterRule = result.get(0);

		Assert.assertEquals(existingFormCounterRule, newFormCounterRule);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FormCounterRule.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"formCounterRuleId", RandomTestUtil.nextLong()));

		List<FormCounterRule> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FormCounterRule newFormCounterRule = addFormCounterRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FormCounterRule.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("formCounterRuleId"));

		Object newFormCounterRuleId = newFormCounterRule.getFormCounterRuleId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"formCounterRuleId", new Object[] {newFormCounterRuleId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFormCounterRuleId = result.get(0);

		Assert.assertEquals(existingFormCounterRuleId, newFormCounterRuleId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FormCounterRule.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("formCounterRuleId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"formCounterRuleId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected FormCounterRule addFormCounterRule() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FormCounterRule formCounterRule = _persistence.create(pk);

		formCounterRule.setRuleName(RandomTestUtil.randomString());

		formCounterRule.setDescription(RandomTestUtil.randomString());

		formCounterRule.setRuleConditions(RandomTestUtil.randomString());

		formCounterRule.setLogicOperator(RandomTestUtil.randomString());

		formCounterRule.setActive(RandomTestUtil.randomBoolean());

		formCounterRule.setCompanyId(RandomTestUtil.nextLong());

		formCounterRule.setGroupId(RandomTestUtil.nextLong());

		formCounterRule.setUserId(RandomTestUtil.nextLong());

		formCounterRule.setUserName(RandomTestUtil.randomString());

		formCounterRule.setCreateDate(RandomTestUtil.nextDate());

		formCounterRule.setModifiedDate(RandomTestUtil.nextDate());

		_formCounterRules.add(_persistence.update(formCounterRule));

		return formCounterRule;
	}

	private List<FormCounterRule> _formCounterRules =
		new ArrayList<FormCounterRule>();
	private FormCounterRulePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}