package com.example.pokersc.entity;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Card> firstHand = new ArrayList<>();
        firstHand.add(new Card(Card.RANK.TWO, Card.SUIT.CLUBS));
        firstHand.add(new Card(Card.RANK.SEVEN, Card.SUIT.HEARTS));
        firstHand.add(new Card(Card.RANK.SEVEN, Card.SUIT.SPADES));
        firstHand.add(new Card(Card.RANK.ACE, Card.SUIT.HEARTS));
        firstHand.add(new Card(Card.RANK.JACK, Card.SUIT.CLUBS));
        firstHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        firstHand.add(new Card(Card.RANK.KING, Card.SUIT.SPADES));
        List<Card> secondHand = new ArrayList<>();
        secondHand.add(new Card(Card.RANK.TWO, Card.SUIT.DIAMONDS));
        secondHand.add(new Card(Card.RANK.TWO, Card.SUIT.SPADES));
        secondHand.add(new Card(Card.RANK.SEVEN, Card.SUIT.SPADES));
        secondHand.add(new Card(Card.RANK.ACE, Card.SUIT.HEARTS));
        secondHand.add(new Card(Card.RANK.JACK, Card.SUIT.CLUBS));
        secondHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        secondHand.add(new Card(Card.RANK.KING, Card.SUIT.SPADES));

        PokerHand result1 = HandRanker.getInstance().getRank(firstHand);
        PokerHand result2 = HandRanker.getInstance().getRank(secondHand);
        System.out.println(result1.compareTo(result2));
    }
}
