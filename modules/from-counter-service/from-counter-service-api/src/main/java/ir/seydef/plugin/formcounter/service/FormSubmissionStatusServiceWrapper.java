/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FormSubmissionStatusService}.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusService
 * @generated
 */
public class FormSubmissionStatusServiceWrapper
	implements FormSubmissionStatusService,
			   ServiceWrapper<FormSubmissionStatusService> {

	public FormSubmissionStatusServiceWrapper(
		FormSubmissionStatusService formSubmissionStatusService) {

		_formSubmissionStatusService = formSubmissionStatusService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _formSubmissionStatusService.getOSGiServiceIdentifier();
	}

	@Override
	public FormSubmissionStatusService getWrappedService() {
		return _formSubmissionStatusService;
	}

	@Override
	public void setWrappedService(
		FormSubmissionStatusService formSubmissionStatusService) {

		_formSubmissionStatusService = formSubmissionStatusService;
	}

	private FormSubmissionStatusService _formSubmissionStatusService;

}