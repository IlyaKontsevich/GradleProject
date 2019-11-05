create table IF NOT EXISTS info
(
    id       bigint unsigned auto_increment,
    user_url varchar(250)    null,
    task_url varchar(250)    null,
    user_id  bigint unsigned not null,
    constraint id
        unique (id)
);
alter table info
    add primary key (id);

create table IF NOT EXISTS tasks
(
    id       bigint unsigned auto_increment,
    name     varchar(250)    not null,
    deadLine date            not null,
    timeAdd  date            not null,
    isDone   tinyint(1)      not null,
    userId   bigint unsigned not null,
    priority varchar(250)    not null,
    constraint id
        unique (id),
    constraint tasksUser
        foreign key (userId) references users (id)
            on update cascade on delete cascade
);

alter table tasks
    add primary key (id);

create table IF NOT EXISTS  users
(
    id       bigint unsigned auto_increment,
    age      int          not null,
    email    varchar(250) not null,
    name     varchar(250) not null,
    password varchar(250) null,
    constraint id
        unique (id),
    constraint users_name_uindex
        unique (name)
);

alter table users
    add primary key (id);

INSERT INTO users (id, age, email, name, password) VALUES (2, 19, 'admin@mail.ru', 'Admin', '$2a$10$UjI43.BDAFxRrtYynmmVYOJXzoULn0n8RQiLyCnFfs0F1Le9V5TKa');
INSERT INTO users (id, age, email, name, password) VALUES (3, 12, 's@mail.ru', 'username', '$2a$10$A0IFoJ5lV.142pJcAeICBeO9vJE.dNU0CNCWHP3CRXhiKEET/jPf2');
INSERT INTO users (id, age, email, name, password) VALUES (4, 1, 's@mail.ru1', 'username1', '$2a$10$KAAmf/e37VZzOPTML3pv0.Nkt41Az76ehMU6jHbXOLu6hCsk6c./m');
INSERT INTO users (id, age, email, name, password) VALUES (6, 1, 's@mail.ru5', 'username', '$2a$10$y.mkwbJXX3ukn8Ytcdwy0.wPrLJgjRZ2fENOL189OmBhxDNYkiPuW');
INSERT INTO users (id, age, email, name, password) VALUES (7, 1, 's@mail.ru4', 'username', '$2a$10$Vh1OpdKVhAyPTSwTtKBliO1xGHDGD5ia.jl0W3CVEaSV6OlDgvRwK');
INSERT INTO users (id, age, email, name, password) VALUES (8, 1, 's@mail.ru45', 'username', '$2a$10$FMy0sTOe2rkS.rKuY2UF4e3qF534ViZNewHVDnqvhp0dFGpXU2zjm');
INSERT INTO users (id, age, email, name, password) VALUES (9, 1, 's@mail.ru67', 'username', '$2a$10$eAueu7M.WlqiNBdkTMPznONVHUArgxAzBq97Mc5kpOpbvriv2/m9q');
INSERT INTO users (id, age, email, name, password) VALUES (10, 1, 's@mail.ru9', 'username', '$2a$10$zzAiYcc31fEq1ibsiiIvx.3nRaMC0jik2alqKvv9kKi6FZYP6jUI.');

INSERT INTO LocalDB.info (id, user_url, task_url, user_id) VALUES (1, '?page=1&size=10&sort=name:asc', '?page=1&size=10&sort=name:asc', 2);
INSERT INTO LocalDB.info (id, user_url, task_url, user_id) VALUES (2, '?page=1&size=3&sort=name:asc', '?page=1&size=3&sort=name:asc', 3);

INSERT INTO LocalDB.tasks (id, name, deadLine, timeAdd, isDone, userId, priority) VALUES (1, 'taskname', '2019-12-12', '2019-11-05', 0, 2, 'low');
INSERT INTO LocalDB.tasks (id, name, deadLine, timeAdd, isDone, userId, priority) VALUES (2, 'taskname3', '2019-12-12', '2019-11-05', 0, 2, 'low');
INSERT INTO LocalDB.tasks (id, name, deadLine, timeAdd, isDone, userId, priority) VALUES (3, 'taskname2', '2019-12-12', '2019-11-05', 0, 2, 'low');
INSERT INTO LocalDB.tasks (id, name, deadLine, timeAdd, isDone, userId, priority) VALUES (4, 'taskname4', '2019-12-12', '2019-11-05', 0, 2, 'low');
