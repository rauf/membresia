# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table installment (
  id                        bigserial not null,
  due_date                  timestamp,
  amount                    Decimal(10,2) default '0.00',
  token                     varchar(255),
  subscription_id           bigint,
  created_at                timestamp not null,
  updated_at                timestamp not null,
  constraint uq_installment_token unique (token),
  constraint pk_installment primary key (id))
;

create table member_installment (
  id                        bigserial not null,
  is_paid                   boolean,
  token                     varchar(255),
  member_id                 bigint,
  installment_id            bigint,
  constraint uq_member_installment_token unique (token),
  constraint pk_member_installment primary key (id))
;

create table messageTemplate (
  id                        bigserial not null,
  title                     varchar(255),
  body                      TEXT,
  token                     varchar(255),
  created_at                timestamp not null,
  updated_at                timestamp not null,
  constraint uq_messageTemplate_token unique (token),
  constraint pk_messageTemplate primary key (id))
;

create table payment (
  id                        bigserial not null,
  amount                    float,
  token                     varchar(255),
  status                    integer,
  installment_id            bigint,
  member_installment_id     bigint,
  created_at                timestamp not null,
  constraint uq_payment_token unique (token),
  constraint pk_payment primary key (id))
;

create table subscription (
  id                        bigserial not null,
  subscription_id           varchar(255),
  title                     varchar(255),
  description               TEXT,
  amount                    Decimal(10,2) default '0.00',
  periodicity               varchar(255),
  token                     varchar(255),
  due_date_period           timestamp,
  created_at                timestamp not null,
  updated_at                timestamp not null,
  constraint uq_subscription_subscription_id unique (subscription_id),
  constraint pk_subscription primary key (id))
;

create table userPerson (
  user_type                 varchar(31) not null,
  id                        bigserial not null,
  email                     varchar(255),
  password                  varchar(255),
  name                      varchar(255),
  last_name                 varchar(255),
  token                     varchar(255),
  status                    boolean,
  created_at                timestamp not null,
  updated_at                timestamp not null,
  member_id                 varchar(255),
  address                   varchar(255),
  cp                        varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  country                   varchar(255),
  nif                       varchar(255),
  phone                     varchar(255),
  constraint uq_userPerson_email unique (email),
  constraint uq_userPerson_token unique (token),
  constraint uq_userPerson_member_id unique (member_id),
  constraint pk_userPerson primary key (id))
;


create table userPerson_subscription (
  userPerson_id                  bigint not null,
  subscription_id                bigint not null,
  constraint pk_userPerson_subscription primary key (userPerson_id, subscription_id))
;
alter table installment add constraint fk_installment_subscription_1 foreign key (subscription_id) references subscription (id);
create index ix_installment_subscription_1 on installment (subscription_id);
alter table member_installment add constraint fk_member_installment_member_2 foreign key (member_id) references userPerson (id);
create index ix_member_installment_member_2 on member_installment (member_id);
alter table member_installment add constraint fk_member_installment_installm_3 foreign key (installment_id) references installment (id);
create index ix_member_installment_installm_3 on member_installment (installment_id);
alter table payment add constraint fk_payment_installment_4 foreign key (installment_id) references installment (id);
create index ix_payment_installment_4 on payment (installment_id);
alter table payment add constraint fk_payment_memberInstallment_5 foreign key (member_installment_id) references member_installment (id);
create index ix_payment_memberInstallment_5 on payment (member_installment_id);



alter table userPerson_subscription add constraint fk_userPerson_subscription_us_01 foreign key (userPerson_id) references userPerson (id);

alter table userPerson_subscription add constraint fk_userPerson_subscription_su_02 foreign key (subscription_id) references subscription (id);

# --- !Downs

drop table if exists installment cascade;

drop table if exists member_installment cascade;

drop table if exists messageTemplate cascade;

drop table if exists payment cascade;

drop table if exists subscription cascade;

drop table if exists userPerson_subscription cascade;

drop table if exists userPerson cascade;

