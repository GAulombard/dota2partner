package com.hodor.dota2partner.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "teaser_text", length = 800)
    private String teaserText;

    @Column(name = "age")
    private Integer age;

    @Column(name = "main_position")
    private Integer mainPosition;

    @Column(name = "worst_position")
    private Integer worstPosition;

    @Column(name = "looking_for_team")
    private Boolean lookingForTeam;

    @Column(name = "looking_for_partner")
    private Boolean lookingForPartner;

    @Column(name = "looking_for_coach")
    private Boolean lookingForCoach;

    @Column(name = "looking_for_strategist")
    private Boolean lookingForStrategist;

    @Column(name = "looking_for_short_caller")
    private Boolean lookingForShortCaller;

    @Column(name = "coaching_experience")
    private Boolean coachingExperience;

    @Column(name = "short_calling_aptitude")
    private Boolean shortCallingAptitude;

    @Column(name = "strategy_aptitude")
    private Boolean strategyAptitude;

    @Column(name = "average_week_game")
    private Integer averageWeekGame;

}
