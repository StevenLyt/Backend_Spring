package com.example.pokersc.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // for now, we treat this as the code that other players use to enter the room (can generate another code)

//    private List<User> userList;
//    private CommunityCards communityCards;
//    private List<HandCards> handCardsList;
    private int total_bet;
    // TODO: to be completed

//    public void addUser(User user) {
//        userList.add(user);
//    }
}