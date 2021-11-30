package com.example.pokersc.entity;

public class PlayerCards {
    private Card[] playerHand;

    public PlayerCards(Card c1, Card c2){
        this.playerHand = new Card[2];
        this.playerHand[0] = c1;
        this.playerHand[1] = c2;
    }
}
