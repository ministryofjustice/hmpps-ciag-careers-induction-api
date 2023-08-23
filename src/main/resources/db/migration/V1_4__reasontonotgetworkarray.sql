alter table ciag_profile drop column reason_to_not_get_work;
create table IF NOT EXISTS  REASON_TO_NOT_WORK (offender_id varchar(255) not null, REASON integer);
alter table REASON_TO_NOT_WORK add constraint REASON_TO_NOT_WORK_CIAG_PROFILE foreign key (offender_id) references ciag_profile;
