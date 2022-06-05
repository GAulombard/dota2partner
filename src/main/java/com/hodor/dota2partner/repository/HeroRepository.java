package com.hodor.dota2partner.repository;

import com.hodor.dota2partner.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Integer> {

    @Query("select localizedName from Hero where id = :id")
    String getHeroLocalizedNameById(int id);

    @Query("select img from Hero where id = :id")
    String getHeroImageById(int id);

}