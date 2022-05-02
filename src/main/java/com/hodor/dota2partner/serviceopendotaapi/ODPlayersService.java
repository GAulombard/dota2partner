package com.hodor.dota2partner.serviceopendotaapi;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;

public interface ODPlayersService {

    ObjectNode getPlayerData(Long steamId32) throws OpenDotaApiException;

    ObjectNode getWinLossCount(Long steamId32) throws OpenDotaApiException;

    ObjectNode getPeers(Long steamId32) throws OpenDotaApiException;

    ObjectNode getPeers(Long steamId32, String queryParameters) throws OpenDotaApiException;
}
