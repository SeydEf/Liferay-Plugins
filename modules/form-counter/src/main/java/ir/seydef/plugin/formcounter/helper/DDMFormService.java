package ir.seydef.plugin.formcounter.helper;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.model.SearchCriteria;
import ir.seydef.plugin.formcounter.service.FormCounterRuleLocalServiceUtil;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;
import ir.seydef.plugin.formcounter.util.DDMStructureFieldUtil;
import ir.seydef.plugin.formcounter.util.PersianTextUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author S.Abolfazl Eftekhari
 */
public class DDMFormService {

	public static String extractSubmitterNameFromRecord(
		DDMFormInstanceRecord record) {

		try {
			if (record != null) {
				DDMFormValues formValues = record.getDDMFormValues();

				if (formValues != null) {
					List<DDMFormFieldValue> formFieldValues =
						formValues.getDDMFormFieldValues();

					Map<String, String> nameFields =
						_extractNameFieldsRecursively(formFieldValues);

					String firstName = nameFields.getOrDefault("firstName", "");
					String lastName = nameFields.getOrDefault("lastName", "");

					String fullName = firstName + " " + lastName;

					fullName = fullName.trim();

					if (Validator.isNotNull(fullName)) {
						return fullName;
					}

					return "";
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error extracting submitter name from record", exception);
		}

		return "";
	}

	public static List<DDMFormInstanceRecord> getFilteredFormRecords(
		List<Long> formInstanceIds, Map<String, List<String>> userCustomFields,
		int start, int end, String orderByCol, String orderByType,
		long groupId) {

		if ((userCustomFields == null) || userCustomFields.isEmpty()) {
			return new ArrayList<>();
		}

		if ((formInstanceIds == null) || ListUtil.isEmpty(formInstanceIds)) {
			return new ArrayList<>();
		}

		try {
			List<Long> approvedRecordIds = _getApprovedRecordIds();

			if (approvedRecordIds.isEmpty()) {
				return new ArrayList<>();
			}

			DynamicQuery dynamicQuery =
				DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

			dynamicQuery.add(
				RestrictionsFactoryUtil.in(
					"formInstanceRecordId", approvedRecordIds));

			dynamicQuery.add(
				RestrictionsFactoryUtil.in("formInstanceId", formInstanceIds));

			dynamicQuery.add(
				PropertyFactoryUtil.forName(
					"groupId"
				).eq(
					groupId
				));

			if (Validator.isNotNull(orderByCol)) {
				if ("desc".equalsIgnoreCase(orderByType)) {
					dynamicQuery.addOrder(OrderFactoryUtil.desc(orderByCol));
				}
				else {
					dynamicQuery.addOrder(OrderFactoryUtil.asc(orderByCol));
				}
			}

			List<DDMFormInstanceRecord> allRecords =
				DDMFormInstanceRecordLocalServiceUtil.dynamicQuery(
					dynamicQuery);

			List<DDMFormInstanceRecord> filteredRecords = new ArrayList<>();

			for (DDMFormInstanceRecord record : allRecords) {
				try {
					if (_matchesUserCustomFields(record, userCustomFields)) {
						filteredRecords.add(record);
					}
				}
				catch (Exception exception) {
					_log.warn(
						"Error filtering record: " +
							record.getFormInstanceRecordId(),
						exception);
				}
			}

			int fromIndex = Math.min(start, filteredRecords.size());
			int toIndex = Math.min(end, filteredRecords.size());

			return filteredRecords.subList(fromIndex, toIndex);
		}
		catch (Exception exception) {
			_log.error("Error retrieving filtered form records", exception);

			return new ArrayList<>();
		}
	}

	public static int getFilteredFormRecordsCount(
		long formInstanceId, Map<String, List<String>> userCustomFields,
		long groupId) {

		if ((userCustomFields == null) || userCustomFields.isEmpty()) {
			return 0;
		}

		try {
			List<Long> approvedRecordIds = _getApprovedRecordIds();

			if (approvedRecordIds.isEmpty()) {
				return 0;
			}

			DynamicQuery dynamicQuery =
				DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

			dynamicQuery.add(
				RestrictionsFactoryUtil.in(
					"formInstanceRecordId", approvedRecordIds));

			if (formInstanceId > 0) {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"formInstanceId", formInstanceId));
			}

			dynamicQuery.add(
				PropertyFactoryUtil.forName(
					"groupId"
				).eq(
					groupId
				));

			List<DDMFormInstanceRecord> allRecords =
				DDMFormInstanceRecordLocalServiceUtil.dynamicQuery(
					dynamicQuery);

			int count = 0;

			for (DDMFormInstanceRecord record : allRecords) {
				try {
					if (_matchesUserCustomFields(record, userCustomFields)) {
						count++;
					}
				}
				catch (Exception exception) {
					_log.warn(
						"Error checking record match: " +
							record.getFormInstanceRecordId(),
						exception);
				}
			}

			return count;
		}
		catch (Exception exception) {
			_log.error("Error counting filtered form records", exception);

			return 0;
		}
	}

	public static DDMFormInstance getFormInstance(long formInstanceId) {
		try {
			return DDMFormInstanceLocalServiceUtil.fetchDDMFormInstance(
				formInstanceId);
		}
		catch (Exception exception) {
			_log.error(
				"Error retrieving form instance: " + formInstanceId, exception);

			return null;
		}
	}

	public static List<DDMFormInstance> getFormInstancesForUser(
		Map<String, List<String>> userCustomFields, long groupId) {

		List<DDMFormInstance> matchingFormInstances = new ArrayList<>();

		try {
			if ((userCustomFields == null) || userCustomFields.isEmpty()) {
				return matchingFormInstances;
			}

			List<FormCounterRule> activeRules =
				FormCounterRuleLocalServiceUtil.findByActive(true);

			if (activeRules.isEmpty()) {
				return matchingFormInstances;
			}

			Map<String, Set<String>> referenceFieldsMap =
				RuleFilterHelper.getReferenceFieldsForCustomFields(
					activeRules, userCustomFields.keySet());

			if (referenceFieldsMap.isEmpty()) {
				return matchingFormInstances;
			}

			Set<String> allReferenceFields = new HashSet<>();

			for (Set<String> refs : referenceFieldsMap.values()) {
				allReferenceFields.addAll(refs);
			}

			List<DDMFormInstance> allFormInstances =
				DDMFormInstanceLocalServiceUtil.getFormInstances(groupId);

			for (DDMFormInstance formInstance : allFormInstances) {
				try {
					DDMStructure structure = formInstance.getStructure();

					if ((structure != null) &&
						_hasAnyReferenceField(structure, allReferenceFields)) {

						matchingFormInstances.add(formInstance);
					}
				}
				catch (Exception exception) {
					_log.warn(
						"Error checking form structure for form instance: " +
							formInstance.getFormInstanceId(),
						exception);
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error retrieving form instances for user custom fields",
				exception);
		}

		return matchingFormInstances;
	}

	public static boolean recordHasRuleReferenceFields(
		DDMFormInstanceRecord record) {

		try {
			List<FormCounterRule> activeRules =
				FormCounterRuleLocalServiceUtil.findByActive(true);

			if (activeRules.isEmpty()) {
				return false;
			}

			Set<String> allReferenceFields = new HashSet<>();

			for (FormCounterRule rule : activeRules) {
				try {
					JSONObject jsonConditions =
						JSONFactoryUtil.createJSONObject(
							rule.getRuleConditions());

					JSONArray conditions = jsonConditions.getJSONArray(
						"conditions");

					for (int i = 0; i < conditions.length(); i++) {
						JSONObject condition = conditions.getJSONObject(i);

						String referenceField = condition.getString(
							"reference");

						if (Validator.isNotNull(referenceField)) {
							allReferenceFields.add(
								referenceField.toLowerCase(
								).trim());
						}
					}
				}
				catch (Exception exception) {
					_log.warn(
						"Error parsing rule conditions: " +
							rule.getFormCounterRuleId(),
						exception);
				}
			}

			if (allReferenceFields.isEmpty()) {
				return false;
			}

			DDMFormValues formValues = record.getDDMFormValues();

			if (formValues == null) {
				return false;
			}

			List<DDMFormFieldValue> formFieldValues =
				formValues.getDDMFormFieldValues();

			if ((formFieldValues == null) ||
				ListUtil.isEmpty(formFieldValues)) {

				return false;
			}

			return _hasAnyReferenceFieldInValues(
				formFieldValues, allReferenceFields);
		}
		catch (Exception exception) {
			_log.error(
				"Error checking if record has rule reference fields",
				exception);

			return false;
		}
	}

	public static List<DDMFormInstanceRecord> searchFormRecords(
		SearchCriteria searchCriteria, List<Long> formInstanceIds, int start,
		int end, String orderByCol, String orderByType, long groupId) {

		Map<String, List<String>> userCustomFields =
			searchCriteria.getUserCustomFields();

		if ((userCustomFields == null) || userCustomFields.isEmpty()) {
			return new ArrayList<>();
		}

		if ((formInstanceIds == null) || ListUtil.isEmpty(formInstanceIds)) {
			return new ArrayList<>();
		}

		try {
			List<Long> approvedRecordIds = _getApprovedRecordIds();

			if (approvedRecordIds.isEmpty()) {
				return new ArrayList<>();
			}

			DynamicQuery dynamicQuery =
				DDMFormInstanceRecordLocalServiceUtil.dynamicQuery();

			dynamicQuery.add(
				RestrictionsFactoryUtil.in(
					"formInstanceRecordId", approvedRecordIds));

			if (searchCriteria.getFormInstanceId() > 0) {
				dynamicQuery.add(
					PropertyFactoryUtil.forName(
						"formInstanceId"
					).eq(
						searchCriteria.getFormInstanceId()
					));
			}
			else {
				dynamicQuery.add(
					RestrictionsFactoryUtil.in(
						"formInstanceId", formInstanceIds));
			}

			dynamicQuery.add(
				PropertyFactoryUtil.forName(
					"groupId"
				).eq(
					groupId
				));

			if (searchCriteria.getStartDate() != null) {
				dynamicQuery.add(
					PropertyFactoryUtil.forName(
						"createDate"
					).ge(
						searchCriteria.getStartDate()
					));
			}

			if (searchCriteria.getEndDate() != null) {
				long time = 24 * 60 * 60 * 1000;

				long searchTime = searchCriteria.getEndDate(
				).getTime();

				Date endDate = new Date(searchTime + time);

				dynamicQuery.add(
					PropertyFactoryUtil.forName(
						"createDate"
					).lt(
						endDate
					));
			}

			if (Validator.isNotNull(orderByCol)) {
				if ("desc".equalsIgnoreCase(orderByType)) {
					dynamicQuery.addOrder(OrderFactoryUtil.desc(orderByCol));
				}
				else {
					dynamicQuery.addOrder(OrderFactoryUtil.asc(orderByCol));
				}
			}

			List<DDMFormInstanceRecord> allRecords =
				DDMFormInstanceRecordLocalServiceUtil.dynamicQuery(
					dynamicQuery);

			List<DDMFormInstanceRecord> filteredRecords = new ArrayList<>();

			for (DDMFormInstanceRecord record : allRecords) {
				try {
					if (!_matchesUserCustomFields(record, userCustomFields)) {
						continue;
					}

					if (!_matchesSeenStatus(
							record, searchCriteria.getStatus())) {

						continue;
					}

					if (!_matchesDynamicFilters(
							record, searchCriteria.getDynamicFilters())) {

						continue;
					}

					if (_matchesTextCriteria(record, searchCriteria)) {
						filteredRecords.add(record);
					}
				}
				catch (Exception exception) {
					_log.warn(
						"Error filtering record: " +
							record.getFormInstanceRecordId(),
						exception);
				}
			}

			int fromIndex = Math.min(start, filteredRecords.size());
			int toIndex = Math.min(end, filteredRecords.size());

			return filteredRecords.subList(fromIndex, toIndex);
		}
		catch (Exception exception) {
			return new ArrayList<>();
		}
	}

	private static boolean _checkFieldMatch(
		List<DDMFormFieldValue> fieldValues, String fieldName,
		String searchValue) {

		if (Validator.isNull(searchValue)) {
			return true; // No search criteria for this field
		}

		return _checkFieldMatchRecursively(fieldValues, fieldName, searchValue);
	}

	private static boolean _checkFieldMatchRecursively(
		List<DDMFormFieldValue> fieldValues, String fieldName,
		String searchValue) {

		if ((fieldValues == null) || ListUtil.isEmpty(fieldValues)) {
			return false;
		}

		for (DDMFormFieldValue fieldValue : fieldValues) {
			try {
				Value value = fieldValue.getValue();

				if ((fieldName.equals(fieldValue.getFieldReference()) ||
					 fieldName.equals(fieldValue.getName())) &&
					(value != null) && (value.getDefaultLocale() != null)) {

					String fieldValueString = GetterUtil.getString(
						value.getString(value.getDefaultLocale()));

					if (PersianTextUtil.contains(
							fieldValueString, searchValue)) {

						return true;
					}
				}

				List<DDMFormFieldValue> nestedFieldValues =
					fieldValue.getNestedDDMFormFieldValues();

				if ((nestedFieldValues != null) &&
					!nestedFieldValues.isEmpty() &&
					_checkFieldMatchRecursively(
						nestedFieldValues, fieldName, searchValue)) {

					return true;
				}
			}
			catch (Exception exception) {
				_log.warn(
					"Error checking field match for field: " + fieldName,
					exception);
			}
		}

		return false;
	}

	private static String _extractDisplayValueFromStructure(
		DDMFormInstanceRecord record, DDMFormFieldValue fieldValue,
		String optionValue, String referenceField) {

		try {
			DDMFormInstance formInstance =
				DDMFormInstanceLocalServiceUtil.fetchDDMFormInstance(
					record.getFormInstanceId());

			if (formInstance != null) {
				DDMStructure structure = formInstance.getStructure();

				if (structure != null) {
					String definition = structure.getDefinition(
					).toLowerCase();

					if (definition.contains(referenceField.toLowerCase())) {
						return _parseOptionValueFromDefinition(
							definition, fieldValue.getFieldReference(),
							optionValue);
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error extracting display value from structure for option: " +
					optionValue,
				exception);
		}

		return optionValue;
	}

	private static Map<String, String> _extractNameFieldsRecursively(
		List<DDMFormFieldValue> formFieldValues) {

		Map<String, String> nameFields = new HashMap<>();

		nameFields.put("firstName", "");
		nameFields.put("lastName", "");

		if ((formFieldValues == null) || ListUtil.isEmpty(formFieldValues)) {
			return nameFields;
		}

		for (DDMFormFieldValue fieldValue : formFieldValues) {
			try {
				Value fieldValueValue = fieldValue.getValue();

				if ((fieldValueValue != null) &&
					(fieldValueValue.getDefaultLocale() != null)) {

					String value = GetterUtil.getString(
						fieldValueValue.getString(
							fieldValueValue.getDefaultLocale()));

					if (Validator.isNotNull(value)) {
						String fieldRef;

						if (fieldValue.getFieldReference() != null) {
							fieldRef = fieldValue.getFieldReference(
							).toLowerCase();
						}
						else {
							fieldRef = "";
						}

						if (fieldRef.contains("fullname") ||
							fieldRef.equals("full_name") ||
							fieldRef.equals("name") ||
							fieldRef.equals("personname") ||
							fieldRef.equals("person_name")) {

							String[] nameParts = value.trim(
							).split(
								"\\s+", 2
							);

							if ((nameParts.length >= 1) &&
								Validator.isNull(nameFields.get("firstName"))) {

								nameFields.put("firstName", nameParts[0]);
							}

							if ((nameParts.length >= 2) &&
								Validator.isNull(nameFields.get("lastName"))) {

								nameFields.put("lastName", nameParts[1]);
							}
						}
						else if (fieldRef.contains("first") ||
								 fieldRef.equals("firstname") ||
								 fieldRef.equals("fname") ||
								 fieldRef.equals("first_name")) {

							if (Validator.isNull(nameFields.get("firstName"))) {
								nameFields.put("firstName", value);
							}
						}
						else if (fieldRef.contains("last") ||
								 fieldRef.equals("lastname") ||
								 fieldRef.equals("lname") ||
								 fieldRef.equals("surname") ||
								 fieldRef.equals("last_name")) {

							if (Validator.isNull(nameFields.get("lastName"))) {
								nameFields.put("lastName", value);
							}
						}
					}
				}

				List<DDMFormFieldValue> nestedFieldValues =
					fieldValue.getNestedDDMFormFieldValues();

				if ((nestedFieldValues != null) &&
					!nestedFieldValues.isEmpty()) {

					Map<String, String> nestedNameFields =
						_extractNameFieldsRecursively(nestedFieldValues);

					if (Validator.isNull(nameFields.get("firstName")) &&
						Validator.isNotNull(
							nestedNameFields.get("firstName"))) {

						nameFields.put(
							"firstName", nestedNameFields.get("firstName"));
					}

					if (Validator.isNull(nameFields.get("lastName")) &&
						Validator.isNotNull(nestedNameFields.get("lastName"))) {

						nameFields.put(
							"lastName", nestedNameFields.get("lastName"));
					}
				}

				if (Validator.isNotNull(nameFields.get("firstName")) &&
					Validator.isNotNull(nameFields.get("lastName"))) {

					break;
				}
			}
			catch (Exception exception) {
				_log.warn("Error extracting name field value", exception);
			}
		}

		return nameFields;
	}

	private static String _findOptionLabel(
		JSONArray options, String optionValue) {

		try {
			for (int i = 0; i < options.length(); i++) {
				JSONObject option = options.getJSONObject(i);

				String value = option.getString("value");

				optionValue = optionValue.toLowerCase();

				if (optionValue.contains(value)) {
					JSONObject label = option.getJSONObject("label");

					if (label != null) {
						String labelText = null;

						Iterator<String> keys = label.keys();

						if (keys.hasNext()) {
							String firstKey = keys.next();

							labelText = label.getString(firstKey);
						}

						if (Validator.isNotNull(labelText)) {
							_log.debug(
								"Found option label '" + labelText +
									"' for value: " + optionValue);

							return labelText;
						}
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error finding option label for value: " + optionValue,
				exception);
		}

		_log.debug(
			"No label found for option value: " + optionValue +
				", returning original value");

		return optionValue;
	}

	private static List<Long> _getApprovedRecordIds() {
		DynamicQuery versionQuery =
			DDMFormInstanceRecordVersionLocalServiceUtil.dynamicQuery();

		versionQuery.add(
			PropertyFactoryUtil.forName(
				"status"
			).eq(
				WorkflowConstants.STATUS_APPROVED
			));
		versionQuery.setProjection(
			ProjectionFactoryUtil.distinct(
				ProjectionFactoryUtil.property("formInstanceRecordId")));

		return DDMFormInstanceRecordVersionLocalServiceUtil.dynamicQuery(
			versionQuery);
	}

	private static boolean _hasAnyReferenceField(
		DDMStructure structure, Set<String> referenceFields) {

		try {
			String definition = structure.getDefinition();

			if (definition != null) {
				JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(
					definition);

				JSONArray fields = jsonDefinition.getJSONArray("fields");

				if (fields != null) {
					return _hasAnyReferenceFieldRecursively(
						fields, referenceFields);
				}
			}
		}
		catch (Exception exception) {
			_log.error("Error checking form reference fields", exception);
		}

		return false;
	}

	private static boolean _hasAnyReferenceFieldInValues(
		List<DDMFormFieldValue> fieldValues, Set<String> referenceFields) {

		for (DDMFormFieldValue fieldValue : fieldValues) {
			String fieldRef = fieldValue.getFieldReference(
			).toLowerCase(
			).trim();

			if (referenceFields.contains(fieldRef)) {
				return true;
			}

			List<DDMFormFieldValue> nestedFieldValues =
				fieldValue.getNestedDDMFormFieldValues();

			if ((nestedFieldValues != null) && !nestedFieldValues.isEmpty() &&
				_hasAnyReferenceFieldInValues(
					nestedFieldValues, referenceFields)) {

				return true;
			}
		}

		return false;
	}

	private static boolean _hasAnyReferenceFieldRecursively(
		JSONArray fields, Set<String> referenceFields) {

		if ((fields == null) || (referenceFields == null) ||
			referenceFields.isEmpty()) {

			return false;
		}

		for (int i = 0; i < fields.length(); i++) {
			try {
				JSONObject field = fields.getJSONObject(i);

				String fieldReference = field.getString("fieldReference");

				for (String refField : referenceFields) {
					if (refField.equalsIgnoreCase(fieldReference)) {
						return true;
					}
				}

				if (field.has("nestedFields")) {
					JSONArray nestedFields = field.getJSONArray("nestedFields");

					if ((nestedFields != null) && (nestedFields.length() > 0) &&
						_hasAnyReferenceFieldRecursively(
							nestedFields, referenceFields)) {

						return true;
					}
				}
			}
			catch (Exception exception) {
				_log.warn(
					"Error processing field in reference field search",
					exception);
			}
		}

		return false;
	}

	private static boolean _hasMatchingFieldValue(
		DDMFormInstanceRecord record, List<DDMFormFieldValue> fieldValues,
		String referenceField, String customFieldValue, String operator) {

		if ((fieldValues == null) || ListUtil.isEmpty(fieldValues)) {
			return false;
		}

		for (DDMFormFieldValue fieldValue : fieldValues) {
			try {
				String fieldRef = fieldValue.getFieldReference();

				if (referenceField.equalsIgnoreCase(fieldRef)) {
					Value value = fieldValue.getValue();

					if ((value != null) && (value.getDefaultLocale() != null)) {
						String recordFieldValue = GetterUtil.getString(
							value.getString(value.getDefaultLocale()));

						recordFieldValue = _extractDisplayValueFromStructure(
							record, fieldValue, recordFieldValue,
							referenceField);

						if (Validator.isNotNull(recordFieldValue) &&
							_matchesCondition(
								recordFieldValue, customFieldValue, operator)) {

							return true;
						}
					}
				}

				List<DDMFormFieldValue> nestedFieldValues =
					fieldValue.getNestedDDMFormFieldValues();

				if ((nestedFieldValues != null) &&
					!nestedFieldValues.isEmpty() &&
					_hasMatchingFieldValue(
						record, nestedFieldValues, referenceField,
						customFieldValue, operator)) {

					return true;
				}
			}
			catch (Exception exception) {
				_log.warn("Error checking field value match", exception);
			}
		}

		return false;
	}

	private static boolean _matchesCondition(
		String recordValue, String customFieldValue, String operator) {

		if (Validator.isNull(recordValue) ||
			Validator.isNull(customFieldValue)) {

			return false;
		}

		String recordValueLower = recordValue.toLowerCase(
		).trim();

		String customFieldValueLower = customFieldValue.toLowerCase(
		).trim();

		switch (operator.toLowerCase()) {
			case "contains":
				return recordValueLower.contains(customFieldValueLower);
			case "equal":
				return recordValueLower.equals(customFieldValueLower);
			case "not-equal":
				return !recordValueLower.equals(customFieldValueLower);
			default:
				_log.warn(
					"Unknown operator: " + operator +
						", defaulting to 'contains'");

				return recordValueLower.contains(customFieldValueLower);
		}
	}

	private static boolean _matchesDynamicFilters(
		DDMFormInstanceRecord record,
		List<SearchCriteria.DynamicFilter> dynamicFilters) {

		if ((dynamicFilters == null) || ListUtil.isEmpty(dynamicFilters)) {
			return true;
		}

		try {
			DDMFormValues formValues = record.getDDMFormValues();

			if (formValues == null) {
				return false;
			}

			for (SearchCriteria.DynamicFilter filter : dynamicFilters) {
				boolean matches = DDMStructureFieldUtil.matchesFilter(
					formValues, filter.getFieldName(), filter.getFieldValue());

				if (!matches) {
					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			_log.warn(
				"Error matching dynamic filters for record: " +
					record.getFormInstanceRecordId(),
				exception);

			return false;
		}
	}

	private static boolean _matchesSeenStatus(
		DDMFormInstanceRecord record, String statusCriteria) {

		if (Validator.isNull(statusCriteria) || statusCriteria.equals("all")) {
			return true;
		}

		try {
			FormSubmissionStatus submissionStatus =
				FormSubmissionStatusLocalServiceUtil.getByFormInstanceRecordId(
					record.getFormInstanceRecordId());

			if (submissionStatus == null) {
				return statusCriteria.equals("unseen");
			}

			boolean seen = submissionStatus.isSeen();

			if (statusCriteria.equals("seen")) {
				return seen;
			}
			else if (statusCriteria.equals("unseen")) {
				return !seen;
			}
		}
		catch (Exception exception) {
			_log.warn(
				"Error checking seen status for record: " +
					record.getFormInstanceRecordId(),
				exception);

			return statusCriteria.equals("unseen");
		}

		return true;
	}

	private static boolean _matchesTextCriteria(
		DDMFormInstanceRecord record, SearchCriteria searchCriteria) {

		try {
			DDMFormValues formValues = record.getDDMFormValues();

			if (formValues == null) {
				return !searchCriteria.hasSearchCriteria();
			}

			DDMFormInstance formInstance = getFormInstance(
				record.getFormInstanceId());

			if (Validator.isNotNull(searchCriteria.getFormName()) &&
				(formInstance != null)) {

				String formName = formInstance.getName(
					formValues.getDefaultLocale());

				if (!PersianTextUtil.contains(
						formName, searchCriteria.getFormName())) {

					return false;
				}
			}

			List<DDMFormFieldValue> fieldValues =
				formValues.getDDMFormFieldValues();

			boolean registrantNameMatch = true;

			if (Validator.isNotNull(searchCriteria.getRegistrantName())) {
				String submitterName = extractSubmitterNameFromRecord(record);

				registrantNameMatch =
					Validator.isNotNull(submitterName) &&
					PersianTextUtil.contains(
						submitterName, searchCriteria.getRegistrantName());
			}

			boolean formNumberMatch = true;

			if (Validator.isNotNull(searchCriteria.getFormNumber())) {
				formNumberMatch =
					(record.getFormInstanceRecordId() == GetterUtil.getLong(
						searchCriteria.getFormNumber())) ||
					PersianTextUtil.contains(
						String.valueOf(record.getFormInstanceRecordId()),
						searchCriteria.getFormNumber());
			}

			boolean trackingCodeMatch = _checkFieldMatch(
				fieldValues, "trackingCode", searchCriteria.getTrackingCode());

			if (registrantNameMatch && formNumberMatch && trackingCodeMatch) {
				return true;
			}

			return false;
		}
		catch (Exception exception) {
			_log.warn(
				"Error matching text criteria for record: " +
					record.getFormInstanceRecordId(),
				exception);

			return false;
		}
	}

	private static boolean _matchesUserCustomFields(
		DDMFormInstanceRecord record,
		Map<String, List<String>> userCustomFields) {

		try {
			List<FormCounterRule> activeRules =
				FormCounterRuleLocalServiceUtil.findByActive(true);

			if (activeRules.isEmpty()) {
				return false;
			}

			DDMFormValues formValues = record.getDDMFormValues();

			if (formValues == null) {
				return false;
			}

			List<DDMFormFieldValue> formFieldValues =
				formValues.getDDMFormFieldValues();

			if ((formFieldValues == null) ||
				ListUtil.isEmpty(formFieldValues)) {

				return false;
			}

			for (FormCounterRule rule : activeRules) {
				if (_recordMatchesCompleteRule(
						record, formFieldValues, rule, userCustomFields)) {

					return true;
				}
			}

			return false;
		}
		catch (Exception exception) {
			_log.error(
				"Error checking if record matches user custom fields: " +
					record.getFormInstanceRecordId(),
				exception);

			return false;
		}
	}

	private static String _parseOptionValueFromDefinition(
		String definition, String fieldReference, String optionValue) {

		try {
			JSONObject jsonDefinition = JSONFactoryUtil.createJSONObject(
				definition);

			JSONArray fields = jsonDefinition.getJSONArray("fields");

			if (fields != null) {
				String result = _parseOptionValueRecursively(
					fields, fieldReference.toLowerCase(), optionValue);

				if ((result != null) && !result.equals(optionValue)) {
					return result;
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error parsing option value from definition using JSON: " +
					optionValue,
				exception);
		}

		return optionValue;
	}

	private static String _parseOptionValueRecursively(
		JSONArray fields, String fieldReference, String optionValue) {

		if (fields == null) {
			return null;
		}

		for (int i = 0; i < fields.length(); i++) {
			try {
				JSONObject field = fields.getJSONObject(i);

				String currentFieldReference = field.getString(
					"fieldreference");

				if (fieldReference.contains(currentFieldReference)) {
					JSONArray options = field.getJSONArray("options");

					if (options != null) {
						String label = _findOptionLabel(options, optionValue);

						if ((label != null) && !label.equals(optionValue)) {
							return label;
						}
					}
				}

				if (field.has("nestedfields")) {
					JSONArray nestedFields = field.getJSONArray("nestedfields");

					if ((nestedFields != null) && (nestedFields.length() > 0)) {
						String result = _parseOptionValueRecursively(
							nestedFields, fieldReference, optionValue);

						if ((result != null) && !result.equals(optionValue)) {
							return result;
						}
					}
				}
			}
			catch (Exception exception) {
				_log.warn(
					"Error processing field in option value parsing",
					exception);
			}
		}

		return null;
	}

	private static boolean _recordMatchesCompleteRule(
		DDMFormInstanceRecord record, List<DDMFormFieldValue> formFieldValues,
		FormCounterRule rule, Map<String, List<String>> userCustomFields) {

		try {
			String ruleConditions = rule.getRuleConditions();

			if (Validator.isNull(ruleConditions)) {
				return false;
			}

			JSONObject jsonConditions = JSONFactoryUtil.createJSONObject(
				ruleConditions);

			JSONArray conditions = jsonConditions.getJSONArray("conditions");

			if ((conditions == null) || (conditions.length() == 0)) {
				return false;
			}

			String logicOperator =
				jsonConditions.has("logic") ?
					jsonConditions.getString("logic") : "AND";

			int matchedConditions = 0;
			int totalConditions = conditions.length();

			for (int i = 0; i < conditions.length(); i++) {
				JSONObject condition = conditions.getJSONObject(i);

				String customFieldName = condition.getString("field");

				if (!userCustomFields.containsKey(customFieldName)) {
					continue;
				}

				String operator =
					condition.has("operator") ?
						condition.getString("operator") : "contains";

				String referenceField = condition.getString("reference");

				List<String> customFieldValues = userCustomFields.get(
					customFieldName);

				boolean conditionMatched = false;

				for (String customFieldValue : customFieldValues) {
					if (_hasMatchingFieldValue(
							record, formFieldValues, referenceField,
							customFieldValue, operator)) {

						conditionMatched = true;

						break;
					}
				}

				if (conditionMatched) {
					matchedConditions++;
				}

				if ("OR".equalsIgnoreCase(logicOperator) && conditionMatched) {
					return true;
				}

				if ("AND".equalsIgnoreCase(logicOperator) &&
					!conditionMatched) {

					return false;
				}
			}

			if ("AND".equalsIgnoreCase(logicOperator)) {
				if (matchedConditions == totalConditions) {
					return true;
				}

				return false;
			}

			if (matchedConditions > 0) {
				return true;
			}

			return false;
		}
		catch (Exception exception) {
			_log.warn(
				"Error evaluating rule: " + rule.getFormCounterRuleId(),
				exception);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMFormService.class);

}