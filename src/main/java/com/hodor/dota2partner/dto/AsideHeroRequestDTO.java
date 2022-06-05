package com.hodor.dota2partner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsideHeroRequestDTO {

    private String name;
    private String image;
    private int matchPlayed;
    private float winRate;
    private int averageKill;
    private int averageDeath;
    private int averageAssist;

}
