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