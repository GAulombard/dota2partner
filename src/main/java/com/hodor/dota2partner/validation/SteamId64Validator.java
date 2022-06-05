package com.hodor.dota2partner.validation;

import com.hodor.dota2partner.serviceopendotaapi.ODPlayerService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@AllArgsConstructor
public class SteamId64Validator implements ConstraintValidator<SteamId64, Long> {

    private final ODPlayerService odPlayerService;

    @SneakyThrows
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {

        if (!odPlayerService.isExistOrPublicAccount(id)) return false;

        return true;
    }
}
