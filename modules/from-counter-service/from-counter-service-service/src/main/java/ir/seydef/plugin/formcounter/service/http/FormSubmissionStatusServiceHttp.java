/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import ir.seydef.plugin.formcounter.service.FormSubmissionStatusServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>FormSubmissionStatusServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusServiceSoap
 * @generated
 */
public class FormSubmissionStatusServiceHttp {

	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			addFormSubmissionStatus(
				HttpPrincipal httpPrincipal, long formInstanceRecordId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class,
				"addFormSubmissionStatus",
				_addFormSubmissionStatusParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formInstanceRecordId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (ir.seydef.plugin.formcounter.model.FormSubmissionStatus)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			createOrUpdate(
				HttpPrincipal httpPrincipal, long formInstanceRecordId,
				boolean seen,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class, "createOrUpdate",
				_createOrUpdateParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formInstanceRecordId, seen, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (ir.seydef.plugin.formcounter.model.FormSubmissionStatus)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			getByFormInstanceRecordId(
				HttpPrincipal httpPrincipal, long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class,
				"getByFormInstanceRecordId",
				_getByFormInstanceRecordIdParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formInstanceRecordId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (ir.seydef.plugin.formcounter.model.FormSubmissionStatus)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
				getSeenByGroupId(HttpPrincipal httpPrincipal, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class, "getSeenByGroupId",
				_getSeenByGroupIdParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getSeenCountByGroupId(
			HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class, "getSeenCountByGroupId",
				_getSeenCountByGroupIdParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
				getUnseenByFormInstanceId(
					HttpPrincipal httpPrincipal, long formInstanceId,
					long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class,
				"getUnseenByFormInstanceId",
				_getUnseenByFormInstanceIdParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formInstanceId, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>
				getUnseenByGroupId(HttpPrincipal httpPrincipal, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class, "getUnseenByGroupId",
				_getUnseenByGroupIdParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<ir.seydef.plugin.formcounter.model.FormSubmissionStatus>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getUnseenCountByGroupId(
			HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class,
				"getUnseenCountByGroupId",
				_getUnseenCountByGroupIdParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean isSeen(
			HttpPrincipal httpPrincipal, long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class, "isSeen",
				_isSeenParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formInstanceRecordId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			markAsSeen(
				HttpPrincipal httpPrincipal, long formInstanceRecordId,
				long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class, "markAsSeen",
				_markAsSeenParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formInstanceRecordId, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (ir.seydef.plugin.formcounter.model.FormSubmissionStatus)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static ir.seydef.plugin.formcounter.model.FormSubmissionStatus
			markAsUnseen(HttpPrincipal httpPrincipal, long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				FormSubmissionStatusServiceUtil.class, "markAsUnseen",
				_markAsUnseenParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formInstanceRecordId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (ir.seydef.plugin.formcounter.model.FormSubmissionStatus)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FormSubmissionStatusServiceHttp.class);

	private static final Class<?>[] _addFormSubmissionStatusParameterTypes0 =
		new Class[] {
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _createOrUpdateParameterTypes1 =
		new Class[] {
			long.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _getByFormInstanceRecordIdParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getSeenByGroupIdParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getSeenCountByGroupIdParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getUnseenByFormInstanceIdParameterTypes5 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getUnseenByGroupIdParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _getUnseenCountByGroupIdParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _isSeenParameterTypes8 = new Class[] {
		long.class
	};
	private static final Class<?>[] _markAsSeenParameterTypes9 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _markAsUnseenParameterTypes10 =
		new Class[] {long.class};

}