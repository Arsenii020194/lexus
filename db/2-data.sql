insert into list.group (id, code, name)
values (1, 'ADMIN', 'ADMIN');
insert
into list.group (id, code, name)
values (2, 'TRAINER', 'TRAINER');
insert into list.group (id, code, name)
values (3, 'USER', 'USER');
insert into md."user" (id, date_birth, id_group, image, name, "password")
values (1, cast('1994-01-02' as date), 1, '', 'admin', '$2a$10$p3ipziRoJzMvxjpS4UKiwOOemKQljvZ5C.m2kvqbzaj7q400Ws0AG');
insert into md."user" (id, date_birth, id_group, image, name, "password")
values (2, cast('1994-01-02' as date), 2, '', 'trainer',
        '$2a$10$ccXLU60FZ.k92mGti2ZntO/FM6.OzDp0UwGZD7GC0jkqcuLv0H4gq');
insert into md."user" (id, date_birth, id_group, image, name, "password")
values (3, cast('1994-01-02' as date), 3, '', 'user', '$2a$10$dY85zy0ZMc2rNHX9Y4Gtoed1y2ODGLQliC.iGw0vjyi2nI9OCl9SK');
insert into md."role" (code, id_user, name)
values ('ROLE_ADMIN', 1, 'ADMIN');
insert into md."role" (code, id_user, name)
values ('ROLE_TRAINER', 2, 'TRAINER');
insert into md."role" (code, id_user, name)
values ('ROLE_USER', 3, 'USER');

insert into LIST.TRAINING_STATUS (ID, NAME, CODE)
values (1, 'Пропущена', 'MISSED');
insert into LIST.TRAINING_STATUS (ID, NAME, CODE)
values (2, 'Подтверждена тренером', 'APPROVED');
insert into LIST.TRAINING_STATUS (ID, NAME, CODE)
values (3, 'Выполнена', 'COMPLETED');

insert into LIST.TRAINING_TYPE (ID, NAME, CODE)
values (1, 'Персоналка', 'PERSONAL');
insert into LIST.TRAINING_TYPE (ID, NAME, CODE)
values (2, 'Группа', 'GROUP');

insert into LIST.SUBSCRIPTION_TYPE (ID, NAME, CODE, PRICE)
values (1, 'Годовой абонемент', 'ANNUAL', 1000.0);
insert into LIST.SUBSCRIPTION_TYPE (ID, NAME, CODE, PRICE)
values (2, 'Абонемент на месяц', 'MONTH', 9000.0);