package com.example.pokersc.entity;

import java.util.Arrays;

public class GameThread extends Thread{

    public Game game;
    public Hand hand;

    public GameThread(){
        this.game = new Game();
        this.hand = null;
    }

    public void run(){
        try {
            while (true) {
                if (game.numPlayers < 3) {
                    game.ongoing = false;
                    Thread.sleep(1000);
                } else {
                    game.ongoing = true;
                    game.handend = false;
                    game.updatePos();
                    hand = new Hand(game.userArr, game.remainingChips, game.dealerPos, game.numPlayers);
                    hand.startHand();
                    System.out.println(Arrays.toString(hand.getRemainingStack()));
                    // function call to end hand
                    //game.remainingChips = hand.getRemainingStack();
                    // update user stats
                    game.updateUserStats();
                    game.handend = true;
                    Thread.sleep(10000);
                    for(int i = 0; i < 8; i++){
                        if(game.remainingChips[i] < 1 && game.userArr[i] != null){
                            game.deleteUser(game.userArr[i].getUsername());
                        }
                    }
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
