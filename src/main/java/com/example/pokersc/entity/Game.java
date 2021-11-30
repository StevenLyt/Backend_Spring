package com.example.pokersc.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int id;
    private int numPlayers;

    private User[] userArr;
    private int[] posArr;

    public Game(){
        this.userArr = new User[8];
        this.posArr = new int[8];
        this.numPlayers = 0;
    }
    public void addUser(User user) {
        this.userArr[numPlayers] = user;
        this.numPlayers ++;
        for(int i = 0 ; i < 8; i++){
            this.posArr[i] = i;
        }
    }
    public void updatePos(){
        for(int i = 0 ; i < 8; i++){
            if(this.posArr[i] == 7) {
                this.posArr[i] = 0;
            }
            else {
                this.posArr[i]++;
            }
        }
    }
    public void startGame(){
        while(numPlayers > 1){
            updatePos();
            Hand hand = new Hand(userArr, posArr, numPlayers);
        }
    }

    public void pauseGame(){
    }


}
