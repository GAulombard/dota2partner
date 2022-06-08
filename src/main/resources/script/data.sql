insert ignore into players (steam_id_64, email, password, enabled, steam_id_32, contributor, deleted, dota_plus,
                            creation_date, rank_tier, win, loss, win_rate)
    value (76561198051430145, 'hodor@inbox.mailtrap.io', '$2a$10$L1C8X1bXjai9B2z92wxkXObIZydFIvwsD6PWRheTiMEa.Pz/447de',
           true, 91164417, false, false, false, '2022-06-06 00:00:01', 0, 0, 0, 0);
insert ignore into players (steam_id_64, email, password, enabled, steam_id_32, contributor, deleted, dota_plus,
                            creation_date, rank_tier, win, loss, win_rate)
    value (76561198094046059, 'zardoz@inbox.mailtrap.io',
           '$2a$10$L1C8X1bXjai9B2z92wxkXObIZydFIvwsD6PWRheTiMEa.Pz/447de', true, 133780331, false, false, false,
           '2022-06-06 00:00:01', 0, 0, 0, 0);
insert ignore into players (steam_id_64, email, password, enabled, steam_id_32, contributor, deleted, dota_plus,
                            creation_date, rank_tier, win, loss, win_rate)
    value (76561198014463037, 'sambrun@inbox.mailtrap.io',
           '$2a$10$L1C8X1bXjai9B2z92wxkXObIZydFIvwsD6PWRheTiMEa.Pz/447de', true, 54197309, false, false, false,
           '2022-06-06 00:00:01', 0, 0, 0, 0);
insert ignore into players (steam_id_64, email, password, enabled, steam_id_32, contributor, deleted, dota_plus,
                            creation_date, rank_tier, win, loss, win_rate)
    value (76561198030647714, 'brou@inbox.mailtrap.io', '$2a$10$L1C8X1bXjai9B2z92wxkXObIZydFIvwsD6PWRheTiMEa.Pz/447de',
           true, 70381986, false, false, false, '2022-06-06 00:00:01', 0, 0, 0, 0);
insert ignore into players (steam_id_64, email, password, enabled, steam_id_32, contributor, deleted, dota_plus,
                            creation_date, rank_tier, win, loss, win_rate)
    value (76561198041834510, 'zilto@inbox.mailtrap.io', '$2a$10$L1C8X1bXjai9B2z92wxkXObIZydFIvwsD6PWRheTiMEa.Pz/447de',
           true, 81568782, false, false, false, '2022-06-06 00:00:01', 0, 0, 0, 0);
insert ignore into players (steam_id_64, email, password, enabled, steam_id_32, contributor, deleted, dota_plus,
                            creation_date, rank_tier, win, loss, win_rate)
    value (76561197964805074, 'framboisier@inbox.mailtrap.io',
           '$2a$10$L1C8X1bXjai9B2z92wxkXObIZydFIvwsD6PWRheTiMEa.Pz/447de', false, 4539346, false, false, false,
           '2022-06-06 00:00:01', 0, 0, 0, 0);
insert ignore into players (steam_id_64, email, password, enabled, steam_id_32, contributor, deleted, dota_plus,
                            creation_date, rank_tier, win, loss, win_rate)
    value (76561197996437422, 'willou@inbox.mailtrap.io',
           '$2a$10$L1C8X1bXjai9B2z92wxkXObIZydFIvwsD6PWRheTiMEa.Pz/447de', true, 36171694, false, false, false,
           '2022-06-06 00:00:01', 0, 0, 0, 0);
insert ignore into players (steam_id_64, email, password, enabled, steam_id_32, contributor, deleted, dota_plus,
                            creation_date, rank_tier, win, loss, win_rate)
    value (76561198047282072, 'snoop@inbox.mailtrap.io', '$2a$10$L1C8X1bXjai9B2z92wxkXObIZydFIvwsD6PWRheTiMEa.Pz/447de',
           true, 87016344, false, false, false, '2022-06-06 00:00:01', 0, 0, 0, 0);
insert ignore into players (steam_id_64, email, password, enabled, steam_id_32, contributor, deleted, dota_plus,
                            creation_date, rank_tier, win, loss, win_rate)
    value (76561198088420271, 'savini@inbox.mailtrap.io',
           '$2a$10$L1C8X1bXjai9B2z92wxkXObIZydFIvwsD6PWRheTiMEa.Pz/447de', true, 128154543, false, false, false,
           '2022-06-06 00:00:01', 0, 0, 0, 0);

insert ignore into roles (name)
values ('ROLE_ADMIN');
insert ignore into roles (name)
values ('ROLE_USER');

insert ignore into heroes_roles (name)
values ('Carry');
insert ignore into heroes_roles (name)
values ('Escape');
insert ignore into heroes_roles (name)
values ('Nuker');
insert ignore into heroes_roles (name)
values ('Initiator');
insert ignore into heroes_roles (name)
values ('Durable');
insert ignore into heroes_roles (name)
values ('Disabler');
insert ignore into heroes_roles (name)
values ('Jungler');
insert ignore into heroes_roles (name)
values ('Support');
insert ignore into heroes_roles (name)
values ('Pusher');

/*insert ignore into players_map_roles (player_id, role_id)
values (1, 1);
insert ignore into players_map_roles (player_id, role_id)
values (2, 2);
insert ignore into players_map_roles (player_id, role_id)
values (3, 2);
insert ignore into players_map_roles (player_id, role_id)
values (4, 2);
insert ignore into players_map_roles (player_id, role_id)
values (5, 2);
insert ignore into players_map_roles (player_id, role_id)
values (6, 2);
insert ignore into players_map_roles (player_id, role_id)
values (7, 2);
insert ignore into players_map_roles (player_id, role_id)
values (8, 2);
insert ignore into players_map_roles (player_id, role_id)
values (9, 2);*/