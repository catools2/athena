create table athena_metric.action (id bigserial not null, category varchar(100) not null, name varchar(100) not null, type varchar(100) not null, target varchar(1000) not null, command varchar(5000) not null, parameter varchar(5000), primary key (id));
create table athena_metric.metric (action_id bigint not null, action_time TIMESTAMPTZ not null, duration bigint, environment_id bigint not null, id bigserial not null, project_id bigint not null, primary key (id));
create index IDXadh6fulxe89os666w4l6aeepb on athena_metric.action (name, type, target, command);
alter table if exists athena_metric.metric add constraint FK8a8ran7knaybt9c14hepi65ac foreign key (action_id) references athena_metric.action;
alter table if exists athena_metric.metric add constraint FKciwet60iy67c7vhy09el3ujn0 foreign key (environment_id) references athena_core.environment;
alter table if exists athena_metric.metric add constraint FKj5lmuglw2ayciv12xr5ocas92 foreign key (project_id) references athena_core.project;
