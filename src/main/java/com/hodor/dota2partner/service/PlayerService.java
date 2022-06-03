package com.hodor.dota2partner.service;

import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.exception.PlayerNotFoundException;
import com.hodor.dota2partner.entity.Player;

public interface PlayerService {

    void refreshPlayerData(Long steamId32) throws OpenDotaApiException, PlayerNotFoundException;

    boolean isExist(Long steamId64);

    Player getPlayer(long steamId32);
}
