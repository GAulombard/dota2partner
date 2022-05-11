package com.hodor.dota2partner.serviceopendotaapi;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;

import java.util.List;

public interface ODPlayersService {

    ObjectNode getPlayerData(Long steamId32) throws OpenDotaApiException;

    ObjectNode getWinLossCount(Long steamId32) throws OpenDotaApiException;

    List<ArrayNode> getPeers(Long steamId32) throws OpenDotaApiException;

    List<ArrayNode> getPeers(Long steamId32, String queryParameters) throws OpenDotaApiException;
}
