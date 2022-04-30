package com.hodor.dota2partner.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.EMailAlreadyExistsException;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.exception.SteamIdNotFoundException;
import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.repository.PlayerRepository;
import com.hodor.dota2partner.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private PasswordEncoder passwordEncoder;
    private String openDotaApiUrl = "https://api.opendota.com/api";


    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createPlayer(Player player) throws SteamIdNotFoundException, OpenDotaApiException, EMailAlreadyExistsException {

        if(playerRepository.existsByEmail(player.getEmail())) throw new EMailAlreadyExistsException("E-Mail "+player.getEmail()+" already exists");

        long steamId32 = player.getSteamId64() - 76561197960265728L;

        ObjectNode dataPlayer = loadPlayerDataFromOpenDota(steamId32);

        if(dataPlayer.path("profile").path("steamid").asText().isEmpty()) {

            log.error("Steam ID: "+steamId32+" does not exist");
            throw new SteamIdNotFoundException("Steam ID: "+steamId32+" does not exist");

        } else {

            log.info("Service - Creating new player - steamId32: "+player.getSteamId32());
            player.setSteamId32(steamId32);
            player.setPassword(passwordEncoder.encode(player.getPassword()));
            player.setAvatar(dataPlayer.path("profile").path("avatar").asText());
            player.setAvatarFull(dataPlayer.path("profile").path("avatarfull").asText());
            player.setAvatarMedium(dataPlayer.path("profile").path("avatarmedium").asText());
            player.setPersonaName(dataPlayer.path("profile").path("personaname").asText());
            player.setProfileUrl(dataPlayer.path("profile").path("profileurl").asText());
            player.setCountryCode(dataPlayer.path("profile").path("loccountrycode").asText());
            player.setCreationDate(LocalDateTime.now(Clock.systemUTC())); //TODO: change this, depending of the country
            player.setLastLogin(LocalDateTime.now(Clock.systemUTC())); //TODO: change this, depending of the country
            player.setDotaPlus(dataPlayer.path("profile").path("plus").asText().equals("true") ? true : false);
            player.setContributor(false);
            player.setVerified(false);
            player.setRole("ROLE_USER");
            playerRepository.save(player);
            log.info("Service - Player created");

        }
    }

    @Override
    public ObjectNode loadPlayerDataFromOpenDota(Long steamId32) throws OpenDotaApiException {

        try {

            RestTemplate restTemplate = new RestTemplate();
            String callUrl = openDotaApiUrl +"/players/"+steamId32;

            log.info("Calling OpenDota Api : "+callUrl);
            ResponseEntity<ObjectNode> response = restTemplate.getForEntity(callUrl,ObjectNode.class);
            ObjectNode jsonObject = response.getBody();
            log.info("OpenDota Api response : {}", jsonObject);

            return jsonObject;

        } catch (Exception e) {
            log.error("Can't reach OpenDota Api");
            throw new OpenDotaApiException("Can't reach OpenDota Api");
        }
    }
}
