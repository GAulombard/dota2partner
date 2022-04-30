package com.hodor.dota2partner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Can't reach OpenDota Api")
public class OpenDotaApiException extends Exception{
    public OpenDotaApiException(String s) {
        super(s);
    }
}
