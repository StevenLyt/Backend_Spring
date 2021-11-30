package com.example.pokersc.entity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hand {

    private User[] playerArr;
    private int[] posArr;
    private int numPlayers;
    private PlayerCards[] playerCards;
    private Deck deck;
    private Card[] flop;
    private Card turn;
    private Card river;

    public Hand(User[] uL, int[] pL, int nP){
        this.playerArr = uL;
        this.posArr = pL;
        this.numPlayers = nP;
        this.playerCards = new PlayerCards[8];
        this.deck = new Deck();
        this.flop = new Card[3];
    }

    public void startHand() {
        //shuffle the deck
        this.deck.shuffle();
        //start dealing hand to players and the board
        for(int i = 0; i < 8; i++){
            if(posArr[i] == 0){
                this.dealCardsToPlayers(i);
            }
        }
        this.dealCommunityCards();
    }

    // deal cards given the position of button
    public void dealCardsToPlayers(int buttonPos) {
        int start = (buttonPos + 2) % 8;
        int numCardsDealt = 0;
        while(numCardsDealt != 2*numPlayers){
            this.playerCards[start].receiveCard(deck.dealCard());
            numCardsDealt++;
            start++;
            start %= 8;
        }
    }

    // deal the board
    public void dealCommunityCards(){
        //burn one card before flop
        this.deck.dealCard();
        //deal flop
        for(int i = 0; i < 3; i++){
            this.flop[i] = this.deck.dealCard();
        }
        //burn one card before turn
        this.deck.dealCard();
        //deal turn
        turn = this.deck.dealCard();
        //burn one card before river
        this.deck.dealCard();
        //deal river
        river = this.deck.dealCard();
    }

    // Above is written by Jason
    // Below is written by Peter
    public void startUserThread() {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (User u : playerArr) {
            exec.execute(u);
        }
        exec.shutdown();
        while(!exec.isTerminated()){Thread.yield();}
    }

    public Action getRecentAction() {
        return new Action(playerArr[0], Action.Act.CHECK);
    }
}
