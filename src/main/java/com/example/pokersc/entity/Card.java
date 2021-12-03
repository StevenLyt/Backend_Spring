package com.example.pokersc.entity;

public class Card implements Comparable<Card> {
    public enum SUIT {
        SPADES,
        HEARTS,
        CLUBS,
        DIAMONDS
    }
    public enum RANK {
        TWO(2), THREE(3), FOUR(4), FIVE(5),
        SIX(6), SEVEN(7), EIGHT(8), NINE(9),
        TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

        private final int value;

        RANK(int value) {
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
        String[] suit = {"s","h","c","d"};
        String[] rank = {"2","3","4","5","6","7","8","9","10","j","q","k","a"};
        return suit[this.suit.ordinal()]+rank[this.rank.ordinal()];
    }

    @Override
    public int compareTo(Card card) {
        if (this == card) return 0;
        if (card == null || getClass() != card.getClass()) return 0;


        if (rank.getValue() > card.rank.getValue()) return -1;
        if (rank.getValue() < card.rank.getValue()) return 1;
        return 0;
    }
}
