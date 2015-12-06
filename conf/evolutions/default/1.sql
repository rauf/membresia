# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table installment (
  id                        bigint auto_increment not null,
  due_date                  datetime(6),
  amount                    Decimal(10,2) default '0.00',
  token                     varchar(255),
  subscription_id           bigint,
  created_at                datetime(6) not null,
  updated_at                datetime(6) not null,
  constraint uq_installment_token unique (token),
  constraint pk_installment primary key (id))
;

create table messageTemplate (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  body                      TEXT,
  token                     varchar(255),
  created_at                datetime(6) not null,
  updated_at                datetime(6) not null,
  constraint uq_messageTemplate_token unique (token),
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
  subscription_id           varchar(255),
  title                     varchar(255),
  description               varchar(255),
  amount                    Decimal(10,2) default '0.00',
  periodicity               varchar(255),
  token                     varchar(255),
  due_date_period           datetime(6),
  created_at                datetime(6) not null,
  updated_at                datetime(6) not null,
  constraint uq_subscrition_subscription_id unique (subscription_id),
  constraint pk_subscrition primary key (id))
;

create table user (
  user_type                 varchar(31) not null,
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  last_name                 varchar(255),
  token                     varchar(255),
  created_at                datetime(6) not null,
  updated_at                datetime(6) not null,
  password                  varchar(255),
  password_raw              varchar(255),
  member_id                 varchar(255),
  address                   varchar(255),
  cp                        varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  country                   varchar(255),
  nif                       varchar(255),
  phone                     varchar(255),
  constraint uq_user_email unique (email),
  constraint uq_user_token unique (token),
  constraint uq_user_member_id unique (member_id),
  constraint pk_user primary key (id))
;


create table user_subscrition (
  user_id                        bigint not null,
  subscrition_id                 bigint not null,
  constraint pk_user_subscrition primary key (user_id, subscrition_id))
;
alter table installment add constraint fk_installment_subscription_1 foreign key (subscription_id) references subscrition (id) on delete restrict on update restrict;
create index ix_installment_subscription_1 on installment (subscription_id);
alter table payment add constraint fk_payment_installment_2 foreign key (installment_id) references installment (id) on delete restrict on update restrict;
create index ix_payment_installment_2 on payment (installment_id);
alter table payment add constraint fk_payment_member_3 foreign key (member_id) references user (id) on delete restrict on update restrict;
create index ix_payment_member_3 on payment (member_id);



alter table user_subscrition add constraint fk_user_subscrition_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_subscrition add constraint fk_user_subscrition_subscrition_02 foreign key (subscrition_id) references subscrition (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table installment;

drop table messageTemplate;

drop table payment;

drop table subscrition;

drop table user_subscrition;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

