CREATE SCHEMA athena_core;
CREATE SCHEMA athena_pipeline;
CREATE SCHEMA athena_openapi;
CREATE SCHEMA athena_tms;
CREATE SCHEMA athena_kube;
CREATE SCHEMA athena_git;
CREATE SCHEMA athena_metric;
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
create table athena_git.commit
(
    line_deleted  integer,
    line_inserted integer,
    parent_count  integer       not null,
    total_file    integer,
    author_id     bigint        not null,
    commit_time   TIMESTAMPTZ   not null,
    committer_id  bigint        not null,
    id            bigserial     not null,
    repository_id bigint        not null,
    hash          varchar(50)   not null unique,
    parent_hash   varchar(50),
    short_message varchar(5000) not null,
    primary key (id)
);
create table athena_git.commit_metadata
(
    id    bigserial     not null,
    name  varchar(300)  not null,
    value varchar(1000) not null,
    primary key (id)
);
create table athena_git.commit_metadata_mid
(
    commit_id   bigint not null,
    metadata_id bigint not null,
    primary key (commit_id, metadata_id)
);
create table athena_git.commit_tag_mid
(
    commit_id bigint not null,
    tag_id    bigint not null,
    primary key (commit_id, tag_id)
);
create table athena_git.diff_entry
(
    deleted     integer       not null,
    inserted    integer       not null,
    commit_id   bigint        not null,
    id          bigserial     not null,
    change_type varchar(30)   not null,
    new         varchar(1000) not null,
    old         varchar(1000) not null,
    primary key (id)
);
create table athena_git.repository
(
    id        bigserial    not null,
    last_sync TIMESTAMPTZ,
    name      varchar(200) not null unique,
    url       varchar(300) not null unique,
    primary key (id)
);
create table athena_git.tag
(
    id   bigserial    not null,
    hash varchar(50)  not null,
    name varchar(200) not null,
    primary key (id)
);
create table athena_kube.container
(
    ready         boolean,
    restart_count integer,
    started       boolean,
    id            bigserial     not null,
    last_sync     TIMESTAMPTZ   not null,
    pod_id        bigint        not null,
    started_at    TIMESTAMPTZ,
    type          varchar(100)  not null,
    image_id      varchar(300)  not null,
    name          varchar(300)  not null,
    image         varchar(1000) not null,
    primary key (id)
);
create table athena_kube.container_metadata
(
    id    bigserial     not null,
    name  varchar(300)  not null,
    value varchar(1000) not null,
    primary key (id),
    constraint UniquePodContainerMetadataNameValue unique (name, value)
);
create table athena_kube.container_metadata_mid
(
    container_id bigint not null,
    metadata_id  bigint not null,
    primary key (container_id, metadata_id)
);
create table athena_kube.pod
(
    created_at TIMESTAMPTZ,
    deleted_at TIMESTAMPTZ,
    id         bigserial   not null,
    last_sync  TIMESTAMPTZ not null,
    project_id bigint      not null,
    status_id  bigint      not null,
    uid        varchar(36) unique,
    namespace  varchar(100),
    hostname   varchar(200),
    node_name  varchar(200),
    name       varchar(500) unique,
    primary key (id)
);
create table athena_kube.pod_annotation
(
    id    bigserial     not null,
    name  varchar(300)  not null,
    value varchar(1000) not null,
    primary key (id),
    constraint UniquePodAnnotationNameValue unique (name, value)
);
create table athena_kube.pod_annotation_mid
(
    annotation_id bigint not null,
    pod_id        bigint not null,
    primary key (annotation_id, pod_id)
);
create table athena_kube.pod_label
(
    id    bigserial     not null,
    name  varchar(300)  not null,
    value varchar(1000) not null,
    primary key (id),
    constraint UniquePodLabelNameValue unique (name, value)
);
create table athena_kube.pod_label_mid
(
    label_id bigint not null,
    pod_id   bigint not null,
    primary key (label_id, pod_id)
);
create table athena_kube.pod_metadata
(
    id    bigserial     not null,
    name  varchar(300)  not null,
    value varchar(1000) not null,
    primary key (id),
    constraint UniquePodMetadataNameValue unique (name, value)
);
create table athena_kube.pod_metadata_mid
(
    metadata_id bigint not null,
    pod_id      bigint not null,
    primary key (metadata_id, pod_id)
);
create table athena_kube.pod_selector
(
    id    bigserial     not null,
    name  varchar(300)  not null,
    value varchar(1000) not null,
    primary key (id),
    constraint UniquePodSelectorNameValue unique (name, value)
);
create table athena_kube.pod_selector_mid
(
    pod_id      bigint not null,
    selector_id bigint not null,
    primary key (pod_id, selector_id)
);
create table athena_kube.pod_status
(
    id      bigserial not null,
    name    varchar(200),
    phase   varchar(200),
    message varchar(1000),
    reason  varchar(1000),
    primary key (id),
    constraint UniquePodStatus unique (name, phase, message, reason)
);
create table athena_openapi.api_path
(
    first_time_seen TIMESTAMPTZ,
    id              bigserial    not null,
    last_sync_time  TIMESTAMPTZ,
    spec_id         bigint       not null,
    method          varchar(10)  not null,
    url             varchar(500) not null,
    title           varchar(1000),
    description     varchar(5000),
    parameters      jsonb,
    primary key (id)
);
create table athena_openapi.api_path_metadata
(
    id    bigserial     not null,
    name  varchar(100)  not null,
    value varchar(2000) not null,
    primary key (id)
);
create table athena_openapi.api_spec
(
    first_time_seen TIMESTAMPTZ,
    id              bigserial    not null,
    last_sync_time  TIMESTAMPTZ,
    project_id      bigint       not null,
    version         varchar(10)  not null,
    name            varchar(100) not null,
    title           varchar(100) not null,
    primary key (id)
);
create table athena_openapi.api_spec_metadata
(
    id    bigserial     not null,
    name  varchar(100)  not null,
    value varchar(2000) not null,
    primary key (id)
);
create table athena_openapi.api_spec_metadata_mid
(
    metadata_id bigint not null,
    spec_id     bigint not null,
    primary key (metadata_id, spec_id)
);
create table athena_openapi.path_metadata_mid
(
    metadata_id bigint not null,
    path_id     bigint not null,
    primary key (metadata_id, path_id)
);
create table athena_pipeline.execution
(
    before_class_end_time    TIMESTAMPTZ,
    before_class_start_time  TIMESTAMPTZ,
    before_method_end_time   TIMESTAMPTZ,
    before_method_start_time TIMESTAMPTZ,
    end_time                 TIMESTAMPTZ  not null,
    executor_id              bigint       not null,
    id                       bigserial    not null,
    pipeline_id              bigint       not null,
    start_time               TIMESTAMPTZ  not null,
    status_id                bigint       not null,
    test_end_time            TIMESTAMPTZ,
    test_start_time          TIMESTAMPTZ,
    class_name               varchar(300) not null,
    method_name              varchar(300) not null,
    package_name             varchar(300) not null,
    parameters               varchar(2000),
    primary key (id)
);
create table athena_pipeline.execution_metadata
(
    id    bigserial     not null,
    name  varchar(100)  not null,
    value varchar(2000) not null,
    primary key (id)
);
create table athena_pipeline.execution_metadata_mid
(
    execution_id bigint not null,
    metadata_id  bigint not null,
    primary key (execution_id, metadata_id)
);
create table athena_pipeline.pipeline
(
    end_date       TIMESTAMPTZ,
    environment_id bigint       not null,
    id             bigserial    not null,
    start_date     TIMESTAMPTZ  not null,
    version_id     bigint       not null,
    name           varchar(100) not null,
    number         varchar(100) not null,
    description    varchar(300) not null,
    primary key (id)
);
create table athena_pipeline.pipeline_metadata
(
    id    bigserial     not null,
    name  varchar(100)  not null,
    value varchar(2000) not null,
    primary key (id)
);
create table athena_pipeline.pipeline_metadata_mid
(
    metadata_id bigint not null,
    pipeline_id bigint not null,
    primary key (metadata_id, pipeline_id)
);
create table athena_pipeline.scenario_execution
(
    before_scenario_end_time   TIMESTAMPTZ,
    before_scenario_start_time TIMESTAMPTZ,
    end_time                   TIMESTAMPTZ   not null,
    executor_id                bigint        not null,
    id                         bigserial     not null,
    pipeline_id                bigint        not null,
    start_time                 TIMESTAMPTZ   not null,
    status_id                  bigint        not null,
    scenario                   varchar(500)  not null,
    feature                    varchar(1000) not null,
    parameters                 varchar(2000),
    primary key (id)
);
create table athena_pipeline.scenario_metadata_mid
(
    execution_id bigint not null,
    metadata_id  bigint not null,
    primary key (execution_id, metadata_id)
);
create table athena_pipeline.status
(
    id   bigserial    not null,
    name varchar(100) not null unique,
    primary key (id)
);
create table athena_tms.cycle
(
    end_date   TIMESTAMPTZ,
    id         bigserial    not null,
    start_date TIMESTAMPTZ  not null,
    version_id bigint,
    code       varchar(10)  not null unique,
    name       varchar(300) not null,
    primary key (id)
);
create table athena_tms.execution
(
    created     TIMESTAMPTZ not null,
    cycle_id    bigint      not null,
    executed    TIMESTAMPTZ,
    executor_id bigint,
    id          bigserial   not null,
    item_id     bigint      not null,
    status_id   bigint      not null,
    primary key (id)
);
create table athena_tms.item
(
    created_by  bigint       not null,
    created_on  TIMESTAMPTZ  not null,
    id          bigserial    not null,
    priority_id bigint       not null,
    project_id  bigint       not null,
    status_id   bigint       not null,
    type_id     bigint       not null,
    updated_by  bigint,
    updated_on  TIMESTAMPTZ,
    code        varchar(15)  not null unique,
    name        varchar(300) not null,
    primary key (id)
);
create table athena_tms.item_metadata_mid
(
    item_id     bigint not null,
    metadata_id bigint not null,
    primary key (item_id, metadata_id)
);
create table athena_tms.item_version_mid
(
    item_id    bigint not null,
    version_id bigint not null,
    primary key (item_id, version_id)
);
create table athena_tms.metadata
(
    id    bigserial     not null,
    name  varchar(100)  not null,
    value varchar(2000) not null,
    primary key (id)
);
create table athena_tms.priority
(
    id   bigserial   not null,
    code varchar(10) not null unique,
    name varchar(50) not null,
    primary key (id)
);
create table athena_tms.status
(
    id   bigserial   not null,
    code varchar(10) not null unique,
    name varchar(50) not null,
    primary key (id)
);
create table athena_tms.status_transition
(
    author      bigint    not null,
    from_status bigint    not null,
    id          bigserial not null,
    item_id     bigint    not null,
    occurred    TIMESTAMPTZ,
    to_status   bigint    not null,
    primary key (id)
);
create table athena_tms.sync_info
(
    end_time   TIMESTAMPTZ  not null,
    id         bigserial    not null,
    project_id bigint       not null,
    start_time TIMESTAMPTZ  not null,
    action     varchar(50)  not null,
    component  varchar(100) not null,
    primary key (id)
);
create table athena_tms.type
(
    id   bigserial   not null,
    code varchar(10) not null unique,
    name varchar(50) not null,
    primary key (id)
);
create index IDXk43tb8sntc046yxf4suq076os on athena_tms.cycle (code);
create index IDXynam1aocxtr559vsbvmxpaaa on athena_tms.execution (created, cycle_id, item_id);
create index IDX6cgogdarkq48dlg1lbnv4q1oq on athena_tms.item (code);
create index IDX639erqxmw75u6kt3lfksougmb on athena_tms.priority (code);
create index IDX90n0sv25slo1kmu0tcakhjjed on athena_tms.status (code);
create index IDX6phb53alqcmb7j69am501p02w on athena_tms.type (code);
alter table if exists athena_core.app_version
    add constraint FK5s3l6egklax4c0brnv7yp9cpt foreign key (project_id) references athena_core.project;
alter table if exists athena_core.environment
    add constraint FKe5x1obcek8qq6nlo6p9gn6v0i foreign key (project_id) references athena_core.project;
alter table if exists athena_core.user_alias
    add constraint FKhx1ayjnubge656u10ug92nkvy foreign key (user_id) references athena_core.user;
alter table if exists athena_git.commit
    add constraint FK4c4goeh7k2c5kyso5kteinmrd foreign key (author_id) references athena_core.user;
alter table if exists athena_git.commit
    add constraint FKn7wosmmq4lrqcmm1j3qo8v25n foreign key (committer_id) references athena_core.user;
alter table if exists athena_git.commit
    add constraint FKgqmkfk1wovdbkmbanfrrxc9pp foreign key (repository_id) references athena_git.repository;
alter table if exists athena_git.commit_metadata_mid
    add constraint FKqixdsmsc3kmtb76wmrlso7bak foreign key (metadata_id) references athena_git.commit_metadata;
alter table if exists athena_git.commit_metadata_mid
    add constraint FK33sf8jmbyi046k667sfbeyje0 foreign key (commit_id) references athena_git.commit;
alter table if exists athena_git.commit_tag_mid
    add constraint FKr3nql3m52idajoutdb4y4bv8r foreign key (tag_id) references athena_git.tag;
alter table if exists athena_git.commit_tag_mid
    add constraint FKmgcmygnf4hdijqi9eulnuo68y foreign key (commit_id) references athena_git.commit;
alter table if exists athena_git.diff_entry
    add constraint FK3xg0l50m09tshdlxhluc74811 foreign key (commit_id) references athena_git.commit;
alter table if exists athena_kube.container
    add constraint FKidh5jk8n2x46l91nky124f39s foreign key (pod_id) references athena_kube.pod;
alter table if exists athena_kube.container_metadata_mid
    add constraint FK10djf8c16osesuevhauhvdgim foreign key (metadata_id) references athena_kube.container_metadata;
alter table if exists athena_kube.container_metadata_mid
    add constraint FKhv0j50ewf7u0uryjryeauc4cn foreign key (container_id) references athena_kube.container;
alter table if exists athena_kube.pod
    add constraint FKpqmjd8srmogc90vxm50sdsed9 foreign key (project_id) references athena_core.project;
alter table if exists athena_kube.pod
    add constraint FKr3p5honftoe7g7fpgabvthmke foreign key (status_id) references athena_kube.pod_status;
alter table if exists athena_kube.pod_annotation_mid
    add constraint FK13s5ctig92ec26h8n6ba1ixxe foreign key (annotation_id) references athena_kube.pod_annotation;
alter table if exists athena_kube.pod_annotation_mid
    add constraint FKbsdp5kgjsgktynla364170iok foreign key (pod_id) references athena_kube.pod;
alter table if exists athena_kube.pod_label_mid
    add constraint FKpwbwcs37hrmr2a7n6its4ber0 foreign key (label_id) references athena_kube.pod_label;
alter table if exists athena_kube.pod_label_mid
    add constraint FKq3cf8vm17h8stqg7j6chwfw25 foreign key (pod_id) references athena_kube.pod;
alter table if exists athena_kube.pod_metadata_mid
    add constraint FK8cy7fosd0on1gbkv0pabjabn1 foreign key (metadata_id) references athena_kube.pod_metadata;
alter table if exists athena_kube.pod_metadata_mid
    add constraint FKbli9a215pefyl6ggyjsvpitid foreign key (pod_id) references athena_kube.pod;
alter table if exists athena_kube.pod_selector_mid
    add constraint FKo5hm5rb7fmfxo7qfvw5kaqqbf foreign key (selector_id) references athena_kube.pod_selector;
alter table if exists athena_kube.pod_selector_mid
    add constraint FKjdcsuuueuitqspc96yi3ms00p foreign key (pod_id) references athena_kube.pod;
alter table if exists athena_openapi.api_path
    add constraint FKoud6vm5jg0hfqqp71xsbw5i4i foreign key (spec_id) references athena_openapi.api_spec;
alter table if exists athena_openapi.api_spec
    add constraint FKcqjl4vog4e9p2efovv4syy5dw foreign key (project_id) references athena_core.project;
alter table if exists athena_openapi.api_spec_metadata_mid
    add constraint FKa7fg5ffcjf1wiao3wffe67c01 foreign key (metadata_id) references athena_openapi.api_spec_metadata;
alter table if exists athena_openapi.api_spec_metadata_mid
    add constraint FK9s99xhb1cqcyh67q0w024fgvk foreign key (spec_id) references athena_openapi.api_spec;
alter table if exists athena_openapi.path_metadata_mid
    add constraint FKg09nlhpnkuyb1bl6uk4vabr57 foreign key (metadata_id) references athena_openapi.api_path_metadata;
alter table if exists athena_openapi.path_metadata_mid
    add constraint FK6xdyw758at5oiivp5eemduw9h foreign key (path_id) references athena_openapi.api_path;
alter table if exists athena_pipeline.execution
    add constraint FKsxj934bma70rv4nxhl9jks1tg foreign key (executor_id) references athena_core.user;
alter table if exists athena_pipeline.execution
    add constraint FK2a1eitje846p27egcbpnpu216 foreign key (pipeline_id) references athena_pipeline.pipeline;
alter table if exists athena_pipeline.execution
    add constraint FKl5b63e2atpci888j3vrih9fgk foreign key (status_id) references athena_pipeline.status;
alter table if exists athena_pipeline.execution_metadata_mid
    add constraint FKickcqlum2vg9let6scpuwkr3b foreign key (metadata_id) references athena_pipeline.execution_metadata;
alter table if exists athena_pipeline.execution_metadata_mid
    add constraint FKm9u1oqvmevg9d5qy25mi8h10x foreign key (execution_id) references athena_pipeline.execution;
alter table if exists athena_pipeline.pipeline
    add constraint FK50lhemdi8a649a2wukj2ev0g5 foreign key (environment_id) references athena_core.environment;
alter table if exists athena_pipeline.pipeline
    add constraint FKpqnqm8tes84nhowr2ryitq258 foreign key (version_id) references athena_core.app_version;
alter table if exists athena_pipeline.pipeline_metadata_mid
    add constraint FKcs5r08ptgvp6eijpulce3dsm0 foreign key (metadata_id) references athena_pipeline.pipeline_metadata;
alter table if exists athena_pipeline.pipeline_metadata_mid
    add constraint FKg07aj55k425j3nwva53a6a2pd foreign key (pipeline_id) references athena_pipeline.pipeline;
alter table if exists athena_pipeline.scenario_execution
    add constraint FKgytyony4rytg2x9sac7bppbij foreign key (executor_id) references athena_core.user;
alter table if exists athena_pipeline.scenario_execution
    add constraint FK5vc883jpgoqbpeqk0pxyvthor foreign key (pipeline_id) references athena_pipeline.pipeline;
alter table if exists athena_pipeline.scenario_execution
    add constraint FKdwx5wr1r46p3dd78c9pbp8pbx foreign key (status_id) references athena_pipeline.status;
alter table if exists athena_pipeline.scenario_metadata_mid
    add constraint FKohg9ibf8as6d3gnd3yqg0hu0f foreign key (metadata_id) references athena_pipeline.execution_metadata;
alter table if exists athena_pipeline.scenario_metadata_mid
    add constraint FKacptnescmpf3du47da8ihq9ps foreign key (execution_id) references athena_pipeline.scenario_execution;
alter table if exists athena_tms.cycle
    add constraint FKcd7m2868v4ponviyv5olrew1s foreign key (version_id) references athena_core.app_version;
alter table if exists athena_tms.execution
    add constraint FKpp38bat6onygn2egn4qvy6isw foreign key (cycle_id) references athena_tms.cycle;
alter table if exists athena_tms.execution
    add constraint FKsxj934bma70rv4nxhl9jks1tg foreign key (executor_id) references athena_core.user;
alter table if exists athena_tms.execution
    add constraint FKf3j4r2wk0rnpm1yw1tfhgh554 foreign key (item_id) references athena_tms.item;
alter table if exists athena_tms.execution
    add constraint FKl5b63e2atpci888j3vrih9fgk foreign key (status_id) references athena_tms.status;
alter table if exists athena_tms.item
    add constraint FKeybj7ikncx4nkfgrb5i1uadq foreign key (created_by) references athena_core.user;
alter table if exists athena_tms.item
    add constraint FKlgtnomwfllo9wtrosb0qoffh5 foreign key (priority_id) references athena_tms.priority;
alter table if exists athena_tms.item
    add constraint FKf60hnjyqgladtp0jw5o0n4e9u foreign key (project_id) references athena_core.project;
alter table if exists athena_tms.item
    add constraint FKa5r9sug9g4f4kif98va9ro3l2 foreign key (status_id) references athena_tms.status;
alter table if exists athena_tms.item
    add constraint FK95i2undeuwt7lb0uy1md9wkp9 foreign key (type_id) references athena_tms.type;
alter table if exists athena_tms.item
    add constraint FKpcquid9hq5dwxrsg5lncnl1rt foreign key (updated_by) references athena_core.user;
alter table if exists athena_tms.item_metadata_mid
    add constraint FKkxa4n4m61qm69fwj6f0udupkn foreign key (metadata_id) references athena_tms.metadata;
alter table if exists athena_tms.item_metadata_mid
    add constraint FKenckba4dsvmvpdnc26iqrntil foreign key (item_id) references athena_tms.item;
alter table if exists athena_tms.item_version_mid
    add constraint FKtjjhljwefqu2xoqxaoxiv6jwt foreign key (version_id) references athena_core.app_version;
alter table if exists athena_tms.item_version_mid
    add constraint FKkqgy2yskehpg6yftnf6t3gssd foreign key (item_id) references athena_tms.item;
alter table if exists athena_tms.status_transition
    add constraint FKm8e9n1uv6mwl0c72e3bsxu46o foreign key (author) references athena_core.user;
alter table if exists athena_tms.status_transition
    add constraint FKqx786lpsnejbs8luvrii363xq foreign key (from_status) references athena_tms.status;
alter table if exists athena_tms.status_transition
    add constraint FK3ffxqq0j5njpl8ulantbb3p9 foreign key (item_id) references athena_tms.item;
alter table if exists athena_tms.status_transition
    add constraint FK9tb7aqy88ijc3g9rfcxs5nkdf foreign key (to_status) references athena_tms.status;
alter table if exists athena_tms.sync_info
    add constraint FKnnvsxyu0mqffcgs3lbxhloite foreign key (project_id) references athena_core.project;
