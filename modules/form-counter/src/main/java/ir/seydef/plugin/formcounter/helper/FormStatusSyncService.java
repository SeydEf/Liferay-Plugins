package ir.seydef.plugin.formcounter.helper;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.service.FormSubmissionStatusLocalServiceUtil;
import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
        immediate = true,
        service = FormStatusSyncService.class
)
public class FormStatusSyncService {

    private static final Log _log = LogFactoryUtil.getLog(FormStatusSyncService.class);

    public void syncFormSubmissionStatuses(List<DDMFormInstance> ddmFormInstances, ServiceContext serviceContext) {
        try {
            for (DDMFormInstance formInstance : ddmFormInstances) {
                try {
                    List<DDMFormInstanceRecord> records = DDMFormInstanceRecordLocalServiceUtil
                            .getFormInstanceRecords(formInstance.getFormInstanceId());

                    for (DDMFormInstanceRecord record : records) {
                        try {
                            if (DDMFormService.recordHasRuleReferenceFields(record)) {
                                FormSubmissionStatus existingStatus = FormSubmissionStatusLocalServiceUtil
                                        .getByFormInstanceRecordId(record.getFormInstanceRecordId());

                                if (existingStatus == null) {
                                    FormSubmissionStatusLocalServiceUtil
                                            .addFormSubmissionStatus(record.getFormInstanceRecordId(), serviceContext);
                                }
                            }
                        } catch (Exception e) {
                            _log.error("Error processing record ID: " + record.getFormInstanceRecordId(), e);
                        }
                    }
                } catch (Exception e) {
                    _log.error("Error processing form instance: " + formInstance.getFormInstanceId(), e);
                }
            }

        } catch (Exception e) {
            _log.error("Error during FormSubmissionStatus sync", e);
        }
    }

    public void createStatusForNewRecord(long formInstanceRecordId, ServiceContext serviceContext) {
        try {
            DDMFormInstanceRecord record = DDMFormInstanceRecordLocalServiceUtil
                    .fetchDDMFormInstanceRecord(formInstanceRecordId);

            if (record == null) {
                _log.warn("Form record not found with ID: " + formInstanceRecordId);
                return;
            }

            FormSubmissionStatus existingStatus = FormSubmissionStatusLocalServiceUtil
                    .getByFormInstanceRecordId(formInstanceRecordId);

            if (existingStatus != null) {
                return;
            }

            FormSubmissionStatusLocalServiceUtil
                    .addFormSubmissionStatus(formInstanceRecordId, serviceContext);

        } catch (Exception e) {
            _log.error("Error creating FormSubmissionStatus for record ID: " + formInstanceRecordId, e);
        }
    }

    public void markRecordAsSeen(long formInstanceRecordId, long userId) {
        try {
            FormSubmissionStatus status = FormSubmissionStatusLocalServiceUtil
                    .getByFormInstanceRecordId(formInstanceRecordId);

            if (status == null) {
                _log.warn("No FormSubmissionStatus found for record ID: " + formInstanceRecordId);
                return;
            }

            if (!status.isSeen()) {
                FormSubmissionStatusLocalServiceUtil.markAsSeen(formInstanceRecordId, userId);
            }

        } catch (Exception e) {
            _log.error("Error marking record ID " + formInstanceRecordId + " as seen", e);
        }
    }
}
