create table ability_to_work_impact (offender_id varchar(255) not null, work_impact_ integer)
create table achieved_qualification (work_interests_id bigint not null, grade varchar(255), highest_grade boolean not null, level integer, subject varchar(255))
create table ciag_profile (offender_id varchar(255) not null, created_by varchar(255), created_date_time timestamp, desire_to_work boolean, hoping_to_get_work integer, modified_by varchar(255), modified_date_time timestamp, other_ability_to_work_impact varchar(255), reason_to_not_get_work varchar(255), schema_version varchar(255), primary key (offender_id))
create table education_qualification (id bigint not null, education_level integer, modified_by varchar(255), modified_date_time timestamp, other_qualification varchar(255), offender_id varchar(255), primary key (id))
create table extra_qualification (work_interests_id bigint not null, qualification integer)
create table particular_work_interests (work_id bigint not null, details varchar(255), other_work varchar(255), role varchar(255), work_type integer)
create table personal_work_interests (work_interests_id bigint not null, personal_work_interest integer)
create table previous_work (id bigint not null, has_worked_before boolean not null, modified_by varchar(255), modified_date_time timestamp, offender_id varchar(255), primary key (id))
create table previous_work_detail (work_id bigint not null, details varchar(255), other_work varchar(255), role varchar(255), work_type integer)
create table prison_education (prison_work_education_id bigint not null, education integer)
create table prison_work (prison_work_education_id bigint not null, work integer)
create table prison_work_education (id bigint not null, modified_by varchar(255), modified_date_time timestamp, other_prison_education varchar(255), other_prison_work varchar(255), offender_id varchar(255), primary key (id))
create table reason_not_to_get_work (offender_id varchar(255) not null, reason integer)
create table skills_work_interests (work_interests_id bigint not null, skills integer)
create table work_interests (id bigint not null, modified_by varchar(255), modified_date_time timestamp, other_personal_intrests varchar(255), other_skill varchar(255), other_work_interest varchar(255), offender_id varchar(255), work_id bigint not null, work_interests integer, primary key (id))
create sequence hibernate_sequence start with 1 increment by 1
alter table ability_to_work_impact add constraint FKqwdh8an0f7tosgl2djp3eaq6a foreign key (offender_id) references ciag_profile
alter table achieved_qualification add constraint FK9nsis7gdqyadskbcksm7ugskq foreign key (work_interests_id) references education_qualification
alter table education_qualification add constraint FKjbytpei5pu56wxig5klvy2vtv foreign key (offender_id) references ciag_profile
alter table extra_qualification add constraint FKrvutj3ivovixy37otbya4no63 foreign key (work_interests_id) references education_qualification
alter table particular_work_interests add constraint FK9113j1b979wtiayhgrtej44jk foreign key (work_id) references work_interests
alter table personal_work_interests add constraint FKmvcu7lm3bkjtxqio4mxm6vvgk foreign key (work_interests_id) references work_interests
alter table previous_work add constraint FKpvsnjfgwsdemynbhkmgugtsav foreign key (offender_id) references ciag_profile
alter table previous_work_detail add constraint FKn1rvhm5ygre2mwy4ec3dqq51n foreign key (work_id) references previous_work
alter table prison_education add constraint FKikvlacla8jpyftbfk7bh41k5u foreign key (prison_work_education_id) references prison_work_education
alter table prison_work add constraint FKhfpgubcyvui0y213gxw8kftao foreign key (prison_work_education_id) references prison_work_education
alter table prison_work_education add constraint FKa6h1es2i7gvcbjkdsv881txh2 foreign key (offender_id) references ciag_profile
alter table reason_not_to_get_work add constraint FKksqifpb4a4sug1j6dbhst4je2 foreign key (offender_id) references ciag_profile
alter table skills_work_interests add constraint FK8qab19avogu8km3vtjsdo248p foreign key (work_interests_id) references work_interests
alter table work_interests add constraint FKrmnhsbjm89vy0mapy52a1dksq foreign key (offender_id) references ciag_profile
alter table work_interests add constraint FK82fjw6u98xhc10d0s0i84m17x foreign key (work_id) references work_interests
