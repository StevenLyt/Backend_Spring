package com.example.pokersc.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int id;
    public int numPlayers;
    public User[] userArr;
    public int dealerPos;

    public Game(){
        this.numPlayers = 0;
        this.userArr = new User[8];
        this.dealerPos = 0;
    }
    public void addUser(User user) {
        this.userArr[numPlayers] = user;
        this.numPlayers++;
    }
    public void updatePos(){
       dealerPos ++;
       dealerPos &= 8;
       while(userArr[dealerPos] == null){
           dealerPos ++;
           dealerPos &= 8;
       }
    }

    public void deleteUser(int user_id){
        for(int i = 0; i < userArr.length; i++){
            if (userArr[i].getUserId()==user_id){
                userArr[i]=null;
            }
        }
        this.numPlayers --;
    }
}
