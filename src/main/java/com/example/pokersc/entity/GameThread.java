package com.example.pokersc.entity;

public class GameThread extends Thread {

    private Game game;

    public GameThread() {
        this.game = new Game();
    }

    public void run() {
        try {
            while (true) {
                if (game.numPlayers < 3) {
                    Thread.sleep(1000);
                } else {
                    game.updatePos();
                    Hand hand = new Hand(game.userArr, game.remainingChips, game.dealerPos, game.numPlayers);
                    hand.startHand();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void main() {
        GameThread gameThread = new GameThread();
        gameThread.start();
    }
}
