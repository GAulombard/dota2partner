package com.hodor.dota2partner.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;

public interface OpenDotaApiService {

    ObjectNode getPlayerData(Long steamId32) throws OpenDotaApiException;

    ObjectNode getWinLossCount(Long steamId32) throws OpenDotaApiException;
}
