package com.hodor.dota2partner.service;

import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.entity.Player;

import java.util.List;

public interface FriendService {

    List<Player> searchFriend(long steamId32) throws OpenDotaApiException;

    void updateFriendList(long steamId32) throws OpenDotaApiException;

}
