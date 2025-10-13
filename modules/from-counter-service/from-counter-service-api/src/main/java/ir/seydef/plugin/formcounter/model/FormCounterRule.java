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
 * The extended model interface for the FormCounterRule service. Represents a row in the &quot;FormCounterRule&quot; database table, with each column mapped to a property of this class.
 *
 * @author S.Abolfazl Eftekhari
 * @see FormCounterRuleModel
 * @generated
 */
@ImplementationClassName(
	"ir.seydef.plugin.formcounter.model.impl.FormCounterRuleImpl"
)
@ProviderType
public interface FormCounterRule extends FormCounterRuleModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>ir.seydef.plugin.formcounter.model.impl.FormCounterRuleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FormCounterRule, Long>
		FORM_COUNTER_RULE_ID_ACCESSOR = new Accessor<FormCounterRule, Long>() {

			@Override
			public Long get(FormCounterRule formCounterRule) {
				return formCounterRule.getFormCounterRuleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FormCounterRule> getTypeClass() {
				return FormCounterRule.class;
			}

		};

}