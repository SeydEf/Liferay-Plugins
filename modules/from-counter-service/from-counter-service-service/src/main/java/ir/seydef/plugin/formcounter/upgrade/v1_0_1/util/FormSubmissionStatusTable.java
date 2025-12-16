package ir.seydef.plugin.formcounter.upgrade.v1_0_1.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author S.Abolfazl Eftekhari
 * @generated
 */
public class FormSubmissionStatusTable {
    public static final String TABLE_NAME = "FormSubmissionStatus";

    public static final Object[][] TABLE_COLUMNS = {
            {"formSubmissionStatusId", Types.BIGINT},
            {"formInstanceRecordId", Types.BIGINT}, {"seen", Types.BOOLEAN},
            {"seenDate", Types.TIMESTAMP}, {"companyId", Types.BIGINT},
            {"groupId", Types.BIGINT}, {"userId", Types.BIGINT},
            {"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
            {"modifiedDate", Types.TIMESTAMP}
    };

    public static final Map<String, Integer> TABLE_COLUMNS_MAP =
            new HashMap<String, Integer>();

    static {
        TABLE_COLUMNS_MAP.put("formSubmissionStatusId", Types.BIGINT);
        TABLE_COLUMNS_MAP.put("formInstanceRecordId", Types.BIGINT);
        TABLE_COLUMNS_MAP.put("seen", Types.BOOLEAN);
        TABLE_COLUMNS_MAP.put("seenDate", Types.TIMESTAMP);
        TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
        TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
        TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
        TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
        TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
        TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
    }

    public static final String TABLE_SQL_CREATE =
            "create table FormSubmissionStatus (formSubmissionStatusId LONG not null primary key,formInstanceRecordId LONG,seen BOOLEAN,seenDate DATE null,companyId LONG,groupId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null)";

    public static final String TABLE_SQL_DROP =
            "drop table FormSubmissionStatus";

    public static final String[] TABLE_SQL_ADD_INDEXES = {
        "create index IX_1E1F4C80 on FormSubmissionStatus (companyId);" +
        "create index IX_837B541 on FormSubmissionStatus (formInstanceRecordId);" +
        "create index IX_335F9E33 on FormSubmissionStatus (seen, groupId);"
    };
}
