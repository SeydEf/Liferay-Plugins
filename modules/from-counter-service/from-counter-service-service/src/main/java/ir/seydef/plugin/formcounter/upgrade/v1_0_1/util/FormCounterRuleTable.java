package ir.seydef.plugin.formcounter.upgrade.v1_0_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author S.Abolfazl Eftekhari
 * @generated
 */
public class FormCounterRuleTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"formCounterRuleId", Types.BIGINT}, {"ruleName", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"ruleConditions", Types.VARCHAR},
		{"logicOperator", Types.VARCHAR}, {"active", Types.BOOLEAN},
		{"companyId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("formCounterRuleId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ruleName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("ruleConditions", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("logicOperator", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("active", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
	}

	public static final String TABLE_NAME = "FormCounterRule";

	public static final String TABLE_SQL_CREATE =
		"create table FormCounterRule (formCounterRuleId LONG not null primary key,ruleName VARCHAR(75) null,description VARCHAR(75) null,ruleConditions VARCHAR(75) null,logicOperator VARCHAR(75) null,active BOOLEAN,companyId LONG,groupId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table FormCounterRule";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
			"create index IX_610391EF on FormCounterRule (active_);" +
			"create index IX_1E1F4C80 on FormSubmissionStatus (companyId);" +
			"create index IX_837B541 on FormSubmissionStatus (formInstanceRecordId);" +
			"create index IX_2D729131 on FormSubmissionStatus (groupId, seen);" +
			"create index IX_756C4857 on FormSubmissionStatus (seen);"
	};

}
