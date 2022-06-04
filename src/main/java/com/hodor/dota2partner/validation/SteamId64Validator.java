package com.hodor.dota2partner.validation;

import com.hodor.dota2partner.serviceopendotaapi.ODPlayersService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@AllArgsConstructor
public class SteamId64Validator implements ConstraintValidator<SteamId64, Long> {

    private final ODPlayersService odPlayersService;

    @SneakyThrows
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {

        if (!odPlayersService.isExistOrPublicAccount(id)) return false;

        return true;
    }
}
