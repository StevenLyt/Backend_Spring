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
                    game.updatePos();
                    hand = new Hand(game.userArr, game.remainingChips, game.dealerPos, game.numPlayers);
                    hand.startHand();
                    System.out.println(Arrays.toString(hand.getRemainingStack()));
                    // function call to end hand
                    //game.remainingChips = hand.getRemainingStack();
                    // update user stats
                    game.updateUserStats();
                    Thread.sleep(10000);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
