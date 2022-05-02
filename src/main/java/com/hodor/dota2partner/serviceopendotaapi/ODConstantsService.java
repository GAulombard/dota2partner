package com.hodor.dota2partner.serviceopendotaapi;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;

public interface ODConstantsService {

    ObjectNode getConstants(String resourceName) throws OpenDotaApiException;

    ObjectNode getConstants() throws OpenDotaApiException;
}
