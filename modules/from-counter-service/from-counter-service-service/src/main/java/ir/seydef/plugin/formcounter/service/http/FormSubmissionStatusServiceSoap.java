/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import ir.seydef.plugin.formcounter.service.FormSubmissionStatusServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>FormSubmissionStatusServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap</code>. If the method in the
 * service utility returns a
 * <code>ir.seydef.plugin.formcounter.model.FormSubmissionStatus</code>, that is translated to a
 * <code>ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FormSubmissionStatusServiceSoap {

	/**
	 * Creates a new FormSubmissionStatus entry for a form instance record
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param serviceContext the service context
	 * @return the created FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap
			addFormSubmissionStatus(
				long formInstanceRecordId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			ir.seydef.plugin.formcounter.model.FormSubmissionStatus
				returnValue =
					FormSubmissionStatusServiceUtil.addFormSubmissionStatus(
						formInstanceRecordId, serviceContext);

			return ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Marks a form submission as seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param userId the user ID who marked it as seen
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap
			markAsSeen(long formInstanceRecordId, long userId)
		throws RemoteException {

		try {
			ir.seydef.plugin.formcounter.model.FormSubmissionStatus
				returnValue = FormSubmissionStatusServiceUtil.markAsSeen(
					formInstanceRecordId, userId);

			return ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Marks a form submission as unseen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the updated FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap
			markAsUnseen(long formInstanceRecordId)
		throws RemoteException {

		try {
			ir.seydef.plugin.formcounter.model.FormSubmissionStatus
				returnValue = FormSubmissionStatusServiceUtil.markAsUnseen(
					formInstanceRecordId);

			return ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Gets the FormSubmissionStatus by form instance record ID
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the FormSubmissionStatus, or null if not found
	 * @throws PortalException if a portal exception occurred
	 */
	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap
			getByFormInstanceRecordId(long formInstanceRecordId)
		throws RemoteException {

		try {
			ir.seydef.plugin.formcounter.model.FormSubmissionStatus
				returnValue =
					FormSubmissionStatusServiceUtil.getByFormInstanceRecordId(
						formInstanceRecordId);

			return ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Checks if a form submission is seen
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return true if seen, false otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	public static boolean isSeen(long formInstanceRecordId)
		throws RemoteException {

		try {
			boolean returnValue = FormSubmissionStatusServiceUtil.isSeen(
				formInstanceRecordId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Gets all unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of unseen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap[]
			getUnseenByGroupId(long groupId)
		throws RemoteException {

		try {
			java.util.List
				<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
					returnValue =
						FormSubmissionStatusServiceUtil.getUnseenByGroupId(
							groupId);

			return ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Gets all seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return list of seen FormSubmissionStatus entries
	 * @throws PortalException if a portal exception occurred
	 */
	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap[]
			getSeenByGroupId(long groupId)
		throws RemoteException {

		try {
			java.util.List
				<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
					returnValue =
						FormSubmissionStatusServiceUtil.getSeenByGroupId(
							groupId);

			return ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Gets count of unseen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of unseen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getUnseenCountByGroupId(long groupId)
		throws RemoteException {

		try {
			int returnValue =
				FormSubmissionStatusServiceUtil.getUnseenCountByGroupId(
					groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Gets count of seen form submissions for a group
	 *
	 * @param groupId the group ID
	 * @return count of seen submissions
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getSeenCountByGroupId(long groupId)
		throws RemoteException {

		try {
			int returnValue =
				FormSubmissionStatusServiceUtil.getSeenCountByGroupId(groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Creates or updates FormSubmissionStatus for a form instance record
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param seen the seen status
	 * @param serviceContext the service context
	 * @return the FormSubmissionStatus
	 * @throws PortalException if a portal exception occurred
	 */
	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap
			createOrUpdate(
				long formInstanceRecordId, boolean seen,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			ir.seydef.plugin.formcounter.model.FormSubmissionStatus
				returnValue = FormSubmissionStatusServiceUtil.createOrUpdate(
					formInstanceRecordId, seen, serviceContext);

			return ir.seydef.plugin.formcounter.model.FormSubmissionStatusSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FormSubmissionStatusServiceSoap.class);

}