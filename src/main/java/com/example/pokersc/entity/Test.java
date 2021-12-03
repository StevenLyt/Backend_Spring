package com.example.pokersc.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Test HandRanker
//        List<Card> firstHand = new ArrayList<>();
//        firstHand.add(new Card(Card.RANK.TWO, Card.SUIT.CLUBS));
//        firstHand.add(new Card(Card.RANK.KING, Card.SUIT.DIAMONDS));
//        firstHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.DIAMONDS));
//        firstHand.add(new Card(Card.RANK.TEN, Card.SUIT.CLUBS));
//        firstHand.add(new Card(Card.RANK.FOUR, Card.SUIT.CLUBS));
//        firstHand.add(new Card(Card.RANK.THREE, Card.SUIT.CLUBS));
//        firstHand.add(new Card(Card.RANK.ACE, Card.SUIT.SPADES));
//        List<Card> secondHand = new ArrayList<>();
//        secondHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.DIAMONDS));
//        secondHand.add(new Card(Card.RANK.JACK, Card.SUIT.CLUBS));
//        secondHand.add(new Card(Card.RANK.TEN, Card.SUIT.CLUBS));
//        secondHand.add(new Card(Card.RANK.SEVEN, Card.SUIT.CLUBS));
//        secondHand.add(new Card(Card.RANK.TWO, Card.SUIT.SPADES));
//        secondHand.add(new Card(Card.RANK.SIX, Card.SUIT.HEARTS));
//        secondHand.add(new Card(Card.RANK.QUEEN, Card.SUIT.HEARTS));
//
//        PokerHand result1 = HandRanker.getInstance().getRank(firstHand);
//        System.out.println(result1);
//        PokerHand result2 = HandRanker.getInstance().getRank(secondHand);
//        System.out.println(result2);
//        System.out.println(result1.compareTo(result2));

        // Test User
        User user1 = new User("steven", "123456", "baidu.com");
        user1.addAmount(1000);
        user1.setId(1);
//        System.out.println(user1.getId());
//        System.out.println(user1.getUsername());
//        System.out.println(user1.getPassword());
//        System.out.println(user1.getProfile_url());
//        System.out.println(user1.getBuyin());
        User user2 = new User("steven2", "good", "google.com");
        user2.addAmount(100);
        user2.setId(2);
//        System.out.println(user2.getId());
//        System.out.println(user2.getUsername());
//        System.out.println(user2.getPassword());
//        System.out.println(user2.getProfile_url());
//        System.out.println(user2.getBuyin());

        //Test Game
        User user3 = new User("steven3", "good", "google.com");
        user3.setId(3);
        User user4 = new User("steven4", "good", "google.com");
        user4.setId(4);
        User user5 = new User("steven5", "good", "google.com");
        user5.setId(5);
        User user6 = new User("steven6", "good", "google.com");
        user6.setId(6);
        User user7 = new User("steven7", "good", "google.com");
        user7.setId(7);
        User user8 = new User("steven8", "good", "google.com");
        user8.setId(8);
//        Game game = new Game();
//        game.addUser(user1, 10, 0);
//        game.addUser(user2, 100, 1);
//        game.addUser(user3, 1000, 2);
//        game.addUser(user4, 10000, 3);
//        game.addUser(user5, 100000, 4);
//        game.addUser(user6, 1000, 5);
//        game.addUser(user7, 100, 6);
//        game.addUser(user8, 10, 7);
//        game.updatePos();
//
//        game.rebuy(3, 1000);
//        System.out.println(game.getBalance(3));
//
//        game.deleteUser(3);
//        User[] users = game.getAllUsers();
//        if (users[2] == null) {
//            System.out.println("User 3 is deleted!");
//        }
    }
}
