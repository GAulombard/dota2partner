package com.hodor.dota2partner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerRequestDTO implements Serializable {

    private Long steamId32;
    private String personaName;
    private int rankTier;
    private String avatarFull;
    private boolean enabled;
    private int matchPlayedWith;
    private int winWith;
    private float winRateWith;
}
