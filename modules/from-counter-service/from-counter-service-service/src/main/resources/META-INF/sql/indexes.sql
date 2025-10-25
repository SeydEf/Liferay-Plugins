create index IX_610391EF on FormCounterRule (active_);

create index IX_1E1F4C80 on FormSubmissionStatus (companyId);
create index IX_837B541 on FormSubmissionStatus (formInstanceRecordId);
create index IX_335F9E33 on FormSubmissionStatus (seen, groupId);