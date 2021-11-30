package com.example.pokersc.entity;

public class Card {

    public final static int SPADES = 0;   // Codes for the 4 suits
    public final static int HEARTS = 1;
    public final static int DIAMONDS = 2;
    public final static int CLUBS = 3;

    private final int value; // 1-13
    private final int suit; 
    
    //constructor
    public Card(int v, int s){
        if (s != SPADES && s != HEARTS && s != DIAMONDS && 
            s != CLUBS) {
            throw new IllegalArgumentException("Illegal playing card suit");
        }
        else {
        	this.suit = s;
        }
        
        if (v < 1 || v > 13){
            throw new IllegalArgumentException("Illegal playing card value");
        }
        else {
        	this.value = v;
        }
    }
    
    public int getCardValue(){
        return this.value;
    }
    
    public int getCardSuit(){
        return this.suit; // 0 = CLUB, 1 = DIAMOND, 2 = HEART, 3 = SPADE
    }
    
    public String toSring() {
    	String suit_asString = "";
    	String value_asString = "";
    	switch(this.suit) {
    	case 0: suit_asString = "Spades";
    	case 1: suit_asString = "Hearts";
    	case 2: suit_asString = "Diamonds";
    	case 3: suit_asString = "Clubs";
    	}
    	switch (this.value) {
        case 1:   value_asString = "Ace";
        case 2:   value_asString = "2";
        case 3:   value_asString = "3";
        case 4:   value_asString = "4";
        case 5:   value_asString = "5";
        case 6:   value_asString = "6";
        case 7:   value_asString = "7";
        case 8:   value_asString = "8";
        case 9:   value_asString = "9";
        case 10:  value_asString = "10";
        case 11:  value_asString = "Jack";
        case 12:  value_asString = "Queen";
    	}
    	return value_asString + " of " + suit_asString;
    }
}
