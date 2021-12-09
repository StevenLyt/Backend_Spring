package com.example.pokersc.entity;

import java.util.List;

public class PokerHand implements Comparable {
    public enum HAND_RANK {
        HIGH_CARD(0),
        PAIR(1),
        TWO_PAIR(2),
        THREE_OF_A_KIND(3),
        STRAIGHT(4),
        FLUSH(5),
        FULL_HOUSE(6),
        FOUR_OF_A_KIND(7),
        STRAIGHT_FLUSH(8);

        private final int value;

        HAND_RANK(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private final HAND_RANK handRank;
    private final List<Card> cards;

    public PokerHand(HAND_RANK handRank, List<Card> cards) {
        if(cards.size() != HandRanker.FULL_HAND) throw new IllegalArgumentException("You have to pass five cards");
        this.handRank = handRank;
        this.cards = cards;
        //sorts for compareTo method
    }

    @Override
    public String toString() {
        return "HandValue{" +
                "handRank=" + handRank +
                ", allCards=" + cards +
                '}';
    }
    @Override
    public int compareTo(Object o) {
        if(this == o) return 0;
        if(o == null || getClass() != o.getClass()) return 0;

        PokerHand pokerHand = (PokerHand) o;

        if(handRank.getValue() > pokerHand.handRank.getValue()) return 1;
        if(handRank.getValue() < pokerHand.handRank.getValue()) return -1;

        for(int index = 0; index < cards.size(); index++) {
            if(cards.get(index).getRank().getValue() > pokerHand.cards.get(index).getRank().getValue()) return 1;
            if(cards.get(index).getRank().getValue() < pokerHand.cards.get(index).getRank().getValue()) return -1;
        }
        return 0;
    }
}