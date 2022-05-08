package com.hodor.dota2partner.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.*;
import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.repository.PlayerRepository;
import com.hodor.dota2partner.serviceopendotaapi.ODPlayersService;
import com.hodor.dota2partner.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Clock;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private PasswordEncoder passwordEncoder;
    private ODPlayersService oDPlayersService;
    private String openDotaApiUrl = "https://api.opendota.com/api";
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PasswordEncoder passwordEncoder, ODPlayersService oDPlayersService) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.oDPlayersService = oDPlayersService;
    }

    @Override
    public void createPlayer(Player player) throws SteamIdNotFoundException, OpenDotaApiException, EMailAlreadyExistsException, PlayerNotFoundException {

        if (playerRepository.existsByEmail(player.getEmail()))
            throw new EMailAlreadyExistsException("E-Mail " + player.getEmail() + " already exists");

        long steamId32 = player.getSteamId64() - 76561197960265728L;

        ObjectNode dataPlayer = oDPlayersService.getPlayerData(steamId32);

        if (dataPlayer.path("profile").path("steamid").asText().isEmpty()) {

            log.error("Steam ID: " + steamId32 + " does not exist");
            throw new SteamIdNotFoundException("Steam ID: " + steamId32 + " does not exist");

        } else {

            log.info("Service - Creating new player - steamId32: "+steamId32);
            player.setSteamId32(steamId32);
            player.setPassword(passwordEncoder.encode(player.getPassword()));
            //TODO: change this, depending of the country
            player.setCreationDate(LocalDateTime.now().plusHours(2));
            player.setContributor(false);
            player.setVerified(false);
            player.setRole("ROLE_USER");
            refreshPlayerData(steamId32);
            playerRepository.save(player);
            log.info("Service - Player created");

        }
    }

    @Override
    public void refreshPlayerData(Long steamId32) throws OpenDotaApiException, PlayerNotFoundException {

        log.info("Service - fetching data's player - " + steamId32);

        if (!playerRepository.existsBySteamId32(steamId32))
            throw new PlayerNotFoundException("Player with this steam Id not found");

        Player player = playerRepository.findPlayerBySteamId32(steamId32);
        double winRate;
        String profile = "profile";

        ObjectNode dataPlayer = oDPlayersService.getPlayerData(steamId32);
        ObjectNode winLossCount = oDPlayersService.getWinLossCount(steamId32);
        ObjectNode peers = oDPlayersService.getPeers(steamId32);

        player.setAvatar(dataPlayer.path(profile).path("avatar").asText());
        player.setAvatarFull(dataPlayer.path(profile).path("avatarfull").asText());
        player.setAvatarMedium(dataPlayer.path(profile).path("avatarmedium").asText());
        player.setPersonaName(dataPlayer.path(profile).path("personaname").asText());
        player.setProfileUrl(dataPlayer.path(profile).path("profileurl").asText());
        player.setCountryCode(dataPlayer.path(profile).path("loccountrycode").asText());
        player.setRankTier(dataPlayer.path("rank_tier").asInt());
        player.setWin(winLossCount.path("win").asInt());
        player.setLoss(winLossCount.path("lose").asInt());
        winRate = ((double) player.getWin() / (double) (player.getWin() + player.getLoss())) * 100;
        player.setWinRate((float) Math.round(winRate * 100) / 100);
        //TODO: change this, depending of the country
        player.setLastLogin(LocalDateTime.now().plusHours(2));
        player.setDotaPlus(dataPlayer.path(profile).path("plus").asText().equals("true"));

        playerRepository.save(player);
        log.info("Service - Data's player fetched");

    }

}
