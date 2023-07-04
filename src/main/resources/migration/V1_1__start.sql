create table ciag_profile
(
    offender_id        varchar(255) not null,
    created_by         varchar(255),
    created_date_time  timestamp,
    desire_to_work     boolean,
    modified_by        varchar(255),
    modified_date_time timestamp,
    schema_version     varchar(255),
    primary key (offender_id)
)
create table functional
(
    offender_id                 varchar(255) not null,
    achieved_date               timestamp,
    functional_assessment       integer,
    functional_assessment_level integer
)
create table goal_steps
(
    goal_id    bigint not null,
    step       varchar(255),
    timeperiod integer
)
create table previous_work
(
    offender_id    varchar(255) not null,
    other_intrests varchar(255),
    work_list      integer
)
create table profile_goals
(
    id          bigint not null,
    goal        varchar(255),
    offender_id varchar(255),
    primary key (id)
)
create table qualification
(
    offender_id   varchar(255) not null,
    grade         varchar(255),
    highest_grade boolean      not null,
    level         integer,
    subject       varchar(255)
)
create table training
(
    offender_id     varchar(255) not null,
    custom_training varchar(255),
    training        integer
)
create table work_detail
(
    offender_id                varchar(255) not null,
    job_title                  varchar(255),
    other_job                  varchar(255),
    tasks_and_responsibilities varchar(255),
    work_type_job              integer
)
create sequence hibernate_sequence start with 1 increment by 1
alter table functional
    add constraint FKsv46op6aqy497tj9c031k54bj foreign key (offender_id) references ciag_profile
alter table goal_steps
    add constraint FK1ynpo9offjmw73ax368t2rl54 foreign key (goal_id) references profile_goals
alter table previous_work
    add constraint FKpvsnjfgwsdemynbhkmgugtsav foreign key (offender_id) references ciag_profile
alter table profile_goals
    add constraint FKpbr2v365hhp0uusbki3a3uoae foreign key (offender_id) references ciag_profile
alter table qualification
    add constraint FK11c4977h2lw320fae1298vhxl foreign key (offender_id) references ciag_profile
alter table training
    add constraint FKbxp0j90dv6of5q6wi4nw0t0r9 foreign key (offender_id) references ciag_profile
alter table work_detail
    add constraint FK71t3fn0rdd8um97arimj3is7t foreign key (offender_id) references ciag_profile
