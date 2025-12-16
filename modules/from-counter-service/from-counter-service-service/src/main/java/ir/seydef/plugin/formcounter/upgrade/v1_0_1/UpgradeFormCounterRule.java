package ir.seydef.plugin.formcounter.upgrade.v1_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import ir.seydef.plugin.formcounter.upgrade.v1_0_1.util.FormCounterRuleTable;
import ir.seydef.plugin.formcounter.upgrade.v1_0_1.util.FormSubmissionStatusTable;

/**
 * @author S.Abolfazl Eftekhari
 */
public class UpgradeFormCounterRule extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable(FormSubmissionStatusTable.TABLE_NAME)) {
			runSQL(FormSubmissionStatusTable.TABLE_SQL_CREATE);
		}

		if (!hasTable(FormCounterRuleTable.TABLE_NAME)) {
			runSQL(FormCounterRuleTable.TABLE_SQL_CREATE);
		}

		if (hasColumnType(
				FormCounterRuleTable.TABLE_NAME, "ruleConditions",
				"VARCHAR(75) null")) {

			alter(
				FormCounterRuleTable.class,
				new AlterColumnType("ruleConditions", "TEXT null"));
		}
	}

}