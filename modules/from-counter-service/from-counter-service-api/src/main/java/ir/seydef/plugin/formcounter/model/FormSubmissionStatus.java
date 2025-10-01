/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the FormSubmissionStatus service. Represents a row in the &quot;FormSubmissionStatus&quot; database table, with each column mapped to a property of this class.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormSubmissionStatusModel
 * @generated
 */
@ImplementationClassName(
	"ir.seydef.plugin.formcounter.model.impl.FormSubmissionStatusImpl"
)
@ProviderType
public interface FormSubmissionStatus
	extends FormSubmissionStatusModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>ir.seydef.plugin.formcounter.model.impl.FormSubmissionStatusImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FormSubmissionStatus, Long>
		FORM_SUBMISSION_STATUS_ID_ACCESSOR =
			new Accessor<FormSubmissionStatus, Long>() {

				@Override
				public Long get(FormSubmissionStatus formSubmissionStatus) {
					return formSubmissionStatus.getFormSubmissionStatusId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<FormSubmissionStatus> getTypeClass() {
					return FormSubmissionStatus.class;
				}

			};

}