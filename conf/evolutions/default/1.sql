# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table feed (
  id                            varchar(40) not null,
  phone                         varchar(255),
  imei                          varchar(255),
  file                          varchar(255),
  added                         datetime(6),
  constraint pk_feed primary key (id)
);

create table user (
  email                         varchar(255) not null,
  password                      varchar(255),
  phone                         varchar(255),
  imei                          varchar(255),
  constraint pk_user primary key (email)
);


# --- !Downs

drop table if exists feed;

drop table if exists user;

