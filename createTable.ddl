create table user
(
    user_id    bigint auto_increment
        primary key,
    created_at datetime     not null,
    updated_at datetime     not null,
    email      varchar(255) not null,
    password   varchar(255) not null,
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email)
);

create table account
(
    account_id bigint auto_increment
        primary key,
    created_at datetime     not null,
    updated_at datetime     not null,
    amount     int          not null,
    memo       varchar(255) not null,
    status     varchar(255) not null,
    user_id    bigint       null,
    constraint FK7m8ru44m93ukyb61dfxw0apf6
        foreign key (user_id) references user (user_id)
);

