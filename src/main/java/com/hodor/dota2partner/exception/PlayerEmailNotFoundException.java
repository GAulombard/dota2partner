package com.hodor.dota2partner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Element not Found")
public class PlayerEmailNotFoundException extends Exception{
    public PlayerEmailNotFoundException(String s) {
        super(s);
    }
}
