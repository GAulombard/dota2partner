package com.hodor.dota2partner.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "players")
@SQLDelete(sql = "UPDATE players SET deleted = true WHERE player_id=?") //safe delete
public class Player implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "steam_id_64")
    @NotNull(message = "Steam Id is mandatory")
    private Long steamId64;

    @Column(name = "steam_id_32")
    private Long steamId32;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    @Column(name = "e_mail")
    private String email;

    @Column(name = "persona_name")
    private String personaName;

    @Column(name = "password")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password must contain at least 1 uppercase char, 8 characters, 1 digit, and 1 symbol")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "avatar_medium")
    private String avatarMedium;

    @Column(name = "avatar_full")
    private String avatarFull;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToMany
    @JoinTable(
            name = "friend",
            joinColumns = @JoinColumn( name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_player_id")
    )
    private List<Player> friendList;

    @ManyToMany
    @JoinTable(
            name = "friend",
            joinColumns = @JoinColumn( name = "friend_player_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> friendListOf;

    @Column(name = "contributor")
    private boolean contributor;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "role")
    private String role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(this.role));

        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return personaName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
