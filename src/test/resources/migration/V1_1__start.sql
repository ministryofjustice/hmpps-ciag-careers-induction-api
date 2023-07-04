drop table if exists "functional" cascade;

drop table if exists "previous_work" cascade;

drop table if exists "qualification" cascade;

drop table if exists "training" cascade;

drop table if exists "user" cascade;

drop table if exists "ciag_profile" cascade;

drop table if exists "goal_steps" cascade;

drop table if exists "profile_goals" cascade;

drop sequence if exists "goal_steps_seq";

drop sequence if exists "profile_goals_seq";

create sequence "article_seq" start with 1 increment by 50;

create sequence "goal_steps_seq" start with 1 increment by 50;

create sequence "profile_goals_seq" start with 1 increment by 50;

create table "functional"
(
    "ciagprofile_offender_id"     varchar(255) not null,
    "job_title"                   varchar(255),
    "other_job"                   varchar(255),
    "tasks_and_responsibilities"  varchar(255),
    "work_type_job"               smallint,
    "offender_id"                 varchar(255) not null,
    "achieved_date"               timestamp(6),
    "functional_assessment"       smallint,
    "functional_assessment_level" smallint
);

create table "previous_work"
(
    "offender_id"    varchar(255) not null,
    "other_intrests" varchar(255),
    "work_list"      smallint
);

create table "qualification"
(
    "offender_id"   varchar(255) not null,
    "grade"         varchar(255),
    "highest_grade" boolean      not null,
    "level"         smallint,
    "subject"       varchar(255)
);

create table "training"
(
    "offender_id"     varchar(255) not null,
    "custom_training" varchar(255),
    "training"        smallint
);

create table "ciag_profile"
(
    "offender_id"        varchar(255) not null,
    "booking_id"         bigint       not null,
    "created_by"         varchar(255),
    "created_date_time"  timestamp(6),
    "desire_to_work"     boolean,
    "modified_by"        varchar(255),
    "modified_date_time" timestamp(6),
    "schema_version"     varchar(255),
    primary key ("offender_id")
);

create table "goal_steps"
(
    "id"         bigint not null,
    "step"       varchar(255),
    "timeperiod" smallint,
    "goals_id"   bigint,
    primary key ("id")
);

create table "profile_goals"
(
    "id"                  bigint not null,
    "goal"                varchar(255),
    "profile_offender_id" varchar(255),
    primary key ("id")
);

alter table if exists "functional"
    add constraint "FKdbxscpkcppys3w8bx0v5mxc9t" foreign key ("ciagprofile_offender_id") references "ciag_profile";
alter table if exists "functional"
    add constraint "FKoyop15iqawlkep4nk2let9ux9" foreign key ("offender_id") references "ciag_profile";
alter table if exists "previous_work"
    add constraint "FKd18dub1mqkqwjnifmjri4wruc" foreign key ("offender_id") references "ciag_profile";
alter table if exists "qualification"
    add constraint "FK8jhx4bck3waaihmblo70a6ty" foreign key ("offender_id") references "ciag_profile";
alter table if exists "training"
    add constraint "FKhj5wudd182qjpvoj3ctewgm8e" foreign key ("offender_id") references "ciag_profile";
alter table if exists "goal_steps"
    add constraint "FKr7438iei6ce4lqdl04ppk8rk" foreign key ("goals_id") references "profile_goals";
alter table if exists "profile_goals"
    add constraint "FKiafie9w02om2o1bv7qlfvo64d" foreign key ("profile_offender_id") references "ciag_profile";