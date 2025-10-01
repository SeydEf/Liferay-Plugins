/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.service.impl;

import com.liferay.portal.aop.AopService;

import ir.seydef.plugin.formcounter.service.base.FormSubmissionStatusServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = {
		"json.web.service.context.name=formcounter",
		"json.web.service.context.path=FormSubmissionStatus"
	},
	service = AopService.class
)
public class FormSubmissionStatusServiceImpl
	extends FormSubmissionStatusServiceBaseImpl {
}