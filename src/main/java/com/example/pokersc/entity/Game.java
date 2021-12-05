package com.example.pokersc.entity;

public class Game {

    private int id;
    public int numPlayers;
    public User[] userArr;
    public int dealerPos;
    public int[] remainingChips;
    public int[] totalBuyin;
    public boolean ongoing;
    public Game(){
        this.numPlayers = 0;
        this.userArr = new User[8];
        this.remainingChips = new int[8];
        this.totalBuyin = new int[8];
        for(int i = 0; i < 8; i++){
            this.totalBuyin[i] = 0;
            this.remainingChips[i] = 0;
            this.userArr[i] = null;
        }
        this.dealerPos = 0;
        this.ongoing = false;
    }

    public void addUser(User user, int buyin, int pos) {
        this.userArr[pos] = user;
        this.remainingChips[pos] = buyin;
        this.totalBuyin[pos] = buyin;
        this.numPlayers++;
    }

    //return true if buyin is complete
    public boolean rebuy(String username, int amount){
        for(int i = 0; i < userArr.length; i++){
            if (userArr[i].getUsername().equals(username)){
                totalBuyin[i] += amount;
                remainingChips[i] += amount;
                return true;
            }
        }
        return false;
    }

    public void updatePos(){
       this.dealerPos ++;
       this.dealerPos %= 8;
       while(userArr[dealerPos] == null){
           this.dealerPos ++;
           this.dealerPos %= 8;
       }
    }

    public void updateUserStats(){
        for(int i = 0; i < 8; i++) {
            if(userArr[i]!=null) {
                userArr[i].setTotal_round(userArr[i].getTotal_round() + 1);
                userArr[i].setTotal_profit(userArr[i].getTotal_profit() + (remainingChips[i] - totalBuyin[i]));
            }
        }
    }

    public void deleteUser(String username){
        for(int i = 0; i < userArr.length; i++){
            if (userArr[i].getUsername().equals(username))
            {
                userArr[i] = null;
                totalBuyin[i] = 0;
                remainingChips[i] = 0;
            }
        }
        this.numPlayers --;
    }

    public int getBalance(String username){
        int index = 0;
        for(int i =0; i<8;i++){
            if (userArr[i].getUsername().equals(username)){
                index = i;
            }
        }
        return totalBuyin[index]-remainingChips[index];
    }

    public User getUserByUsername(String username){
        for(User user: userArr){
            if (user != null && user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public User[] getAllUsers() {
        return userArr;
    }
}
