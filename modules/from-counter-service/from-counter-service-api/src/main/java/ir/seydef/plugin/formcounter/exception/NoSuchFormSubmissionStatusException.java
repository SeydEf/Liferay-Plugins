/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package ir.seydef.plugin.formcounter.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author S.Abolfazl Eftekhari
 */
public class NoSuchFormSubmissionStatusException extends NoSuchModelException {

	public NoSuchFormSubmissionStatusException() {
	}

	public NoSuchFormSubmissionStatusException(String msg) {
		super(msg);
	}

	public NoSuchFormSubmissionStatusException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchFormSubmissionStatusException(Throwable throwable) {
		super(throwable);
	}

}