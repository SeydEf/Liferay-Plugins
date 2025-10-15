package ir.seydef.plugin.formcounter.rules.admin.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import ir.seydef.plugin.formcounter.model.FormCounterRule;
import ir.seydef.plugin.formcounter.rules.admin.constants.FormCounterRulesAdminPortletKeys;
import ir.seydef.plugin.formcounter.rules.admin.model.Rule;
import ir.seydef.plugin.formcounter.rules.admin.model.RuleCondition;
import ir.seydef.plugin.formcounter.service.FormCounterRuleLocalService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=Form Counter Rules Admin",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + FormCounterRulesAdminPortletKeys.FORM_COUNTER_RULES_ADMIN,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class FormCounterRulesAdminPortlet extends MVCPortlet {

    private static final Log _log = LogFactoryUtil.getLog(FormCounterRulesAdminPortlet.class);
    @Reference
    private FormCounterRuleLocalService _formCounterRuleLocalService;

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        try {
            List<FormCounterRule> rules = _formCounterRuleLocalService.getFormCounterRules(-1, -1);
            renderRequest.setAttribute("rules", rules);

            long ruleId = ParamUtil.getLong(renderRequest, "ruleId", 0);
            if (ruleId > 0) {
                FormCounterRule rule = _formCounterRuleLocalService.fetchFormCounterRule(ruleId);
                if (rule != null) {
                    renderRequest.setAttribute("rule", rule);

                    Rule ruleModel = Rule.fromJSONString(rule.getRuleConditions());
                    if (ruleModel != null) {
                        renderRequest.setAttribute("ruleModel", ruleModel);
                    }
                }
            }

        } catch (Exception e) {
            _log.error("Error preparing rule admin portlet render", e);
            SessionErrors.add(renderRequest, "error-loading-rules");
        }

        super.render(renderRequest, renderResponse);
    }

    public void addRule(
            ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        ServiceContext serviceContext = ServiceContextFactory.getInstance(
                FormCounterRule.class.getName(), actionRequest);

        String ruleName = ParamUtil.getString(actionRequest, "ruleName");
        String description = ParamUtil.getString(actionRequest, "description");
        boolean active = ParamUtil.getBoolean(actionRequest, "active", true);
        String logicOperator = ParamUtil.getString(actionRequest, "logicOperator", "AND");

        Rule ruleModel = new Rule();
        ruleModel.setName(ruleName);
        ruleModel.setDescription(description);
        ruleModel.setActive(active);
        ruleModel.setLogicOperator(logicOperator);

        int conditionCount = ParamUtil.getInteger(actionRequest, "conditionCount", 0);

        for (int i = 0; i < conditionCount; i++) {
            String field = ParamUtil.getString(actionRequest, "field" + i);
            String operator = ParamUtil.getString(actionRequest, "operator" + i);
            String reference = ParamUtil.getString(actionRequest, "reference" + i);

            RuleCondition condition = new RuleCondition(field, operator, reference);
            ruleModel.addCondition(condition);
        }

        try {
            _formCounterRuleLocalService.addFormCounterRule(
                    0,
                    ruleName,
                    description,
                    ruleModel.toJSONString(),
                    logicOperator,
                    active,
                    serviceContext);

            SessionMessages.add(actionRequest, "rule-added-successfully");
        } catch (Exception e) {
            _log.error("Error adding rule", e);
            SessionErrors.add(actionRequest, "error-adding-rule");
        }
    }

    public void updateRule(
            ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        long ruleId = ParamUtil.getLong(actionRequest, "ruleId");

        if (ruleId <= 0) {
            SessionErrors.add(actionRequest, "error-invalid-rule-id");
            return;
        }

        String ruleName = ParamUtil.getString(actionRequest, "ruleName");
        String description = ParamUtil.getString(actionRequest, "description");
        boolean active = ParamUtil.getBoolean(actionRequest, "active", true);
        String logicOperator = ParamUtil.getString(actionRequest, "logicOperator", "AND");

        Rule ruleModel = new Rule();
        ruleModel.setName(ruleName);
        ruleModel.setRuleId(ruleId);
        ruleModel.setDescription(description);
        ruleModel.setLogicOperator(logicOperator);
        ruleModel.setActive(active);

        int conditionCount = ParamUtil.getInteger(actionRequest, "conditionCount", 0);

        for (int i = 0; i < conditionCount; i++) {
            String field = ParamUtil.getString(actionRequest, "field" + i);
            String operator = ParamUtil.getString(actionRequest, "operator" + i);
            String reference = ParamUtil.getString(actionRequest, "reference" + i);

            RuleCondition condition = new RuleCondition(field, operator, reference);
            ruleModel.addCondition(condition);
        }

        try {
            FormCounterRule rule = _formCounterRuleLocalService.getFormCounterRule(ruleId);

            rule.setRuleName(ruleName);
            rule.setDescription(description);
            rule.setRuleConditions(ruleModel.toJSONString());
            rule.setLogicOperator(logicOperator);
            rule.setActive(active);
            rule.setModifiedDate(new Date());

            _formCounterRuleLocalService.updateFormCounterRule(rule);

            SessionMessages.add(actionRequest, "rule-updated-successfully");
        } catch (Exception e) {
            _log.error("Error updating rule", e);
            SessionErrors.add(actionRequest, "error-updating-rule");
        }
    }

    public void deleteRule(
            ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        long ruleId = ParamUtil.getLong(actionRequest, "ruleId");

        if (ruleId <= 0) {
            SessionErrors.add(actionRequest, "error-invalid-rule-id");
            return;
        }

        try {
            _formCounterRuleLocalService.deleteFormCounterRule(ruleId);
            SessionMessages.add(actionRequest, "rule-deleted-successfully");
        } catch (Exception e) {
            _log.error("Error deleting rule", e);
            SessionErrors.add(actionRequest, "error-deleting-rule");
        }
    }
}