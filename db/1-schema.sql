create schema LIST;
create schema MD;

create table LIST.GROUP
(
    ID   BIGSERIAL primary key,
    NAME varchar not null,
    CODE varchar not null
);

create table LIST.TRAINING_STATUS
(
    ID   BIGSERIAL primary key,
    NAME varchar not null,
    CODE varchar not null
);

create table LIST.TRAINING_TYPE
(
    ID   BIGSERIAL primary key,
    NAME varchar not null,
    CODE varchar not null
);

create table MD.USER
(
    ID         BIGSERIAL primary key,
    NAME       varchar not null,
    PASSWORD   varchar not null,
    IMAGE      bytea,
    DATE_BIRTH date    not null,
    ID_GROUP   int,
    foreign key (ID_GROUP) references LIST.GROUP (ID)
);

create table MD.TRAINING
(
    ID            BIGSERIAL primary key,
    DATE_TRAINING date not null,
    PRICE         bigint,
    ID_TRAINER    int,
    ID_CLIENT     int,
    ID_STATUS     int,
    ID_TYPE       int,
    foreign key (ID_TRAINER) references MD.USER (ID),
    foreign key (ID_CLIENT) references MD.USER (ID),
    foreign key (ID_STATUS) references LIST.TRAINING_STATUS (ID),
    foreign key (ID_TYPE) references LIST.TRAINING_TYPE (ID)
);

create table MD.ROLE
(
    ID      BIGSERIAL primary key,
    NAME    varchar not null,
    CODE    varchar not null,
    ID_USER int,
    foreign key (ID_USER) references MD.USER (ID)
);

create table LIST.SUBSCRIPTION_TYPE
(
    ID    BIGSERIAL primary key,
    NAME  varchar not null,
    CODE  varchar not null,
    PRICE bigint
);

create table MD.SUBSCRIPTION
(
    ID                   BIGSERIAL primary key,
    NAME                 varchar not null,
    ID_USER              int,
    DATE_FROM            date    not null,
    DATE_TO              date    not null,
    PRICE                bigint,
    ID_SUBSCRIPTION_TYPE int,
    foreign key (ID_USER) references MD.USER (ID),
    foreign key (ID_SUBSCRIPTION_TYPE) references LIST.SUBSCRIPTION_TYPE (ID)
);

create table MD.TOKEN
(
    VALUE varchar primary key
);