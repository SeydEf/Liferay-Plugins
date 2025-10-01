/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.impl;

import com.liferay.portal.aop.AopService;

import ir.seydef.plugin.formcounter.service.base.FormSubmissionStatusLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = "model.class.name=ir.seydef.plugin.formcounter.model.FormSubmissionStatus",
	service = AopService.class
)
public class FormSubmissionStatusLocalServiceImpl
	extends FormSubmissionStatusLocalServiceBaseImpl {
}