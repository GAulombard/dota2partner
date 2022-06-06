package com.hodor.dota2partner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsideHeroRequestDTO implements Serializable {

    private String name;
    private String image;
    private int matchPlayed;
    private float winRate;
    private int averageKill;
    private int averageDeath;
    private int averageAssist;

}
