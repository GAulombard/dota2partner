package com.hodor.dota2partner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ActivationMailException extends RuntimeException {
    public ActivationMailException(String s) {
        super(s);
    }
}
