package ir.seydef.plugin.formcounter.listener;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import ir.seydef.plugin.formcounter.serviceHelper.DDMFormService;
import ir.seydef.plugin.formcounter.serviceHelper.FormStatusSyncService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
        immediate = true,
        service = ModelListener.class
)
public class DDMFormInstanceRecordModelListener extends BaseModelListener<DDMFormInstanceRecordVersion> {

    private static final Log _log = LogFactoryUtil.getLog(DDMFormInstanceRecordModelListener.class);

    @Reference
    private FormStatusSyncService _formStatusSyncService;

    @Override
    public void onAfterCreate(DDMFormInstanceRecordVersion model) throws ModelListenerException {
        try {
            String branchId = DDMFormService.extractBranchIdFromRecord(model.getFormInstanceRecord());

            if (Validator.isNotNull(branchId)) {
                ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
                if (serviceContext == null) {
                    serviceContext = new ServiceContext();
                    serviceContext.setCompanyId(model.getCompanyId());
                    serviceContext.setScopeGroupId(model.getGroupId());
                    serviceContext.setUserId(model.getUserId());
                }

                _formStatusSyncService.createStatusForNewRecord(
                        model.getFormInstanceRecordId(), serviceContext);
            }

        } catch (Exception e) {
            _log.error("Error creating FormSubmissionStatus for new form record: " +
                    model.getFormInstanceRecordId(), e);
        }
    }
}
