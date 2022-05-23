package com.hodor.dota2partner.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class SteamId64Validator implements ConstraintValidator<SteamId64,Long> {

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        log.info("test: "+id);
        return false;
    }
}
