# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table adminUser (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  password                  varchar(255),
  name                      varchar(255),
  last_name                 varchar(255),
  token                     varchar(255),
  password_raw              varchar(255),
  created_at                datetime(6) not null,
  updated_at                datetime(6) not null,
  constraint uq_adminUser_email unique (email),
  constraint uq_adminUser_token unique (token),
  constraint pk_adminUser primary key (id))
;

create table installment (
  id                        bigint auto_increment not null,
  due_date                  datetime(6),
  subscription_id           bigint,
  created_at                datetime(6) not null,
  updated_at                datetime(6) not null,
  constraint pk_installment primary key (id))
;

create table member (
  id                        bigint auto_increment not null,
  member_id                 varchar(255),
  email                     varchar(255),
  name                      varchar(255),
  last_name                 varchar(255),
  token                     varchar(255),
  address                   varchar(255),
  cp                        varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  country                   varchar(255),
  nif                       varchar(255),
  phone                     varchar(255),
  created_at                datetime(6) not null,
  updated_at                datetime(6) not null,
  constraint uq_member_member_id unique (member_id),
  constraint uq_member_email unique (email),
  constraint uq_member_token unique (token),
  constraint pk_member primary key (id))
;

create table messageTemplate (
  id                        bigint auto_increment not null,
  title                     float,
  body                      float,
  created_at                datetime(6) not null,
  updated_at                datetime(6) not null,
  constraint pk_messageTemplate primary key (id))
;

create table payment (
  id                        bigint auto_increment not null,
  amount                    float,
  status                    integer,
  installment_id            bigint,
  member_id                 bigint,
  created_at                datetime(6) not null,
  constraint pk_payment primary key (id))
;

create table subscrition (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  description               varchar(255),
  amount                    float,
  periocity                 varchar(255),
  due_date_period           datetime(6),
  created_at                datetime(6) not null,
  updated_at                datetime(6) not null,
  constraint pk_subscrition primary key (id))
;


create table member_subscrition (
  member_id                      bigint not null,
  subscrition_id                 bigint not null,
  constraint pk_member_subscrition primary key (member_id, subscrition_id))
;
alter table installment add constraint fk_installment_subscription_1 foreign key (subscription_id) references subscrition (id) on delete restrict on update restrict;
create index ix_installment_subscription_1 on installment (subscription_id);
alter table payment add constraint fk_payment_installment_2 foreign key (installment_id) references installment (id) on delete restrict on update restrict;
create index ix_payment_installment_2 on payment (installment_id);
alter table payment add constraint fk_payment_member_3 foreign key (member_id) references member (id) on delete restrict on update restrict;
create index ix_payment_member_3 on payment (member_id);



alter table member_subscrition add constraint fk_member_subscrition_member_01 foreign key (member_id) references member (id) on delete restrict on update restrict;

alter table member_subscrition add constraint fk_member_subscrition_subscrition_02 foreign key (subscrition_id) references subscrition (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table adminUser;

drop table installment;

drop table member;

drop table member_subscrition;

drop table messageTemplate;

drop table payment;

drop table subscrition;

SET FOREIGN_KEY_CHECKS=1;

