package ir.seydef.plugin.formcounter.rules.admin.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;
import ir.seydef.plugin.formcounter.rules.admin.constants.FormCounterRulesAdminPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
        immediate = true,
        property = {
                "panel.app.order:Integer=400",
                "panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS_CUSTOM_APPS
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
