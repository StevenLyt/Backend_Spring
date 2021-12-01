package com.example.pokersc.entity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hand {

    private User[] playerArr;
    private int dealerPos;
    private int numPlayers;
    private PlayerCards[] playerCards;
    private Deck deck;
    private Card[] flop;
    private Card turn;
    private Card river;
    private int actionOnWhichPlayer;

    public Hand(User[] userList, int dPos, int numP){
        this.playerArr = userList;
        this.dealerPos = dPos;
        this.numPlayers = numP;
        this.playerCards = new PlayerCards[8];
        this.deck = new Deck();
        this.flop = new Card[3];
        actionOnWhichPlayer = dealerPos + 3; //first action on UTG;
        actionOnWhichPlayer %= 8;
        while(playerArr[actionOnWhichPlayer] == null){
            actionOnWhichPlayer ++;
            actionOnWhichPlayer &= 8;
        }
    }

    public void startHand() {
        //shuffle the deck.
        this.deck.shuffle();
        //start dealing hand to players and the board
        this.dealCardsToPlayers(dealerPos);
        this.dealCommunityCards();

        //flop
        int numActionLeft = numPlayers;
        while(numActionLeft > 0){

        }
    }

    // deal cards given the position of button
    private void dealCardsToPlayers(int buttonPos) {
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
    private void dealCommunityCards(){
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
