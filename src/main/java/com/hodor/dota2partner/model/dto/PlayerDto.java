package com.hodor.dota2partner.model.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PlayerDto implements Serializable {

    private Long steamId32;
    private String personaName;
    private int rankTier;
    private int win;
    private int loss;
    private float winRate;

}
