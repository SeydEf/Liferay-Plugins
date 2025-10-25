/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author S.Abolfazl Eftekhari
 */
public class NoSuchRuleException extends NoSuchModelException {

	public NoSuchRuleException() {
	}

	public NoSuchRuleException(String msg) {
		super(msg);
	}

	public NoSuchRuleException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchRuleException(Throwable throwable) {
		super(throwable);
	}

}