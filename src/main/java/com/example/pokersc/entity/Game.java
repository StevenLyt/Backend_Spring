package com.example.pokersc.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int id;
    public int numPlayers;
    public User[] userArr;
    public int dealerPos;
    public int[] remainingChips;
    public int[] totalBuyin;

    public Game(){
        this.numPlayers = 0;
        this.userArr = new User[8];
        for(int i = 0; i < 8; i++){
            this.totalBuyin[i] = 0;
            remainingChips[i] = 0;
            userArr[i] = null;
        }
        this.dealerPos = 0;

    }

    public void addUser(User user, int buyin, int pos) {
        this.userArr[pos] = user;
        this.remainingChips[pos] = buyin;
        this.totalBuyin[pos] = buyin;
        this.numPlayers++;
    }

    public void rebuy(int user_id, int amount){
        for(int i = 0; i < userArr.length; i++){
            if (userArr[i].getUserId() == user_id){
                totalBuyin[i] += amount;
            }
        }
    }

    public void updatePos(){
       this.dealerPos ++;
       this.dealerPos &= 8;
       while(userArr[dealerPos] == null){
           this.dealerPos ++;
           this.dealerPos &= 8;
       }
    }

    public void deleteUser(int user_id){
        for(int i = 0; i < userArr.length; i++){
            if (userArr[i].getUserId()==user_id){
                userArr[i] = null;
            }
        }
        this.numPlayers --;
    }

    public int getBalance(int userId){
        int index = 0;
        for(int i =0; i<8;i++){
            if (userArr[i].getUserId()==userId){
                index = i;
            }
        }
        return totalBuyin[index]-remainingChips[index];
    }
}
