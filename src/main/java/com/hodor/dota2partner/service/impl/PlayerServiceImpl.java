package com.hodor.dota2partner.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.dto.AsideHeroRequestDTO;
import com.hodor.dota2partner.exception.*;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.repository.HeroRepository;
import com.hodor.dota2partner.repository.PlayerRepository;
import com.hodor.dota2partner.serviceopendotaapi.ODMatchService;
import com.hodor.dota2partner.serviceopendotaapi.ODPlayerService;
import com.hodor.dota2partner.service.PlayerService;
import com.hodor.dota2partner.util.Calculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final HeroRepository heroRepository;
    private final PlayerRepository playerRepository;
    private final ODPlayerService oDPlayerService;
    private final ODMatchService odMatchService;

    @Override
    @Async
    @Transactional
    public void refreshPlayerData(Long steamId32) throws OpenDotaApiException, PlayerNotFoundException {

        log.debug("Fetching player data process");

        if (!playerRepository.existsBySteamId32(steamId32))
            throw new PlayerNotFoundException("Player with this steam Id not found");

        Player player = playerRepository.findPlayerBySteamId32(steamId32);
        String profile = "profile";

        ObjectNode dataPlayer = oDPlayerService.getPlayerData(steamId32);
        ObjectNode winLossCount = oDPlayerService.getWinLossCount(steamId32);

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
        player.setLastLogin(ZonedDateTime.now(ZoneId.of("Europe/Paris")).plusHours(2));
        player.setDotaPlus(dataPlayer.path(profile).path("plus").asText().equals("true"));
        playerRepository.save(player);

        log.debug("Fetching player data process successful");

    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(Long steamId64) {
        return playerRepository.existsBySteamId64(steamId64);
    }

    @Override
    @Transactional(readOnly = true)
    public Player getPlayer(long steamId32) {
        return playerRepository.findPlayerBySteamId32(steamId32);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsideHeroRequestDTO> getAsideHeroList(Long steamId32) throws OpenDotaApiException {
        log.debug("Retrieving aside data process");

        List<AsideHeroRequestDTO> asideHeroRequestDTOList = new ArrayList<>();

        ArrayNode data = oDPlayerService.getHeroes(steamId32, "date=180&having=20");

        data.iterator().forEachRemaining(hero -> {
            AsideHeroRequestDTO asideHeroRequestDTO = new AsideHeroRequestDTO();
            int id = hero.path("hero_id").asInt();
            String query = "?hero_id=" + id + "&date=180";
            int killSum = 0;
            int deathSum = 0;
            int assistSum = 0;

            //todo: make a special method for that
            try {
                ArrayNode singleHeroDetail = odMatchService.getMyHeroData(steamId32, query);
                for (JsonNode heroDetail : singleHeroDetail) {
                    killSum = killSum + heroDetail.path("kills").asInt();
                    deathSum = deathSum + heroDetail.path("deaths").asInt();
                    assistSum = assistSum + heroDetail.path("assists").asInt();
                }
                asideHeroRequestDTO.setAverageKill(killSum / singleHeroDetail.size());
                asideHeroRequestDTO.setAverageDeath(deathSum / singleHeroDetail.size());
                asideHeroRequestDTO.setAverageAssist(assistSum / singleHeroDetail.size());
            } catch (OpenDotaApiException e) {
                e.printStackTrace();
            }

            asideHeroRequestDTO.setName(heroRepository.getHeroLocalizedNameById(id));
            asideHeroRequestDTO.setImage(heroRepository.getHeroImageById(id));
            asideHeroRequestDTO.setMatchPlayed(hero.path("games").asInt());
            asideHeroRequestDTO.setWinRate(Calculator.winRateCalculator(hero.path("win").asInt(),
                    (hero.path("games").asInt()) - (hero.path("win").asInt())));

            asideHeroRequestDTOList.add(asideHeroRequestDTO);
        });

        log.debug("Retrieving aside data process successful");
        return asideHeroRequestDTOList;
    }

}
