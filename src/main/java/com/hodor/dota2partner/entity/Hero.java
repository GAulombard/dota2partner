package com.hodor.dota2partner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "heroes")
public class Hero {

    @Id
    //@GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String name;

    @Column(name = "localized_name")
    private String localizedName;

    @Column(name = "primary_attr", length = 200)
    private String primaryAttr;

    @Column(name = "attack_type", length = 200)
    private String attackType;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name = "heroes_map_roles",
            joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Column(name = "img", length = 500)
    private String img;

    @Column(name = "icon", length = 500)
    private String icon;

    @Column(name = "base_health", precision = 10)
    private BigDecimal baseHealth;

    @Column(name = "base_health_regen", precision = 10)
    private BigDecimal baseHealthRegen;

    @Column(name = "base_mana", precision = 10)
    private BigDecimal baseMana;

    @Column(name = "base_mana_regen", precision = 10)
    private BigDecimal baseManaRegen;

    @Column(name = "base_armor", precision = 10)
    private BigDecimal baseArmor;

    @Column(name = "base_mr", precision = 10)
    private BigDecimal baseMr;

    @Column(name = "base_attack_min", precision = 10)
    private BigDecimal baseAttackMin;

    @Column(name = "base_attack_max", precision = 10)
    private BigDecimal baseAttackMax;

    @Column(name = "base_str", precision = 10)
    private BigDecimal baseStr;

    @Column(name = "base_agi", precision = 10)
    private BigDecimal baseAgi;

    @Column(name = "base_int", precision = 10)
    private BigDecimal baseInt;

    @Column(name = "str_gain", precision = 10)
    private BigDecimal strGain;

    @Column(name = "agi_gain", precision = 10)
    private BigDecimal agiGain;

    @Column(name = "int_gain", precision = 10)
    private BigDecimal intGain;

    @Column(name = "attack_range", precision = 10)
    private BigDecimal attackRange;

    @Column(name = "projectile_speed", precision = 10)
    private BigDecimal projectileSpeed;

    @Column(name = "attack_rate", precision = 10)
    private BigDecimal attackRate;

    @Column(name = "move_speed", precision = 10)
    private BigDecimal moveSpeed;

    @Column(name = "turn_rate", precision = 10)
    private BigDecimal turnRate;

    @Column(name = "cm_enabled")
    private Boolean cmEnabled;

    @Column(name = "legs")
    private Integer legs;

}
