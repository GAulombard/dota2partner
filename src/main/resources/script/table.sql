create table heroes
(
    id                int          not null,
    name              varchar(200) null,
    localized_name    varchar(200) null,
    primary_attr      varchar(200) null,
    attack_type       varchar(200) null,
    img               varchar(500) null,
    icon              varchar(500) null,
    base_health       decimal      null,
    base_health_regen decimal      null,
    base_mana         decimal      null,
    base_mana_regen   decimal      null,
    base_armor        decimal      null,
    base_mr           decimal      null,
    base_attack_min   decimal      null,
    base_attack_max   decimal      null,
    base_str          decimal      null,
    base_agi          decimal      null,
    base_int          decimal      null,
    str_gain          decimal      null,
    agi_gain          decimal      null,
    int_gain          decimal      null,
    attack_range      decimal      null,
    projectile_speed  decimal      null,
    attack_rate       decimal      null,
    move_speed        decimal      null,
    turn_rate         decimal      null,
    cm_enabled        tinyint(1)   null,
    legs              int          null,
    constraint heroes_id_uindex
        unique (id)
);

alter table heroes
    add primary key (id);

create table heroes_roles
(
    role_id int auto_increment,
    name    varchar(200) not null,
    constraint heroes_roles_role_id_uindex
        unique (role_id)
);

alter table heroes_roles
    add primary key (role_id);

create table heroes_map_roles
(
    hero_id int not null,
    role_id int not null,
    constraint hero_fk
        foreign key (hero_id) references heroes (id),
    constraint hero_role_fk
        foreign key (role_id) references heroes_roles (role_id)
);

create index heroe_fk_idx
    on heroes_map_roles (hero_id);

create index heroe_role_fk_idx
    on heroes_map_roles (role_id);

create table players
(
    player_id     bigint auto_increment,
    steam_id_64   bigint       null,
    steam_id_32   bigint       null,
    persona_name  varchar(50)  null,
    email         varchar(125) not null,
    password      varchar(100) null,
    creation_date datetime     null,
    last_login    datetime     null,
    country_code  varchar(5)   null,
    dota_plus     tinyint(1)   null,
    contributor   tinyint(1)   null,
    enabled       tinyint(1)   null,
    deleted       tinyint(1)   null,
    avatar        varchar(200) null,
    avatar_full   varchar(200) null,
    avatar_medium varchar(200) null,
    profile_url   varchar(200) null,
    rank_tier     int          null,
    win           int          null,
    loss          int          null,
    win_rate      double       null,
    constraint players_e_mail_uindex
        unique (email),
    constraint players_id_uindex
        unique (player_id),
    constraint players_steam_id_32_uindex
        unique (steam_id_32),
    constraint players_steam_id_64_uindex
        unique (steam_id_64)
);

alter table players
    add primary key (player_id);

create table friend
(
    id               bigint auto_increment
        primary key,
    friend_player_id bigint not null,
    player_id        bigint not null,
    constraint friend_players__fk1
        foreign key (player_id) references players (player_id),
    constraint friend_players_player_id_fk
        foreign key (friend_player_id) references players (player_id)
);

create index player_friend_fk
    on friend (player_id);

create index player_friend_fk1
    on friend (friend_player_id);

create table roles
(
    role_id int auto_increment,
    name    varchar(50) not null,
    constraint roles_role_id_uindex
        unique (role_id)
);

alter table roles
    add primary key (role_id);

create table players_map_roles
(
    player_id bigint not null,
    role_id   int    not null,
    constraint player_fk
        foreign key (player_id) references players (player_id),
    constraint role_fk
        foreign key (role_id) references roles (role_id)
);

create index player_fk_idx
    on players_map_roles (player_id);

create index role_fk_idx
    on players_map_roles (role_id);

create table token
(
    id               bigint auto_increment,
    token            varchar(500) null,
    player_player_id int          null,
    expiry_date      datetime     null,
    constraint token_id_uindex
        unique (id)
);

alter table token
    add primary key (id);

