
create table circle
(
    id_circle         bigint auto_increment primary key,
    type             varchar(255) not null,
    area             double       not null,
    created_at       date         null,
    created_by       varchar(255) null,
    last_modified_at date         null,
    last_modified_by varchar(255) null,
    perimeter        double       not null,
    version          varchar(255) null,
    radius           double       not null
);

create table rectangle
(
    id_rectangle         bigint auto_increment primary key,
    type             varchar(255) not null,
    area             double       not null,
    created_at       date         null,
    created_by       varchar(255) null,
    last_modified_at date         null,
    last_modified_by varchar(255) null,
    perimeter        double       not null,
    version          varchar(255) null,
    length           double       not null,
    width            double       not null
);

create table square
(
    id_square         bigint auto_increment primary key,
    type             varchar(255) not null,
    area             double       not null,
    created_at       date         null,
    created_by       varchar(255) null,
    last_modified_at date         null,
    last_modified_by varchar(255) null,
    perimeter        double       not null,
    version          varchar(255) null,
    width            double       not null
);