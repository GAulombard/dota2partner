package com.hodor.dota2partner.serviceopendotaapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;

public interface ODMatchService {


    ArrayNode getMyHeroData(Long steamId32, String query) throws OpenDotaApiException;

}
