package com.hodor.dota2partner.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.entity.Hero;
import com.hodor.dota2partner.entity.HeroRole;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.repository.HeroRepository;
import com.hodor.dota2partner.repository.HeroRoleRepository;
import com.hodor.dota2partner.service.HeroService;
import com.hodor.dota2partner.serviceopendotaapi.ODConstantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final HeroRoleRepository heroRoleRepository;
    private final ODConstantService odConstantService;

    @Override
    @Transactional
    public void saveHeroData() throws OpenDotaApiException {
        log.debug("Fetching heroes data process");

        //todo: check if there are exception to throw

        String urlSteam = "https://steamcdn-a.akamaihd.net";

        ObjectNode heroData = odConstantService.getConstant("heroes");

        heroData.iterator().forEachRemaining(heroIterator -> {

            Hero hero = new Hero();
            hero.setId(heroIterator.path("id").asInt());
            hero.setName(heroIterator.path("name").asText());
            hero.setLocalizedName(heroIterator.path("localized_name").asText());
            hero.setPrimaryAttr(heroIterator.path("primary_attr").asText());
            hero.setAttackType(heroIterator.path("attack_type").asText());
            hero.setImg(urlSteam + heroIterator.path("img").asText());
            hero.setIcon(urlSteam + heroIterator.path("icon").asText());
            hero.setBaseHealth(heroIterator.path("base_health").decimalValue());
            hero.setBaseHealthRegen(heroIterator.path("base_health_regen").decimalValue());
            hero.setBaseMana(heroIterator.path("base_mana").decimalValue());
            hero.setBaseManaRegen(heroIterator.path("base_mana_regen").decimalValue());
            hero.setBaseArmor(heroIterator.path("base_armor").decimalValue());
            hero.setBaseMr(heroIterator.path("base_mr").decimalValue());
            hero.setBaseAttackMin(heroIterator.path("base_attack_min").decimalValue());
            hero.setBaseAttackMax(heroIterator.path("base_attack_max").decimalValue());
            hero.setBaseStr(heroIterator.path("base_str").decimalValue());
            hero.setBaseAgi(heroIterator.path("base_agi").decimalValue());
            hero.setBaseInt(heroIterator.path("base_int").decimalValue());
            hero.setStrGain(heroIterator.path("str_gain").decimalValue());
            hero.setAgiGain(heroIterator.path("agi_gain").decimalValue());
            hero.setIntGain(heroIterator.path("int_gain").decimalValue());
            hero.setAttackRange(heroIterator.path("attack_range").decimalValue());
            hero.setProjectileSpeed(heroIterator.path("projectile_speed").decimalValue());
            hero.setAttackRate(heroIterator.path("attack_rate").decimalValue());
            hero.setMoveSpeed(heroIterator.path("move_speed").decimalValue());
            hero.setTurnRate(heroIterator.path("turn_rate").decimalValue());
            hero.setCmEnabled(heroIterator.path("cm_enabled").asBoolean());
            hero.setLegs(heroIterator.path("legs").asInt());

            Collection<HeroRole> heroRoleCollection = new ArrayList<>();
            heroIterator.path("roles").iterator().forEachRemaining(role -> {
                HeroRole heroRole = heroRoleRepository.findByName(role.asText());
                heroRoleCollection.add(heroRole);
            });
            hero.setRoles(heroRoleCollection);

            heroRepository.save(hero);

        });

        log.debug("Fetching heroes data process successful");
    }
}
