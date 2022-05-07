package com.hodor.dota2partner.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlayerDto implements Serializable {
    private final Long steamId32;
    private final String personaName;
    private final int rankTier;
    private final int win;
    private final int loss;
    private final float winRate;
}
