package com.example.pokersc.entity;

import java.util.Arrays;
import java.util.Collections;

public class Deck {
	
	private int num_cardsUsed; 
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
		num_cardsUsed = 0;
	};
	
	public void shuffle() {
		Collections.shuffle(Arrays.asList(deck));
	};
	
	public int cardsLeft() {
		return 52 - this.num_cardsUsed;
	}
	
	public Card dealCard() throws Exception {
		
		if(num_cardsUsed == 52) {
            		throw new Exception("No cards are left in the deck.");
		}
		// pop and return the card at the top
		num_cardsUsed++;
		return deck[num_cardsUsed - 1];
	}
	
}
