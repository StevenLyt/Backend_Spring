package com.example.pokersc.entity;

public class GameThread extends Thread{

    private Game game;

    public GameThread(){
        this.game = new Game();
    }
    public void run(){
        while(game.numPlayers > 1){
            game.updatePos();
            Hand hand = new Hand(game.userArr, game.dealerPos, game.numPlayers);
            hand.startHand();
        }
    }

    public void main(){
        GameThread gameThread = new GameThread();
        gameThread.start();
    }
}
