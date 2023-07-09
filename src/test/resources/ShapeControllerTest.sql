CREATE SCHEMA geometric_shapes;
create table circle
(
    id_shape         bigint auto_increment primary key,
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
    id_shape         bigint auto_increment primary key,
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
    id_shape         bigint auto_increment primary key,
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

insert into square (id_shape ,created_at, created_by, last_modified_at, last_modified_by, type, version, area, perimeter, width) values (1, '2023-07-01', 'roko', '2023-07-01', 'roko', 'SQUARE', 'v1', 100.0, 40.0, 10);
insert into square (id_shape ,created_at, created_by, last_modified_at, last_modified_by, type, version, area, perimeter, width) values (2, '2023-07-01', 'roko', '2023-07-01', 'roko', 'SQUARE', 'v1', 400.0, 80.0, 20);