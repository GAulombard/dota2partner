package com.hodor.dota2partner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Element already exists")
public class EMailAlreadyExistsException extends Exception {
    public EMailAlreadyExistsException(@NotEmpty(message = "Email is mandatory") @Email(message = "Email is not valid") String s) {
        super(s);
    }
}
