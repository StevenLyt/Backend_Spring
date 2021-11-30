package com.example.pokersc.entity;

import java.util.List;

public class Hand {

    private User[] playerArr;
    private int[] posArr;
    private int numPlayers;
    private Deck deck;
    private CommunityCards communityCards;
    private PlayerCards[] playerCards;

    public Hand(User[] uL, int[] pL, int nP){
        this.playerArr = uL;
        this.posArr = pL;
        this.numPlayers = nP;
        this.playerCards = new PlayerCards[8];
        this.deck = new Deck();
        this.deck.shuffle();

    }

    public void startHand() {
        for(int i = 0; i < 8; i++){
            if(posArr[i] == 0){
                dealCards(i);
            }
        }
    }
    // start to deal cards given the position of button
    public void dealCards(int buttonPos) {
        int start = (buttonPos + 2) % 8;
        int numCardsDealt = 0;
        while(numCardsDealt != 2*numPlayers){
            this.playerCards[start].receiveCard(deck.dealCard());
            numCardsDealt++;
            start++;
            start %= 8;
        }
    }

}
