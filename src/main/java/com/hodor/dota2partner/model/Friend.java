package com.hodor.dota2partner.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "friend")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @ManyToOne
    @JoinColumn(name = "friend_player_id")
    private Player friendPlayerId;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player playerId;
}
