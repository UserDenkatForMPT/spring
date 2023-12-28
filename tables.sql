create table model
(
    id_model            bigserial
        primary key,
    creation_time_model varchar(10240) not null,
    desc_model          varchar(10240) not null,
    name_model          varchar(10240) not null
);

create table module
(
    id_module   bigserial
        primary key,
    desc_module varchar(10240) not null,
    name_module varchar(10240) not null
);

create table role_
(
    id_role   bigserial
        primary key,
    name_role varchar(1024) not null
);

create table tarif
(
    id_tarif    bigserial
        primary key,
    desc_tarif  varchar(10240) not null,
    name_tarif  varchar(10240) not null,
    price_tarif varchar(10240) not null
);

create table user_
(
    id_user  bigserial
        primary key,
    login    varchar(255) not null,
    password varchar(255) not null,
    role_id  bigint
        constraint fkov4dyirjec1plvp9ed7ny2geu
            references role_,
    tarif_id bigint
        constraint fkeqoqcwbaa8enuysoe6swxm0v5
            references tarif
);

create table chat
(
    id_chat          bigserial
        primary key,
    name_chat        varchar(10240) not null,
    update_time_chat varchar(10240) not null,
    model_id         bigint
        constraint fkluyvu1im2c2njvl3b3uyado5h
            references model,
    user_id          bigint
        constraint fkj4led7u6bbo2gtho8wv3vg92b
            references user_
);

create table message
(
    id_message      bigserial
        primary key,
    is_user_message boolean        not null,
    number_message  bigint         not null,
    text_message    varchar(10240) not null,
    chat_id         bigint
        constraint fkmejd0ykokrbuekwwgd5a5xt8a
            references chat
);

create table mkm_chat_module
(
    module_id bigint not null
        constraint fknfb8ja6uvy9vjs08vihln6396
            references module,
    chat_id   bigint not null
        constraint fkl9liih2q5oxbjybqcm0ep3jo0
            references chat
);