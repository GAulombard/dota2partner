package com.hodor.dota2partner.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class NumericValidator implements ConstraintValidator<Numeric, Long> {

    @Override
    public boolean isValid(Long s, ConstraintValidatorContext constraintValidatorContext) {

        log.info("test: " + s);

        return true;
    }
}
