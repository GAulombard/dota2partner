package com.hodor.dota2partner.serviceopendotaapi.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.serviceopendotaapi.ODMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ODMatchServiceImpl implements ODMatchService {

    private String openDotaApiUrl = "https://api.opendota.com/api";

    @Override
    public ArrayNode getMyHeroData(Long steamId32, String query) throws OpenDotaApiException {
        try {

            RestTemplate restTemplate = new RestTemplate();
            String callUrl = openDotaApiUrl + "/players/" + steamId32 + "/matches" + query;

            log.info("Calling OpenDota Api : " + callUrl);
            ResponseEntity<ArrayNode> response = restTemplate.getForEntity(callUrl, ArrayNode.class);
            ArrayNode jsonObject = response.getBody();
            log.info("Data from OpenDota Api fetched");
            log.debug("OpenDota Api response : {}", jsonObject);

            return jsonObject;

        } catch (Exception e) {
            log.error("Can't reach OpenDota Api");
            throw new OpenDotaApiException("Can't reach OpenDota Api");
        }
    }
}
