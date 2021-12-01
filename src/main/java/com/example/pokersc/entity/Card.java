package com.example.pokersc.entity;

public class Card implements Comparable<Card> {
    public static enum SUIT {
        SPADES,
        HEARTS,
        CLUBS,
        DIAMONDS
    }

    public static enum RANK {
        TWO(2), THREE(3), FOUR(4), FIVE(5),
        SIX(6), SEVEN(7), EIGHT(8), NINE(9),
        TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

        private final int value;

        private RANK(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private final RANK rank;
    private final SUIT suit;

    public Card(RANK rank, SUIT suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public RANK getRank() {
        return rank;
    }

    public SUIT getSuit() {
        return suit;
    }
    @Override
    public String toString() {
        return "Card{" +
                "rank=" + rank +
                ", suit=" + suit +
                '}';
    }

    @Override
    public int compareTo(Card card) {
        if(this == card) return 0;
        if(card == null || getClass() != card.getClass()) return 0;


        if(rank.getValue() > card.rank.getValue()) return -1;
        if(rank.getValue() < card.rank.getValue()) return 1;
        return 0;
    }
    /*public final static int SPADES = 0;   // Codes for the 4 suits
    public final static int HEARTS = 1;
    public final static int DIAMONDS = 2;
    public final static int CLUBS = 3;

    private final int value; // 2-14
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
        
        if (v < 2 || v > 14){
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
    	switch (this.suit) {
            case 0:
                suit_asString = "Spades";
                break;
            case 1:
                suit_asString = "Hearts";
                break;
            case 2:
                suit_asString = "Diamonds";
                break;
            case 3:
                suit_asString = "Clubs";
                break;
    	}
    	switch (this.value) {
            case 2:
                value_asString = "2";
                break;
            case 3:
                value_asString = "3";
                break;
            case 4:
                value_asString = "4";
                break;
            case 5:
                value_asString = "5";
                break;
            case 6:
                value_asString = "6";
                break;
            case 7:
                value_asString = "7";
                break;
            case 8:
                value_asString = "8";
                break;
            case 9:
                value_asString = "9";
                break;
            case 10:
                value_asString = "10";
                break;
            case 11:
                value_asString = "Jack";
                break;
            case 12:
                value_asString = "Queen";
                break;
            case 13:
                value_asString = "King";
                break;
            case 14:
                value_asString = "Ace";
                break;
    	}
    	return value_asString + " of " + suit_asString;
    }

    @Override
    public int compareTo(Object o) {
        if(this == o) return 0;
        if(o == null || getClass() != o.getClass()) return 0;

        Card card = (Card) o;

        if(this.value > card.getCardValue()) return -1;
        if(this.value < card.getCardValue()) return 1;
        return 0;
    }*/
}
