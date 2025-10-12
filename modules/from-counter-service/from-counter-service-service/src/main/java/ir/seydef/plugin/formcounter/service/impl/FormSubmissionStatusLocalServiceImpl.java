/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import ir.seydef.plugin.formcounter.model.FormSubmissionStatus;
import ir.seydef.plugin.formcounter.service.base.FormSubmissionStatusLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = "model.class.name=ir.seydef.plugin.formcounter.model.FormSubmissionStatus",
	service = AopService.class
)
public class FormSubmissionStatusLocalServiceImpl
	extends FormSubmissionStatusLocalServiceBaseImpl {

	public FormSubmissionStatus addFormSubmissionStatus(
			long formInstanceRecordId, ServiceContext serviceContext)
		throws PortalException {

		long formSubmissionStatusId = counterLocalService.increment();

		FormSubmissionStatus formSubmissionStatus =
			formSubmissionStatusPersistence.create(formSubmissionStatusId);

		formSubmissionStatus.setFormInstanceRecordId(formInstanceRecordId);

		formSubmissionStatus.setSeen(false);
		formSubmissionStatus.setSeenDate(null);

		User user = _userLocalService.getUser(serviceContext.getUserId());
		Date now = new Date();

		formSubmissionStatus.setCompanyId(serviceContext.getCompanyId());
		formSubmissionStatus.setGroupId(serviceContext.getScopeGroupId());
		formSubmissionStatus.setUserId(user.getUserId());
		formSubmissionStatus.setUserName(user.getFullName());
		formSubmissionStatus.setCreateDate(now);
		formSubmissionStatus.setModifiedDate(now);

		return formSubmissionStatusPersistence.update(formSubmissionStatus);
	}

	public FormSubmissionStatus createOrUpdate(
			long formInstanceRecordId, boolean seen,
			ServiceContext serviceContext)
		throws PortalException {

		FormSubmissionStatus existing = getByFormInstanceRecordId(
			formInstanceRecordId);

		if (existing != null) {


			existing.setSeen(seen);
			existing.setSeenDate(seen ? new Date() : null);
			existing.setModifiedDate(new Date());

			return formSubmissionStatusPersistence.update(existing);
		}

		FormSubmissionStatus newStatus = addFormSubmissionStatus(
			formInstanceRecordId, serviceContext);

		if (seen) {
			newStatus.setSeen(true);
			newStatus.setSeenDate(new Date());
			newStatus = formSubmissionStatusPersistence.update(newStatus);
		}

		return newStatus;
	}

	public FormSubmissionStatus getByFormInstanceRecordId(
		long formInstanceRecordId) {

		try {
			return formSubmissionStatusPersistence.findByFormInstanceRecordId(
				formInstanceRecordId);
		}
		catch (Exception e) {
			return null;
		}
	}

	public List<FormSubmissionStatus> getSeenByGroupId(long groupId) {
		return formSubmissionStatusPersistence.findByG_S(groupId, true);
	}

	public int getSeenCountByGroupId(long groupId) {
		return formSubmissionStatusPersistence.countByG_S(groupId, true);
	}

	public List<FormSubmissionStatus> getUnseenByFormInstanceId(
		long formInstanceId, long groupId) {

		try {

			com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery =
				dynamicQuery(
				).add(
					com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.
						eq("groupId", groupId)
				).add(
					com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.
						eq("seen", false)
				);

			List<FormSubmissionStatus> unseenSubmissions = dynamicQuery(
				dynamicQuery);

			List<FormSubmissionStatus> result = new java.util.ArrayList<>();

			for (FormSubmissionStatus status : unseenSubmissions) {
				try {

					com.liferay.dynamic.data.lists.model.DDLRecord record =
						com.liferay.dynamic.data.lists.service.
							DDLRecordLocalServiceUtil.getDDLRecord(
								status.getFormInstanceRecordId());

					if (record.getRecordSetId() == formInstanceId) {
						result.add(status);
					}
				}
				catch (Exception e) {
					continue;
				}
			}

			return result;
		}
		catch (Exception e) {
			return java.util.Collections.emptyList();
		}
	}

	public List<FormSubmissionStatus> getUnseenByGroupId(long groupId) {
		return formSubmissionStatusPersistence.findByG_S(groupId, false);
	}

	public int getUnseenCountByGroupId(long groupId) {
		return formSubmissionStatusPersistence.countByG_S(groupId, false);
	}

	public boolean isSeen(long formInstanceRecordId) {
		FormSubmissionStatus status = getByFormInstanceRecordId(
			formInstanceRecordId);

        return (status != null) && status.isSeen();
    }

	public FormSubmissionStatus markAsSeen(
			long formInstanceRecordId, long userId)
		throws PortalException {

		FormSubmissionStatus formSubmissionStatus =
			formSubmissionStatusPersistence.findByFormInstanceRecordId(
				formInstanceRecordId);

		formSubmissionStatus.setSeen(true);
		formSubmissionStatus.setSeenDate(new Date());
		formSubmissionStatus.setModifiedDate(new Date());

		return formSubmissionStatusPersistence.update(formSubmissionStatus);
	}

	public FormSubmissionStatus markAsUnseen(long formInstanceRecordId)
		throws PortalException {

		FormSubmissionStatus formSubmissionStatus =
			formSubmissionStatusPersistence.findByFormInstanceRecordId(
				formInstanceRecordId);

		formSubmissionStatus.setSeen(false);
		formSubmissionStatus.setSeenDate(null);
		formSubmissionStatus.setModifiedDate(new Date());

		return formSubmissionStatusPersistence.update(formSubmissionStatus);
	}

	@Reference
	private UserLocalService _userLocalService;

}