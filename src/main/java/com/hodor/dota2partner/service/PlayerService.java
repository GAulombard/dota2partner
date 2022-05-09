package com.hodor.dota2partner.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.EMailAlreadyExistsException;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.exception.PlayerNotFoundException;
import com.hodor.dota2partner.exception.SteamIdNotFoundException;
import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.model.dto.CreatePlayerDto;

public interface PlayerService {


    void createPlayer(CreatePlayerDto dto) throws SteamIdNotFoundException, OpenDotaApiException, EMailAlreadyExistsException, PlayerNotFoundException;

    void refreshPlayerData(Long steamId32) throws OpenDotaApiException, PlayerNotFoundException;
}
