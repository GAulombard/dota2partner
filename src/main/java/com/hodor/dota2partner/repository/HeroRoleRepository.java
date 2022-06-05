package com.hodor.dota2partner.repository;

import com.hodor.dota2partner.entity.HeroRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRoleRepository extends JpaRepository<HeroRole, Integer> {

    HeroRole findByName(String name);

}