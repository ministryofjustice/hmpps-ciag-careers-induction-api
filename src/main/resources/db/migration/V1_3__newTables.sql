create table ability_to_work_impact (offender_id varchar(255) not null, work_impact integer);
create table achieved_qualification (work_interests_id bigint not null, grade varchar(255), level integer, subject varchar(255));
create table ciag_profile (offender_id varchar(255) not null, other_ability_to_work_impact varchar(255), created_by varchar(255), created_date_time timestamp, desire_to_work boolean, hoping_to_get_work integer, modified_by varchar(255), modified_date_time timestamp, reason_not_to_get_work integer, reason_to_not_get_work varchar(255), schema_version varchar(255), prison_work_and_education_id bigint, education_and_qualification_id bigint, skills_and_interests_id bigint, previous_work_id bigint, primary key (offender_id));
create table current_work_interests (id bigint not null, modified_by varchar(255), modified_date_time timestamp, other_work_interest varchar(255), primary key (id));
create table education_qualification (id bigint not null, other_qualification varchar(255), education_level integer, modified_by varchar(255), modified_date_time timestamp, primary key (id));
create table extra_qualification (work_interests_id bigint not null, qualification integer);
create table particular_work_interests (work_interests_id bigint not null, role varchar(255), work_interest integer);
create table personal_work_interests (work_interests_id bigint not null, personal_work_interest integer);
create table previous_work (id bigint not null, has_worked_before boolean not null, modified_by varchar(255), modified_date_time timestamp, work_experience_other varchar(255), work_interests_id bigint, primary key (id));
create table prison_education (prison_work_education_id bigint not null, education integer);
create table prison_work (prison_work_education_id bigint not null, work integer);
create table prison_work_education (id bigint not null, other_prison_education varchar(255), other_prison_work varchar(255), modified_by varchar(255), modified_date_time timestamp, primary key (id));
create table skills_and_interests (id bigint not null, modified_by varchar(255), modified_date_time timestamp, other_personal_intrests varchar(255), other_skill varchar(255), primary key (id));
create table skills_work_interests (work_interests_id bigint not null, skills integer);
create table work_experience (previous_work_id bigint not null, work_experience integer);
create table work_experience_detail (previous_work_id bigint not null, details varchar(255), other_work varchar(255), role varchar(255), type_of_work_experience integer);
create table work_interests (work_interests_id bigint not null, work_interests integer);
alter table ability_to_work_impact add constraint FKqwdh8an0f7tosgl2djp3eaq6a foreign key (offender_id) references ciag_profile;
alter table achieved_qualification add constraint FK9nsis7gdqyadskbcksm7ugskq foreign key (work_interests_id) references education_qualification;
alter table ciag_profile add constraint FKl2u6n3u2hvv8hcau0efg212hg foreign key (prison_work_and_education_id) references prison_work_education;
alter table ciag_profile add constraint FKawx49h9mopv8wrlpookycdk7f foreign key (education_and_qualification_id) references education_qualification;
alter table ciag_profile add constraint FKfgk6llvg4v3dqw5wd17cuklmg foreign key (skills_and_interests_id) references skills_and_interests;
alter table ciag_profile add constraint FKkq7lonsvshs4mpjp6hrp5bj6s foreign key (previous_work_id) references previous_work;
alter table extra_qualification add constraint FKrvutj3ivovixy37otbya4no63 foreign key (work_interests_id) references education_qualification;
alter table particular_work_interests add constraint FKpc8wtcaga5i9s9n2o0k7kbr4f foreign key (work_interests_id) references current_work_interests;
alter table personal_work_interests add constraint FK3i65cqp2morceh1rpc31tc52a foreign key (work_interests_id) references skills_and_interests;
alter table previous_work add constraint FK2fg36qrgi97gu5ggi4jgshaut foreign key (work_interests_id) references current_work_interests;
alter table prison_education add constraint FKikvlacla8jpyftbfk7bh41k5u foreign key (prison_work_education_id) references prison_work_education;
alter table prison_work add constraint FKhfpgubcyvui0y213gxw8kftao foreign key (prison_work_education_id) references prison_work_education;
alter table skills_work_interests add constraint FK29w5gtbsg7bf9wwh4iq6fci2u foreign key (work_interests_id) references skills_and_interests;
alter table work_experience add constraint FK3w4brs7x1okijbwi3tm5yk8gf foreign key (previous_work_id) references previous_work;
alter table work_experience_detail add constraint FKi1fbxy46f7pu36xphmn6sb8c4 foreign key (previous_work_id) references previous_work;
alter table work_interests add constraint FKe4iutmsleo15923jw8cxv6upo foreign key (work_interests_id) references current_work_interests;