package com.example.pokersc.entity;

import java.util.*;

public class Reception extends Thread {
    public LinkedList<Player> usersToAdd;
    public Game currentGame;
    public LinkedList<User> usersToLeave;

    public Reception(Game game){
        usersToAdd = new LinkedList<>();
        usersToLeave = new LinkedList<>();
        currentGame = game;
    }

    public void addPlayer(User user, int buyin, int pos){
        usersToAdd.add(new Player(user,buyin,pos));
    }

    public void removePlayer(User user){
        usersToLeave.add(user);
    }


    public void run(){
        while (true){
            while(!usersToAdd.isEmpty()){
                Player tempUser = usersToAdd.pop();
                currentGame.addUser(tempUser.getUser(),tempUser.getBuyin(),tempUser.getPos());
            }
            while (!usersToLeave.isEmpty()){
                User temp = usersToLeave.pop();
                int balance = currentGame.getBalance(temp.getUsername()) + temp.getTotal_profit();
                temp.setTotal_profit(balance);
                currentGame.deleteUser(temp.getUsername());
            }


        }
    }
}

class Player{
    private User user;
    private int buyin;
    private int pos;

    public Player(User user, int buyin, int pos){
        this.user = user;
        this.buyin = buyin;
        this.pos = pos;
    }

    public User getUser(){
        return user;
    }

    public int getBuyin() {
        return buyin;
    }

    public int getPos() {
        return pos;
    }
}