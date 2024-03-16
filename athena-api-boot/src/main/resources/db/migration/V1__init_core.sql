CREATE SCHEMA athena_core;
create table athena_core.app_version
(
    id         bigserial   not null,
    project_id bigint      not null,
    code       varchar(10) not null unique,
    name       varchar(50) not null,
    primary key (id)
);
create table athena_core.environment
(
    id         bigserial   not null,
    project_id bigint      not null,
    code       varchar(10) not null unique,
    name       varchar(50) not null,
    primary key (id)
);
create table athena_core.project
(
    id   bigserial   not null,
    code varchar(10) not null unique,
    name varchar(50) not null,
    primary key (id)
);
create table athena_core.user
(
    id       bigserial    not null,
    username varchar(150) not null unique,
    primary key (id)
);
create table athena_core.user_alias
(
    id      bigserial    not null,
    user_id bigint       not null,
    alias   varchar(200) not null unique,
    primary key (id)
);

create index IDXeg9xtij8mvb1jio1602somk3j on athena_core.app_version (code);
create index IDXc1yi6kkb16pl3vg047wm1o204 on athena_core.environment (code);
create index IDXeh3nusutt0qy84a4yr9pfxkyg on athena_core.project (code);

alter table if exists athena_core.app_version
    add constraint FK5s3l6egklax4c0brnv7yp9cpt foreign key (project_id) references athena_core.project;
alter table if exists athena_core.environment
    add constraint FKe5x1obcek8qq6nlo6p9gn6v0i foreign key (project_id) references athena_core.project;
alter table if exists athena_core.user_alias
    add constraint FKhx1ayjnubge656u10ug92nkvy foreign key (user_id) references athena_core.user;
