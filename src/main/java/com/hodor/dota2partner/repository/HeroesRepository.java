package com.hodor.dota2partner.repository;

import com.hodor.dota2partner.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroesRepository extends JpaRepository<Hero, int> {
}