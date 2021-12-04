package com.example.pokersc.entity;

public class GameThread extends Thread{

    public Game game;
    public Hand hand;

    public GameThread(){
        this.game = new Game();
        this.hand = null;
    }

    public void run(){
        //try {
            while (true) {
                if (game.numPlayers < 3) {
                    continue;
                } else {
                    game.updatePos();
                    hand = new Hand(game.userArr, game.remainingChips, game.dealerPos, game.numPlayers);
                    hand.startHand();
                    // function call to end hand
                    game.remainingChips = hand.getRemainingStack();
                    hand.saveStats();
                }
            }
        //}
        /*catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
