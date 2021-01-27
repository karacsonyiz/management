create table users
(
    userid   BIGINT auto_increment primary key,
    name     varchar(255),
    password varchar(255),
    email    varchar(255),
    phone    varchar(12),
    address  varchar(255),
    enabled  int,
    role     varchar(255)
);

insert into users(name, password, email, phone, address, enabled, role)
values ('admin', '$2a$10$FV9w7BuvK2gmnVPaUqQ88.d9V2C73VyTvc24DrqHZJXTGnQV5xtl.', 'admin@admin.com', '061555444',
        'Bajza utca 33', 1, 'ROLE_ADMIN'),
       ('user', '$2a$10$v8PdVYFQ/Btnd1RpicvfR.sA/v0TvNoJogNsULJqRvAWXoCCM50xu', 'user@user.com', '061999111',
        'Malom utca 2', 1, 'ROLE_USER');