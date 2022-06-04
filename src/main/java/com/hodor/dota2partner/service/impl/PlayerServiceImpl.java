package com.hodor.dota2partner.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.*;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.dto.CreatePlayerDTO;
import com.hodor.dota2partner.repository.PlayerRepository;
import com.hodor.dota2partner.serviceopendotaapi.ODPlayersService;
import com.hodor.dota2partner.service.PlayerService;
import com.hodor.dota2partner.util.Calculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ODPlayersService oDPlayersService;
    @Autowired
    private DtoConverterServiceImpl dtoConverterService;
    @Autowired
    private FriendServiceImpl friendService;

    @Override
    @Async
    public void refreshPlayerData(Long steamId32) throws OpenDotaApiException, PlayerNotFoundException {

        log.info("Service - fetching data's player - " + steamId32);

        if (!playerRepository.existsBySteamId32(steamId32))
            throw new PlayerNotFoundException("Player with this steam Id not found");

        Player player = playerRepository.findPlayerBySteamId32(steamId32);
        String profile = "profile";

        ObjectNode dataPlayer = oDPlayersService.getPlayerData(steamId32);
        ObjectNode winLossCount = oDPlayersService.getWinLossCount(steamId32);

        player.setAvatar(dataPlayer.path(profile).path("avatar").asText());
        player.setAvatarFull(dataPlayer.path(profile).path("avatarfull").asText());
        player.setAvatarMedium(dataPlayer.path(profile).path("avatarmedium").asText());
        player.setPersonaName(dataPlayer.path(profile).path("personaname").asText());
        player.setProfileUrl(dataPlayer.path(profile).path("profileurl").asText());
        player.setCountryCode(dataPlayer.path(profile).path("loccountrycode").asText());
        player.setRankTier(dataPlayer.path("rank_tier").asInt());
        player.setWin(winLossCount.path("win").asInt());
        player.setLoss(winLossCount.path("lose").asInt());
        player.setWinRate(Calculator.winRateCalculator(player.getWin(), player.getLoss()));
        player.setLastLogin(Instant.now());
        player.setDotaPlus(dataPlayer.path(profile).path("plus").asText().equals("true"));
        playerRepository.save(player);

        log.info("Service - Data's player fetched");

    }

    @Override
    public boolean isExist(Long steamId64) {
        return playerRepository.existsBySteamId64(steamId64);
    }

    @Override
    public Player getPlayer(long steamId32) {
        return playerRepository.findPlayerBySteamId32(steamId32);
    }

}
