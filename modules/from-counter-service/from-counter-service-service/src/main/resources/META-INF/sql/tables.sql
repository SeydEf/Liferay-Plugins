create table FormCounterRule (
	formCounterRuleId LONG not null primary key,
	ruleName VARCHAR(75) null,
	description VARCHAR(75) null,
	ruleConditions VARCHAR(75) null,
	active_ BOOLEAN,
	companyId LONG,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);

create table FormSubmissionStatus (
	formSubmissionStatusId LONG not null primary key,
	formInstanceRecordId LONG,
	seen BOOLEAN,
	seenDate DATE null,
	companyId LONG,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);