package com.hodor.dota2partner.model.dto;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerDTO {

    private Integer playerId;

    private Long steamId64;

    private Long steamId32;

    private String email;

    private String personaName;

    private String password;

    private int rankTier;

    private String avatar;

    private String avatarMedium;

    private String avatarFull;

    private String profileUrl;

    private String countryCode;

    private LocalDateTime creationDate;

    private LocalDateTime lastLogin;

    private List<PlayerDTO> friendList;

    private List<PlayerDTO> friendListOf;

    private boolean dotaPlus;

    private boolean contributor;

    private boolean verified;

    private boolean deleted;

    private String role;

    private int win;

    private int loss;

    private float winRate;

}
