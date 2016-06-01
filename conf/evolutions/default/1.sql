# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table administrators (
  admin_id                      integer not null,
  login_id                      varchar(255),
  pass                          varchar(255),
  constraint pk_administrators primary key (admin_id)
);
create sequence administrators_seq;

create table case_study (
  case_id                       integer not null,
  year                          integer,
  month                         integer,
  constraint pk_case_study primary key (case_id)
);
create sequence case_study_seq;

create table category (
  category_id                   integer not null,
  category_name                 varchar(255),
  constraint pk_category primary key (category_id)
);
create sequence category_seq;

create table department (
  department_id                 integer not null,
  department_name               varchar(255),
  constraint pk_department primary key (department_id)
);
create sequence department_seq;

create table employees (
  employees_id                  integer not null,
  name                          varchar(255),
  department_id_department_id   integer,
  login_id                      varchar(255),
  pass                          varchar(255),
  permissions                   varchar(255),
  employees_cd                  varchar(255),
  del_flag                      integer,
  constraint pk_employees primary key (employees_id)
);
create sequence employees_seq;

create table gratitude_card (
  card_id                       integer not null,
  sender_id_employees_id        integer,
  receiver_id_employees_id      integer,
  date                          timestamp,
  card_title                    varchar(255),
  card_content                  varchar(255),
  category_id_category_id       integer,
  constraint pk_gratitude_card primary key (card_id)
);
create sequence gratitude_card_seq;

create table gratitude_card_case_study (
  gratitude_card_card_id        integer not null,
  case_study_case_id            integer not null,
  constraint pk_gratitude_card_case_study primary key (gratitude_card_card_id,case_study_case_id)
);

alter table employees add constraint fk_employees_department_id_department_id foreign key (department_id_department_id) references department (department_id) on delete restrict on update restrict;
create index ix_employees_department_id_department_id on employees (department_id_department_id);

alter table gratitude_card add constraint fk_gratitude_card_sender_id_employees_id foreign key (sender_id_employees_id) references employees (employees_id) on delete restrict on update restrict;
create index ix_gratitude_card_sender_id_employees_id on gratitude_card (sender_id_employees_id);

alter table gratitude_card add constraint fk_gratitude_card_receiver_id_employees_id foreign key (receiver_id_employees_id) references employees (employees_id) on delete restrict on update restrict;
create index ix_gratitude_card_receiver_id_employees_id on gratitude_card (receiver_id_employees_id);

alter table gratitude_card add constraint fk_gratitude_card_category_id_category_id foreign key (category_id_category_id) references category (category_id) on delete restrict on update restrict;
create index ix_gratitude_card_category_id_category_id on gratitude_card (category_id_category_id);

alter table gratitude_card_case_study add constraint fk_gratitude_card_case_study_gratitude_card foreign key (gratitude_card_card_id) references gratitude_card (card_id) on delete restrict on update restrict;
create index ix_gratitude_card_case_study_gratitude_card on gratitude_card_case_study (gratitude_card_card_id);

alter table gratitude_card_case_study add constraint fk_gratitude_card_case_study_case_study foreign key (case_study_case_id) references case_study (case_id) on delete restrict on update restrict;
create index ix_gratitude_card_case_study_case_study on gratitude_card_case_study (case_study_case_id);


# --- !Downs

alter table employees drop constraint if exists fk_employees_department_id_department_id;
drop index if exists ix_employees_department_id_department_id;

alter table gratitude_card drop constraint if exists fk_gratitude_card_sender_id_employees_id;
drop index if exists ix_gratitude_card_sender_id_employees_id;

alter table gratitude_card drop constraint if exists fk_gratitude_card_receiver_id_employees_id;
drop index if exists ix_gratitude_card_receiver_id_employees_id;

alter table gratitude_card drop constraint if exists fk_gratitude_card_category_id_category_id;
drop index if exists ix_gratitude_card_category_id_category_id;

alter table gratitude_card_case_study drop constraint if exists fk_gratitude_card_case_study_gratitude_card;
drop index if exists ix_gratitude_card_case_study_gratitude_card;

alter table gratitude_card_case_study drop constraint if exists fk_gratitude_card_case_study_case_study;
drop index if exists ix_gratitude_card_case_study_case_study;

drop table if exists administrators;
drop sequence if exists administrators_seq;

drop table if exists case_study;
drop sequence if exists case_study_seq;

drop table if exists category;
drop sequence if exists category_seq;

drop table if exists department;
drop sequence if exists department_seq;

drop table if exists employees;
drop sequence if exists employees_seq;

drop table if exists gratitude_card;
drop sequence if exists gratitude_card_seq;

drop table if exists gratitude_card_case_study;

