package ir.seydef.plugin.formcounter.helper;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(immediate = true, service = FormStatusSyncService.class)
public class FormStatusSyncService {

	public void createStatusForNewRecord(
		long formInstanceRecordId, ServiceContext serviceContext) {

		try {
			DDMFormInstanceRecord record =
				DDMFormInstanceRecordLocalServiceUtil.
					fetchDDMFormInstanceRecord(formInstanceRecordId);

			if (record == null) {
				_log.warn(
					"Form record not found with ID: " + formInstanceRecordId);

				return;
			}

			FormSubmissionStatus existingStatus =
				FormSubmissionStatusLocalServiceUtil.getByFormInstanceRecordId(
					formInstanceRecordId);

			if (existingStatus != null) {
				return;
			}

			FormSubmissionStatusLocalServiceUtil.addFormSubmissionStatus(
				formInstanceRecordId, serviceContext);
		}
		catch (Exception exception) {
			_log.error(
				"Error creating FormSubmissionStatus for record ID: " +
					formInstanceRecordId,
				exception);
		}
	}

	public void markRecordAsSeen(long formInstanceRecordId, long userId) {
		try {
			FormSubmissionStatus status =
				FormSubmissionStatusLocalServiceUtil.getByFormInstanceRecordId(
					formInstanceRecordId);

			if (status == null) {
				_log.warn(
					"No FormSubmissionStatus found for record ID: " +
						formInstanceRecordId);

				return;
			}

			if (!status.isSeen()) {
				FormSubmissionStatusLocalServiceUtil.markAsSeen(
					formInstanceRecordId, userId);
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error marking record ID " + formInstanceRecordId + " as seen",
				exception);
		}
	}

	public void syncFormSubmissionStatuses(
		List<DDMFormInstance> ddmFormInstances, ServiceContext serviceContext) {

		try {
			for (DDMFormInstance formInstance : ddmFormInstances) {
				try {
					List<DDMFormInstanceRecord> records =
						DDMFormInstanceRecordLocalServiceUtil.
							getFormInstanceRecords(
								formInstance.getFormInstanceId());

					for (DDMFormInstanceRecord record : records) {
						try {
							if (DDMFormService.recordHasRuleReferenceFields(
									record)) {

								FormSubmissionStatus existingStatus =
									FormSubmissionStatusLocalServiceUtil.
										getByFormInstanceRecordId(
											record.getFormInstanceRecordId());

								if (existingStatus == null) {
									FormSubmissionStatusLocalServiceUtil.
										addFormSubmissionStatus(
											record.getFormInstanceRecordId(),
											serviceContext);
								}
							}
						}
						catch (Exception exception) {
							_log.error(
								"Error processing record ID: " +
									record.getFormInstanceRecordId(),
								exception);
						}
					}
				}
				catch (Exception exception) {
					_log.error(
						"Error processing form instance: " +
							formInstance.getFormInstanceId(),
						exception);
				}
			}
		}
		catch (Exception exception) {
			_log.error("Error during FormSubmissionStatus sync", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormStatusSyncService.class);

}