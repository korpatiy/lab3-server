create table t_message
(
    message_id bigint generated by default as identity
        constraint t_message_pkey
            primary key,
    author     varchar(30)   not null,
    text       varchar(1000) not null,
    clap       integer       not null
);