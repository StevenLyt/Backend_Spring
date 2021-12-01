package com.example.pokersc.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int id;
    public int numPlayers;
    public User[] userArr;
    public int[] posArr;

    public Game(){
        this.userArr = new User[8];
        this.posArr = new int[8];
        this.numPlayers = 0;
    }
    public void addUser(User user) {
        this.userArr[numPlayers] = user;
        this.numPlayers++;
        for(int i = 0 ; i < 8; i++){
            this.posArr[i] = i;
        }
    }
    public void updatePos(){
        for(int i = 0 ; i < 8; i++){
            this.posArr[i]++;
            this.posArr[i] &= 8;
        }
    }

    public void deleteUser(int user_id){
        for(int i = 0; i < userArr.length; i++){
            if (userArr[i].getUserId()==user_id){
                userArr[i]=null;
            }
        }
        //TODO Update the pos array.
    }
}
