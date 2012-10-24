create database if not exists readerfeeder;
drop table user;
drop table task;

create table user (
    id int not null auto_increment,
    name varchar(80) null,
    constraint pk_user primary key (id)
);

create table task (
    id int not null,
    name varchar(80) null,
    description varchar(300) null,
    points int null,
    code varchar(10) null,
    constraint pk_task primary key (id)
);
