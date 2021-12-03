package com.example.pokersc.entity;

import java.util.Arrays;
import java.util.Collections;

import static com.example.pokersc.entity.Card.RANK;
import static com.example.pokersc.entity.Card.SUIT;

public class Deck {
	
	private int numCardsUsed;
	private Card[] deck;
	
	public Deck() {
		deck = new Card[52];
		int index = 0;
		for (SUIT suit : SUIT.values()) {
			for (RANK rank : RANK.values()) {
				deck[index] = new Card(rank, suit);
				index++;
			}
		}
		numCardsUsed = 0;
	}
	
	public void shuffle() {
		Collections.shuffle(Arrays.asList(deck));
	}
	
	public int cardsLeft() {
		return 52 - this.numCardsUsed;
	}
	
	public Card dealCard() {
		// pop and return the card at the top
		numCardsUsed++;
		return deck[numCardsUsed - 1];
	}
	
}
