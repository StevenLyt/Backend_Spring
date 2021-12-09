package com.example.pokersc.entity;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Straight Flush
        List<Card> straightFlushHand = new ArrayList<>();
        straightFlushHand.add(new Card(Card.RANK.JACK, Card.SUIT.SPADES));
        straightFlushHand.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        straightFlushHand.add(new Card(Card.RANK.ACE, Card.SUIT.SPADES));
        straightFlushHand.add(new Card(Card.RANK.KING, Card.SUIT.SPADES));
        straightFlushHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        straightFlushHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        straightFlushHand.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Straight Flush Second Set
        List<Card> straightFlushHand2 = new ArrayList<>();
        straightFlushHand2.add(new Card(Card.RANK.JACK, Card.SUIT.SPADES));
        straightFlushHand2.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        straightFlushHand2.add(new Card(Card.RANK.NINE, Card.SUIT.SPADES));
        straightFlushHand2.add(new Card(Card.RANK.KING, Card.SUIT.SPADES));
        straightFlushHand2.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        straightFlushHand2.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        straightFlushHand2.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Four of a Kind
        List<Card> fourOfAKindHand = new ArrayList<>();
        fourOfAKindHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        fourOfAKindHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.CLUBS));
        fourOfAKindHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.DIAMONDS));
        fourOfAKindHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.HEARTS));
        fourOfAKindHand.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        fourOfAKindHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        fourOfAKindHand.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Four of a Kind Second Set
        List<Card> fourOfAKindHand2 = new ArrayList<>();
        fourOfAKindHand2.add(new Card(Card.RANK.JACK, Card.SUIT.SPADES));
        fourOfAKindHand2.add(new Card(Card.RANK.JACK, Card.SUIT.CLUBS));
        fourOfAKindHand2.add(new Card(Card.RANK.JACK, Card.SUIT.DIAMONDS));
        fourOfAKindHand2.add(new Card(Card.RANK.JACK, Card.SUIT.HEARTS));
        fourOfAKindHand2.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        fourOfAKindHand2.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        fourOfAKindHand2.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Full House
        List<Card> fullHouseHand = new ArrayList<>();
        fullHouseHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        fullHouseHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.CLUBS));
        fullHouseHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.DIAMONDS));
        fullHouseHand.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        fullHouseHand.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        fullHouseHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        fullHouseHand.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Full House Second Set
        List<Card> fullHouseHand2 = new ArrayList<>();
        fullHouseHand2.add(new Card(Card.RANK.JACK, Card.SUIT.SPADES));
        fullHouseHand2.add(new Card(Card.RANK.JACK, Card.SUIT.CLUBS));
        fullHouseHand2.add(new Card(Card.RANK.JACK, Card.SUIT.DIAMONDS));
        fullHouseHand2.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        fullHouseHand2.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        fullHouseHand2.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        fullHouseHand2.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Flush
        List<Card> flushHand = new ArrayList<>();
        flushHand.add(new Card(Card.RANK.ACE, Card.SUIT.SPADES));
        flushHand.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        flushHand.add(new Card(Card.RANK.ACE, Card.SUIT.SPADES));
        flushHand.add(new Card(Card.RANK.KING, Card.SUIT.SPADES));
        flushHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        flushHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        flushHand.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Flush Second Set
        List<Card> flushHand2 = new ArrayList<>();
        flushHand2.add(new Card(Card.RANK.FOUR, Card.SUIT.SPADES));
        flushHand2.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        flushHand2.add(new Card(Card.RANK.THREE, Card.SUIT.SPADES));
        flushHand2.add(new Card(Card.RANK.KING, Card.SUIT.SPADES));
        flushHand2.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        flushHand2.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        flushHand2.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Straight
        List<Card> straightHand = new ArrayList<>();
        straightHand.add(new Card(Card.RANK.JACK, Card.SUIT.HEARTS));
        straightHand.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        straightHand.add(new Card(Card.RANK.ACE, Card.SUIT.SPADES));
        straightHand.add(new Card(Card.RANK.KING, Card.SUIT.SPADES));
        straightHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        straightHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        straightHand.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Straight Second Set
        List<Card> straightHand2 = new ArrayList<>();
        straightHand2.add(new Card(Card.RANK.JACK, Card.SUIT.HEARTS));
        straightHand2.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        straightHand2.add(new Card(Card.RANK.NINE, Card.SUIT.SPADES));
        straightHand2.add(new Card(Card.RANK.KING, Card.SUIT.SPADES));
        straightHand2.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        straightHand2.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        straightHand2.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Set
        List<Card> setHand = new ArrayList<>();
        setHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        setHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.CLUBS));
        setHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.DIAMONDS));
        setHand.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        setHand.add(new Card(Card.RANK.THREE, Card.SUIT.SPADES));
        setHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        setHand.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Set Second Set
        List<Card> setHand2 = new ArrayList<>();
        setHand2.add(new Card(Card.RANK.JACK, Card.SUIT.SPADES));
        setHand2.add(new Card(Card.RANK.JACK, Card.SUIT.CLUBS));
        setHand2.add(new Card(Card.RANK.JACK, Card.SUIT.DIAMONDS));
        setHand2.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        setHand2.add(new Card(Card.RANK.THREE, Card.SUIT.SPADES));
        setHand2.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        setHand2.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Two Pairs
        List<Card> twoPairHand = new ArrayList<>();
        twoPairHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        twoPairHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.CLUBS));
        twoPairHand.add(new Card(Card.RANK.JACK, Card.SUIT.DIAMONDS));
        twoPairHand.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        twoPairHand.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        twoPairHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        twoPairHand.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // Two Pairs Second Set
        List<Card> twoPairHand2 = new ArrayList<>();
        twoPairHand2.add(new Card(Card.RANK.THREE, Card.SUIT.SPADES));
        twoPairHand2.add(new Card(Card.RANK.THREE, Card.SUIT.CLUBS));
        twoPairHand2.add(new Card(Card.RANK.JACK, Card.SUIT.DIAMONDS));
        twoPairHand2.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        twoPairHand2.add(new Card(Card.RANK.TEN, Card.SUIT.SPADES));
        twoPairHand2.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        twoPairHand2.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // One Pair
        List<Card> pairHand = new ArrayList<>();
        pairHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        pairHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.CLUBS));
        pairHand.add(new Card(Card.RANK.FOUR, Card.SUIT.DIAMONDS));
        pairHand.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        pairHand.add(new Card(Card.RANK.THREE, Card.SUIT.SPADES));
        pairHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        pairHand.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // One Pair Second Set
        List<Card> pairHand2 = new ArrayList<>();
        pairHand2.add(new Card(Card.RANK.JACK, Card.SUIT.SPADES));
        pairHand2.add(new Card(Card.RANK.JACK, Card.SUIT.CLUBS));
        pairHand2.add(new Card(Card.RANK.FOUR, Card.SUIT.DIAMONDS));
        pairHand2.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        pairHand2.add(new Card(Card.RANK.THREE, Card.SUIT.SPADES));
        pairHand2.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        pairHand2.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // High Card
        List<Card> highCardHand = new ArrayList<>();
        highCardHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.SPADES));
        highCardHand.add(new Card(Card.RANK.EIGHT, Card.SUIT.CLUBS));
        highCardHand.add(new Card(Card.RANK.SIX, Card.SUIT.DIAMONDS));
        highCardHand.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        highCardHand.add(new Card(Card.RANK.THREE, Card.SUIT.SPADES));
        highCardHand.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        highCardHand.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));
        // High Card Second Set
        List<Card> highCardHand2 = new ArrayList<>();
        highCardHand2.add(new Card(Card.RANK.JACK, Card.SUIT.SPADES));
        highCardHand2.add(new Card(Card.RANK.EIGHT, Card.SUIT.CLUBS));
        highCardHand2.add(new Card(Card.RANK.SIX, Card.SUIT.DIAMONDS));
        highCardHand2.add(new Card(Card.RANK.TEN, Card.SUIT.HEARTS));
        highCardHand2.add(new Card(Card.RANK.THREE, Card.SUIT.SPADES));
        highCardHand2.add(new Card(Card.RANK.TWO, Card.SUIT.HEARTS));
        highCardHand2.add(new Card(Card.RANK.FIVE, Card.SUIT.HEARTS));

        PokerHand straightFlushResult = HandRanker.getInstance().getRank(straightFlushHand);
        PokerHand fourOfAKindResult = HandRanker.getInstance().getRank(fourOfAKindHand);
        PokerHand fullHouseResult = HandRanker.getInstance().getRank(fullHouseHand);
        PokerHand flushResult = HandRanker.getInstance().getRank(flushHand);
        PokerHand straightResult = HandRanker.getInstance().getRank(straightHand);
        PokerHand setResult = HandRanker.getInstance().getRank(setHand);
        PokerHand twoPairResult = HandRanker.getInstance().getRank(twoPairHand);
        PokerHand pairResult = HandRanker.getInstance().getRank(pairHand);
        PokerHand highCardResult = HandRanker.getInstance().getRank(highCardHand);

        // Second Set
        PokerHand straightFlushResult2 = HandRanker.getInstance().getRank(straightFlushHand2);
        PokerHand fourOfAKindResult2 = HandRanker.getInstance().getRank(fourOfAKindHand2);
        PokerHand fullHouseResult2 = HandRanker.getInstance().getRank(fullHouseHand2);
        PokerHand flushResult2 = HandRanker.getInstance().getRank(flushHand2);
        PokerHand straightResult2 = HandRanker.getInstance().getRank(straightHand2);
        PokerHand setResult2 = HandRanker.getInstance().getRank(setHand2);
        PokerHand twoPairResult2 = HandRanker.getInstance().getRank(twoPairHand2);
        PokerHand pairResult2 = HandRanker.getInstance().getRank(pairHand2);
        PokerHand highCardResult2 = HandRanker.getInstance().getRank(highCardHand2);

        // Descending Ranking Test
        // the result should all be 1 as the ranking is ordered
        System.out.println(straightFlushResult.compareTo(fourOfAKindResult));
        System.out.println(fourOfAKindResult.compareTo(fullHouseResult));
        System.out.println(fullHouseResult.compareTo(flushResult));
        System.out.println(flushResult.compareTo(straightResult));
        System.out.println(straightResult.compareTo(setResult));
        System.out.println(setResult.compareTo(twoPairResult));
        System.out.println(twoPairResult.compareTo(pairResult));
        System.out.println(pairResult.compareTo(highCardResult));

        // Same Ranking Test
        // the result should all be 1 because set one is set to be higher ranked than set 2
        System.out.println(straightFlushResult.compareTo(straightFlushResult2));
        System.out.println(fourOfAKindResult.compareTo(fourOfAKindResult2));
        System.out.println(fullHouseResult.compareTo(fullHouseResult2));
        System.out.println(flushResult.compareTo(flushResult2));
        System.out.println(straightResult.compareTo(straightResult2));
        System.out.println(setResult.compareTo(setResult2));
        System.out.println(twoPairResult.compareTo(twoPairResult2));
        System.out.println(pairResult.compareTo(pairResult2));
    }
}
