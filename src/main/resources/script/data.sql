create table players
(
    player_id     bigint auto_increment,
    steam_id_64   bigint                          null,
    steam_id_32   bigint                          null,
    persona_name  varchar(50)                     null,
    password      varchar(100)                    null,
    avatar        varchar(100)                    null,
    avatar_medium varchar(100)                    null,
    avatar_full   varchar(100)                    null,
    profile_url   varchar(100)                    null,
    country_code  varchar(5)                      null,
    creation_date datetime                        null,
    contributor   tinyint(1)  default 0           null,
    deleted       tinyint(1)  default 0           null,
    role          varchar(15) default 'ROLE_USER' null,
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

