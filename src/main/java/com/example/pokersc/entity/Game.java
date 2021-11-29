package com.example.pokersc.entity;


import javax.persistence.*;
import java.util.List;

public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private List<User> userList;
    private CommunityCards communityCards;
    private List<HandCards> handCardsList;
    private int total_bet;
    // TODO: to be completed

    public void addUser(User user) {
        userList.add(user);
    }
}
