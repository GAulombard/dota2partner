package com.hodor.dota2partner.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class NumericValidator implements ConstraintValidator<Numeric,Object> {

    @Override
    public boolean isValid(Object s, ConstraintValidatorContext constraintValidatorContext) {

        log.info("test: "+s);

        return false;
    }
}
