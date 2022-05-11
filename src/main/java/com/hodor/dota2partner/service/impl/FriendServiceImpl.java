package com.hodor.dota2partner.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.repository.FriendRepository;
import com.hodor.dota2partner.repository.PlayerRepository;
import com.hodor.dota2partner.service.FriendService;
import com.hodor.dota2partner.serviceopendotaapi.impl.ODPlayersServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private ODPlayersServiceImpl odPlayersService;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> searchFriend(long steamId32) throws OpenDotaApiException {
        log.info("Service - searching for friends");
        List<Player> friendList = new ArrayList<>();

        ArrayNode peers = odPlayersService.getPeers(steamId32);

        peers.iterator().forEachRemaining(peer -> {
            long id = peer.path("account_id").asLong();
            if(playerRepository.existsBySteamId32(id))  {
                friendList.add(playerRepository.findPlayerBySteamId32(id));
            }
        });

        if(friendList == null) log.info("Service - no friends found");

        log.info("Service - "+friendList.size()+" friend(s) found");
        return friendList;
    }

    @Override
    public void updateFriendList(long steamId32) throws OpenDotaApiException {

        List<Player> friendList = searchFriend(steamId32);

    }

}
