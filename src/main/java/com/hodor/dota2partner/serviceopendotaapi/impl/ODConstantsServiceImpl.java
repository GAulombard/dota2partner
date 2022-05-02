package com.hodor.dota2partner.serviceopendotaapi.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.serviceopendotaapi.ODConstantsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ODConstantsServiceImpl implements ODConstantsService {

    private String openDotaApiUrl = "https://api.opendota.com/api";

    @Override
    public ObjectNode getConstants(String resourceName) throws OpenDotaApiException {
        try {

            RestTemplate restTemplate = new RestTemplate();
            String callUrl = openDotaApiUrl + "/constants/" + resourceName;

            log.info("Calling OpenDota Api : " + callUrl);
            ResponseEntity<ObjectNode> response = restTemplate.getForEntity(callUrl, ObjectNode.class);
            ObjectNode jsonObject = response.getBody();
            log.info("Data from OpenDota Api fetched");
            log.debug("OpenDota Api response : {}", jsonObject);

            return jsonObject;

        } catch (Exception e) {
            log.error("Can't reach OpenDota Api");
            throw new OpenDotaApiException("Can't reach OpenDota Api");
        }
    }

    @Override
    public ObjectNode getConstants() throws OpenDotaApiException {
        try {

            RestTemplate restTemplate = new RestTemplate();
            String callUrl = openDotaApiUrl + "/constants/";

            log.info("Calling OpenDota Api : " + callUrl);
            ResponseEntity<ObjectNode> response = restTemplate.getForEntity(callUrl, ObjectNode.class);
            ObjectNode jsonObject = response.getBody();
            log.info("Data from OpenDota Api fetched");
            log.debug("OpenDota Api response : {}", jsonObject);

            return jsonObject;

        } catch (Exception e) {
            log.error("Can't reach OpenDota Api");
            throw new OpenDotaApiException("Can't reach OpenDota Api");
        }
    }
}
