package com.example.pokersc.entity;

import java.util.List;

public class Hand {

    private User[] playerArr;
    private int[] posArr;
    private Deck deck;
    private CommunityCards communityCards;

    public Hand(User[] uL, int[] pl){
        this.playerArr = uL;
        this.posArr = pl;
        this.deck = new Deck();
        this.deck.shuffle();
    }

    public void startHand(){
        for(int i = 0; i < 8; i++){
            if(posArr[i] == 0){

            }
        }
    }

}
