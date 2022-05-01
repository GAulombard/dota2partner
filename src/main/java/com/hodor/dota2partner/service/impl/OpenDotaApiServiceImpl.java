package com.hodor.dota2partner.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.service.OpenDotaApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;

@Service
@Slf4j
public class OpenDotaApiServiceImpl implements OpenDotaApiService {

    private String openDotaApiUrl = "https://api.opendota.com/api";

    @Override
    public ObjectNode getPlayerData(Long steamId32) throws OpenDotaApiException {

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

    @Override
    public ObjectNode getWinLossCount(Long steamId32) throws OpenDotaApiException {
        try {

            RestTemplate restTemplate = new RestTemplate();
            String callUrl = openDotaApiUrl +"/players/"+steamId32+"/wl";

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
