package com.example.pokersc.entity;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Card> firstHand = new ArrayList<>();
        firstHand.add(new Card(Card.RANK.TWO, Card.SUIT.CLUBS));
        firstHand.add(new Card(Card.RANK.KING, Card.SUIT.DIAMONDS));
        firstHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.DIAMONDS));
        firstHand.add(new Card(Card.RANK.TEN, Card.SUIT.CLUBS));
        firstHand.add(new Card(Card.RANK.FOUR, Card.SUIT.CLUBS));
        firstHand.add(new Card(Card.RANK.THREE, Card.SUIT.CLUBS));
        firstHand.add(new Card(Card.RANK.ACE, Card.SUIT.SPADES));
//        List<Card> secondHand = new ArrayList<>();
//        secondHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.DIAMONDS));
//        secondHand.add(new Card(Card.RANK.JACK, Card.SUIT.CLUBS));
//        secondHand.add(new Card(Card.RANK.TEN, Card.SUIT.CLUBS));
//        secondHand.add(new Card(Card.RANK.SEVEN, Card.SUIT.CLUBS));
//        secondHand.add(new Card(Card.RANK.TWO, Card.SUIT.SPADES));
//        secondHand.add(new Card(Card.RANK.SIX, Card.SUIT.HEARTS));
//        secondHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.HEARTS));

        PokerHand result1 = HandRanker.getInstance().getRank(firstHand);
        System.out.println(result1);
        //PokerHand result2 = HandRanker.getInstance().getRank(secondHand);
        // System.out.println(result1.compareTo(result2));
    }
}
