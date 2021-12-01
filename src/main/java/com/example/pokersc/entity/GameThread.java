package com.example.pokersc.entity;

public class GameThread extends Thread{

    private Game game;
    private Action currentAction = null;

    public GameThread(){
        this.game = new Game();
    }

    public void run(){
        while(true) {
            if(game.numPlayers < 3){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                game.updatePos();
                Hand hand = new Hand(game.userArr, game.dealerPos, game.numPlayers);
                hand.startHand();
                while(this.currentAction == null){}
            }
        }
    }

    public void addAction(Action action) {
        this.currentAction = action;
    }

    public void main(){
        GameThread gameThread = new GameThread();
        gameThread.start();
    }
}
