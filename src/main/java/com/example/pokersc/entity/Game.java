package com.example.pokersc.entity;

public class Game {

    private int id;
    public int numPlayers;
    public User[] userArr;
    public int dealerPos;
    public int[] remainingChips;
    public int[] totalBuyin;
    public boolean ongoing;
    public boolean handend;
    public Hand hand = null;
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
        this.handend = true;
    }

    public void addUser(User user, int buyin, int pos) {
        this.userArr[pos] = user;
        this.remainingChips[pos] = buyin;
        this.totalBuyin[pos] = buyin;
        this.numPlayers++;
    }

    //return true if buyin is complete
    public String rebuy(String username, int amount) {
        int pos = -1;
        for (int i = 0; i < userArr.length; i++) {
            if (userArr[i] != null && userArr[i].getUsername().equals(username)) {
                pos = i;
            }
        }
        if (pos == -1) {
            return "user not found";
        }

        if (!handend) {
            if (hand.getActive()[pos]) {
                return "player is active";
            }
        }

        if (remainingChips[pos] + amount <= 1200) {
            totalBuyin[pos] += amount;
            remainingChips[pos] += amount;
            if (!handend) {
                hand.startingStack[pos] += amount;
            }
            return "success";
        } else {
            return "chips exceeded";
        }
    }



    public void updatePos(){
       this.dealerPos ++;
       this.dealerPos %= 8;
       while(userArr[dealerPos] == null){
           this.dealerPos ++;
           this.dealerPos %= 8;
       }
    }

    public void deleteUser(String username){
        for(int i = 0; i < userArr.length; i++){
            if (userArr[i]!=null && userArr[i].getUsername().equals(username))
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
            if (userArr[i]!=null && userArr[i].getUsername().equals(username)){
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
