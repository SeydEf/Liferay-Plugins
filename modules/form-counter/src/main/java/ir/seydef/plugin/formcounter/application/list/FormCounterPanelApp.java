package ir.seydef.plugin.formcounter.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import ir.seydef.plugin.formcounter.constants.FormCounterPanelCategoryKeys;
import ir.seydef.plugin.formcounter.constants.FormCounterPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + FormCounterPanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class FormCounterPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return FormCounterPortletKeys.FORMCOUNTER;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + FormCounterPortletKeys.FORMCOUNTER + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}