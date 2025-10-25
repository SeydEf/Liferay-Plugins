package ir.seydef.plugin.formcounter.rules.admin.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import ir.seydef.plugin.formcounter.constants.FormCounterPanelCategoryKeys;
import ir.seydef.plugin.formcounter.rules.admin.constants.FormCounterRulesAdminPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = {
		"panel.app.order:Integer=200",
		"panel.category.key=" + FormCounterPanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class FormCounterRulesAdminPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return FormCounterRulesAdminPortletKeys.FORM_COUNTER_RULES_ADMIN;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + FormCounterRulesAdminPortletKeys.FORM_COUNTER_RULES_ADMIN + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}