package com.example.pokersc.entity;

import javax.persistence.*;

@Entity
@Table(name = "GameResult")
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int game_id;
    private int user_id;
    private int amount; // positive win, negative lose

    protected GameResult() {
    }
}
