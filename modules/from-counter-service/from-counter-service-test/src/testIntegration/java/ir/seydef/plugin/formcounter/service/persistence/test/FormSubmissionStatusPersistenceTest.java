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
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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

import ir.seydef.plugin.formcounter.exception.NoSuchFormSubmissionStatusException;
import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;
import ir.seydef.plugin.formcounter.service.persistence.FormSubmissionStatusPersistence;
import ir.seydef.plugin.formcounter.service.persistence.FormSubmissionStatusUtil;

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
public class FormSubmissionStatusPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "ir.seydef.plugin.formcounter.service"));

	@Before
	public void setUp() {
		_persistence = FormSubmissionStatusUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FormSubmissionStatus> iterator =
			_formSubmissionStatuses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FormSubmissionStatus formSubmissionStatus = _persistence.create(pk);

		Assert.assertNotNull(formSubmissionStatus);

		Assert.assertEquals(formSubmissionStatus.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FormSubmissionStatus newFormSubmissionStatus =
			addFormSubmissionStatus();

		_persistence.remove(newFormSubmissionStatus);

		FormSubmissionStatus existingFormSubmissionStatus =
			_persistence.fetchByPrimaryKey(
				newFormSubmissionStatus.getPrimaryKey());

		Assert.assertNull(existingFormSubmissionStatus);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFormSubmissionStatus();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FormSubmissionStatus newFormSubmissionStatus = _persistence.create(pk);

		newFormSubmissionStatus.setFormInstanceRecordId(
			RandomTestUtil.nextLong());

		newFormSubmissionStatus.setSeen(RandomTestUtil.randomBoolean());

		newFormSubmissionStatus.setSeenDate(RandomTestUtil.nextDate());

		newFormSubmissionStatus.setCompanyId(RandomTestUtil.nextLong());

		newFormSubmissionStatus.setGroupId(RandomTestUtil.nextLong());

		newFormSubmissionStatus.setUserId(RandomTestUtil.nextLong());

		newFormSubmissionStatus.setUserName(RandomTestUtil.randomString());

		newFormSubmissionStatus.setCreateDate(RandomTestUtil.nextDate());

		newFormSubmissionStatus.setModifiedDate(RandomTestUtil.nextDate());

		_formSubmissionStatuses.add(
			_persistence.update(newFormSubmissionStatus));

		FormSubmissionStatus existingFormSubmissionStatus =
			_persistence.findByPrimaryKey(
				newFormSubmissionStatus.getPrimaryKey());

		Assert.assertEquals(
			existingFormSubmissionStatus.getFormSubmissionStatusId(),
			newFormSubmissionStatus.getFormSubmissionStatusId());
		Assert.assertEquals(
			existingFormSubmissionStatus.getFormInstanceRecordId(),
			newFormSubmissionStatus.getFormInstanceRecordId());
		Assert.assertEquals(
			existingFormSubmissionStatus.isSeen(),
			newFormSubmissionStatus.isSeen());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFormSubmissionStatus.getSeenDate()),
			Time.getShortTimestamp(newFormSubmissionStatus.getSeenDate()));
		Assert.assertEquals(
			existingFormSubmissionStatus.getCompanyId(),
			newFormSubmissionStatus.getCompanyId());
		Assert.assertEquals(
			existingFormSubmissionStatus.getGroupId(),
			newFormSubmissionStatus.getGroupId());
		Assert.assertEquals(
			existingFormSubmissionStatus.getUserId(),
			newFormSubmissionStatus.getUserId());
		Assert.assertEquals(
			existingFormSubmissionStatus.getUserName(),
			newFormSubmissionStatus.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFormSubmissionStatus.getCreateDate()),
			Time.getShortTimestamp(newFormSubmissionStatus.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFormSubmissionStatus.getModifiedDate()),
			Time.getShortTimestamp(newFormSubmissionStatus.getModifiedDate()));
	}

	@Test
	public void testCountByFormInstanceRecordId() throws Exception {
		_persistence.countByFormInstanceRecordId(RandomTestUtil.nextLong());

		_persistence.countByFormInstanceRecordId(0L);
	}

	@Test
	public void testCountBySeen() throws Exception {
		_persistence.countBySeen(RandomTestUtil.randomBoolean());

		_persistence.countBySeen(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_S(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FormSubmissionStatus newFormSubmissionStatus =
			addFormSubmissionStatus();

		FormSubmissionStatus existingFormSubmissionStatus =
			_persistence.findByPrimaryKey(
				newFormSubmissionStatus.getPrimaryKey());

		Assert.assertEquals(
			existingFormSubmissionStatus, newFormSubmissionStatus);
	}

	@Test(expected = NoSuchFormSubmissionStatusException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FormSubmissionStatus> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FormSubmissionStatus", "formSubmissionStatusId", true,
			"formInstanceRecordId", true, "seen", true, "seenDate", true,
			"companyId", true, "groupId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FormSubmissionStatus newFormSubmissionStatus =
			addFormSubmissionStatus();

		FormSubmissionStatus existingFormSubmissionStatus =
			_persistence.fetchByPrimaryKey(
				newFormSubmissionStatus.getPrimaryKey());

		Assert.assertEquals(
			existingFormSubmissionStatus, newFormSubmissionStatus);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FormSubmissionStatus missingFormSubmissionStatus =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFormSubmissionStatus);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FormSubmissionStatus newFormSubmissionStatus1 =
			addFormSubmissionStatus();
		FormSubmissionStatus newFormSubmissionStatus2 =
			addFormSubmissionStatus();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFormSubmissionStatus1.getPrimaryKey());
		primaryKeys.add(newFormSubmissionStatus2.getPrimaryKey());

		Map<Serializable, FormSubmissionStatus> formSubmissionStatuses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, formSubmissionStatuses.size());
		Assert.assertEquals(
			newFormSubmissionStatus1,
			formSubmissionStatuses.get(
				newFormSubmissionStatus1.getPrimaryKey()));
		Assert.assertEquals(
			newFormSubmissionStatus2,
			formSubmissionStatuses.get(
				newFormSubmissionStatus2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FormSubmissionStatus> formSubmissionStatuses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(formSubmissionStatuses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FormSubmissionStatus newFormSubmissionStatus =
			addFormSubmissionStatus();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFormSubmissionStatus.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FormSubmissionStatus> formSubmissionStatuses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, formSubmissionStatuses.size());
		Assert.assertEquals(
			newFormSubmissionStatus,
			formSubmissionStatuses.get(
				newFormSubmissionStatus.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FormSubmissionStatus> formSubmissionStatuses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(formSubmissionStatuses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FormSubmissionStatus newFormSubmissionStatus =
			addFormSubmissionStatus();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFormSubmissionStatus.getPrimaryKey());

		Map<Serializable, FormSubmissionStatus> formSubmissionStatuses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, formSubmissionStatuses.size());
		Assert.assertEquals(
			newFormSubmissionStatus,
			formSubmissionStatuses.get(
				newFormSubmissionStatus.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FormSubmissionStatusLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<FormSubmissionStatus>() {

				@Override
				public void performAction(
					FormSubmissionStatus formSubmissionStatus) {

					Assert.assertNotNull(formSubmissionStatus);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FormSubmissionStatus newFormSubmissionStatus =
			addFormSubmissionStatus();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FormSubmissionStatus.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"formSubmissionStatusId",
				newFormSubmissionStatus.getFormSubmissionStatusId()));

		List<FormSubmissionStatus> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FormSubmissionStatus existingFormSubmissionStatus = result.get(0);

		Assert.assertEquals(
			existingFormSubmissionStatus, newFormSubmissionStatus);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FormSubmissionStatus.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"formSubmissionStatusId", RandomTestUtil.nextLong()));

		List<FormSubmissionStatus> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FormSubmissionStatus newFormSubmissionStatus =
			addFormSubmissionStatus();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FormSubmissionStatus.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("formSubmissionStatusId"));

		Object newFormSubmissionStatusId =
			newFormSubmissionStatus.getFormSubmissionStatusId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"formSubmissionStatusId",
				new Object[] {newFormSubmissionStatusId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFormSubmissionStatusId = result.get(0);

		Assert.assertEquals(
			existingFormSubmissionStatusId, newFormSubmissionStatusId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FormSubmissionStatus.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("formSubmissionStatusId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"formSubmissionStatusId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FormSubmissionStatus newFormSubmissionStatus =
			addFormSubmissionStatus();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newFormSubmissionStatus.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		FormSubmissionStatus newFormSubmissionStatus =
			addFormSubmissionStatus();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FormSubmissionStatus.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"formSubmissionStatusId",
				newFormSubmissionStatus.getFormSubmissionStatusId()));

		List<FormSubmissionStatus> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		FormSubmissionStatus formSubmissionStatus) {

		Assert.assertEquals(
			Long.valueOf(formSubmissionStatus.getFormInstanceRecordId()),
			ReflectionTestUtil.<Long>invoke(
				formSubmissionStatus, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "formInstanceRecordId"));
	}

	protected FormSubmissionStatus addFormSubmissionStatus() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FormSubmissionStatus formSubmissionStatus = _persistence.create(pk);

		formSubmissionStatus.setFormInstanceRecordId(RandomTestUtil.nextLong());

		formSubmissionStatus.setSeen(RandomTestUtil.randomBoolean());

		formSubmissionStatus.setSeenDate(RandomTestUtil.nextDate());

		formSubmissionStatus.setCompanyId(RandomTestUtil.nextLong());

		formSubmissionStatus.setGroupId(RandomTestUtil.nextLong());

		formSubmissionStatus.setUserId(RandomTestUtil.nextLong());

		formSubmissionStatus.setUserName(RandomTestUtil.randomString());

		formSubmissionStatus.setCreateDate(RandomTestUtil.nextDate());

		formSubmissionStatus.setModifiedDate(RandomTestUtil.nextDate());

		_formSubmissionStatuses.add(_persistence.update(formSubmissionStatus));

		return formSubmissionStatus;
	}

	private List<FormSubmissionStatus> _formSubmissionStatuses =
		new ArrayList<FormSubmissionStatus>();
	private FormSubmissionStatusPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}