package com.example.pokersc.entity;

import java.util.Arrays;
import java.util.Collections;

public class Deck {
	
	private int numCardsUsed;
	private Card[] deck;
	
	public Deck() {
		deck = new Card[52];
		int index = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 1; j < 14; j++) {
				deck[index] = new Card(j, i);
				index ++;
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
