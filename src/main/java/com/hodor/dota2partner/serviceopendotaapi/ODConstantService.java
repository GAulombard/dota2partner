package com.hodor.dota2partner.serviceopendotaapi;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.exception.OpenDotaApiException;

public interface ODConstantService {

    ObjectNode getConstant(String resourceName) throws OpenDotaApiException;

    ObjectNode getConstant() throws OpenDotaApiException;
}
