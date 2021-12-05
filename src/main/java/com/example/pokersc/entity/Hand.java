package com.example.pokersc.entity;

import com.example.pokersc.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hand {

    private User[] playerArr;
    private boolean[] active;
    private int [] remainingStack;
    private int dealerPos;
    private int numPlayers;
    private PlayerCards[] playerCards;
    private Deck deck;
    private int state = 0; // 0 = preflop,

    private int winnerPos = -1;
    public int numActionLeft;

    @Autowired
    private UsersRepository usersRepository;

    public int getWinnerPos() {
        return winnerPos;
    }

    public User[] getPlayerArr() {
        return playerArr;
    }

    public boolean[] getActive() {
        return active;
    }

    public int[] getRemainingStack() {
        return remainingStack;
    }

    public int getDealerPos() {
        return dealerPos;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public PlayerCards[] getPlayerCards() {
        return playerCards;
    }

    public Deck getDeck() {
        return deck;
    }

    public Card[] getCommunityCards() {
        return communityCards;
    }

    public int getPot() {
        return pot;
    }

    public int getActionOnWhichPlayer() {
        return actionOnWhichPlayer;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public int[] getChipPutInThisPhase() {
        return chipPutInThisPhase;
    }

    public int getMaxBetInThisPhase() {
        return maxBetInThisPhase;
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    private Card[] communityCards;
    private int pot;
    private int actionOnWhichPlayer;
    private int smallBlind;
    private int bigBlind;
    private int[] chipPutInThisPhase;
    private int maxBetInThisPhase;
    private Action currentAction = null;
    // for testing

    public Hand(User[] userList, int[] chips, int dPos, int numP){
        this.playerArr = userList;
        this.remainingStack = chips;
        this.dealerPos = dPos;
        this.numPlayers = numP;
        this.playerCards = new PlayerCards[8];
        this.active = new boolean[8];
        this.chipPutInThisPhase = new int[8];
        for(int i = 0; i < 8; i++){
            playerCards[i] = new PlayerCards();
            chipPutInThisPhase[i] = 0;
            if(playerArr[i] != null) {
                active[i] = true;
            }
            else {
                active[i] = false;
            }
        }
        this.deck = new Deck();
        this.communityCards = new Card[5];
        this.smallBlind = this.dealerPos + 1;
        this.smallBlind %= 8;
        while(!active[smallBlind]){
            this.smallBlind ++;
            this.smallBlind %= 8;
        }
        this.bigBlind = this.smallBlind + 1;
        this.bigBlind %= 8;
        while(!active[bigBlind]){
            this.bigBlind ++;
            this.bigBlind %= 8;
        }
        actionOnWhichPlayer = bigBlind + 1; //first action on UTG;
        actionOnWhichPlayer %= 8;
        while(!active[actionOnWhichPlayer]){
            actionOnWhichPlayer ++;
            actionOnWhichPlayer %= 8;
        }
    }

    public void startHand() {
        //shuffle the deck.
        this.deck.shuffle();
        //start dealing hand to players and the board
        this.dealCardsToPlayers(dealerPos);
        this.dealCommunityCards();

        this.maxBetInThisPhase = 2; // default game 1/2
        this.pot = 3; // small blind 1

        this.chipPutInThisPhase[smallBlind] = 1;
        this.chipPutInThisPhase[bigBlind] = 2;

        //pre-flop
        for(int i = 0; i < 8; i++){
            this.chipPutInThisPhase[i] = 0;
        }
        this.chipPutInThisPhase[smallBlind] = 1;
        this.remainingStack[smallBlind] -= 1;
        this.chipPutInThisPhase[bigBlind] = 2;
        this.remainingStack[bigBlind] -= 2;
        this.maxBetInThisPhase = 2;
        numActionLeft = numPlayers;
        while(!readyForNextRound() || numActionLeft > 0){
            while (true){
                if(currentAction != null){
                    break;
                }
            }
            doAction(actionOnWhichPlayer);
            actionOnWhichPlayer ++; //first action on UTG;
            actionOnWhichPlayer %= 8;
            while(!active[actionOnWhichPlayer]){
                actionOnWhichPlayer ++;
                actionOnWhichPlayer %= 8;
            }
            this.currentAction = null;
            numActionLeft --;
        }
        //end flop

        state = 3;
        //flop
        this.initializePhase();
        numActionLeft = numPlayers;
        this.actionOnWhichPlayer = smallBlind;
        while(!active[actionOnWhichPlayer]){
            actionOnWhichPlayer ++;
            actionOnWhichPlayer %= 8;
        }
        while(!readyForNextRound() || numActionLeft > 0) {
            while (true){
                if(currentAction != null){
                    break;
                }
            }
            doAction(actionOnWhichPlayer);
            actionOnWhichPlayer ++; //first action on UTG;
            actionOnWhichPlayer %= 8;
            while(playerArr[actionOnWhichPlayer] == null){
                actionOnWhichPlayer ++;
                actionOnWhichPlayer %= 8;
            }
            this.currentAction = null;
            numActionLeft --;
        }
        //end flop

        state = 4;
        //turn
        this.initializePhase();
        numActionLeft = numPlayers;
        this.actionOnWhichPlayer = smallBlind;
        while(!active[actionOnWhichPlayer]){
            actionOnWhichPlayer ++;
            actionOnWhichPlayer %= 8;
        }
        while(!readyForNextRound() || numActionLeft > 0) {
            while (true){
                if(currentAction != null){
                    break;
                }
            }
            doAction(actionOnWhichPlayer);
            actionOnWhichPlayer ++; //first action on UTG;
            actionOnWhichPlayer %= 8;
            while(playerArr[actionOnWhichPlayer] == null){
                actionOnWhichPlayer ++;
                actionOnWhichPlayer %= 8;
            }
            this.currentAction = null;
            numActionLeft --;
        }
        // end turn

        state = 5;
        //river
        this.initializePhase();
        numActionLeft = numPlayers;
        this.actionOnWhichPlayer = smallBlind;
        while(!active[actionOnWhichPlayer]){
            actionOnWhichPlayer ++;
            actionOnWhichPlayer %= 8;
        }
        while(!readyForNextRound() || numActionLeft > 0) {
            while (true){
                if(currentAction != null){
                    break;
                }
            }
            doAction(actionOnWhichPlayer);
            actionOnWhichPlayer ++; //first action on UTG;
            actionOnWhichPlayer %= 8;
            while(playerArr[actionOnWhichPlayer] == null){
                actionOnWhichPlayer ++;
                actionOnWhichPlayer %= 8;
            }
            this.currentAction = null;
            numActionLeft --;
        }
        //end river
        this.winnerPos = getWinner();
        User winner =  playerArr[this.winnerPos];
        winner.setTotal_win(winner.getTotal_win() + 1);
        this.remainingStack[winnerPos] += pot;

    }

    // initialize phase (flop, turn, river);
    public void initializePhase(){
        for(int i = 0; i < 8; i++){
            this.chipPutInThisPhase[i] = 0;
        }
        this.maxBetInThisPhase = 0;
    }

    // check if every player puts same amount or folds
    public boolean readyForNextRound(){
        int amount = 0;
        for(int i = 0; i < 8; i++){
            if(active[i]) {
                amount = chipPutInThisPhase[i];
                break;
            }
        }
        for(int i = 0; i < 8; i++){
            if(active[i] && chipPutInThisPhase[i] != amount) {
               return false;
            }
        }
        return true;
    }

    // update active arr, chipPutInThisPhase arr, maxBetInThisPhase, pot, numPlayer
    public void doAction(int pos){
        if(currentAction.getAct() == Action.Act.FOLD){
            this.active[pos] = false;
            this.numPlayers --;
        }
        else if(currentAction.getAct() == Action.Act.CALL){
            this.pot += (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
            this.remainingStack[pos] -= (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
            this.chipPutInThisPhase[pos] = maxBetInThisPhase;
        }
        else if(currentAction.getAct() == Action.Act.RAISE){
            maxBetInThisPhase = currentAction.getAmount();
            this.remainingStack[pos] -= (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
            this.pot += (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
            this.chipPutInThisPhase[pos] = maxBetInThisPhase;
        }
    }

    public void addAction(Action action) {
        try {
            while (currentAction != null) {
                Thread.sleep(1000);
            }
            this.currentAction = action;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // deal cards given the position of button
    private void dealCardsToPlayers(int buttonPos) {
        int start = (buttonPos + 1) % 8;
        int numCardsDealt = 0;
        while(numCardsDealt != 2*numPlayers){
            if(playerArr[start] != null) {
                this.playerCards[start].receiveCard(deck.dealCard());
                numCardsDealt++;
            }
            start++;
            start %= 8;

        }
    }

    // deal the board
    private void dealCommunityCards(){
        //burn one card before flop
        this.deck.dealCard();

        //deal flop
        for(int i = 0; i < 3; i++){
            this.communityCards[i] = this.deck.dealCard();
        }
        //burn one card before turn
        this.deck.dealCard();
        //deal turn
        this.communityCards[3] = this.deck.dealCard();
        //burn one card before river
        this.deck.dealCard();
        //deal river
        this.communityCards[4] = this.deck.dealCard();
    }

    // Above is written by Jason
    public boolean checkAction(int pos, Action action) {
        if (action.getAct() == Action.Act.CHECK) {
            if (this.chipPutInThisPhase[pos] != this.maxBetInThisPhase)
                return false;
        } else if (action.getAct() == Action.Act.RAISE) {
            if (action.getAmount() < 2 * this.maxBetInThisPhase)
                return false;
            else if (this.remainingStack[pos] < action.getAmount())
                return false;
        } else if (action.getAct() == Action.Act.CALL) {
            if (this.remainingStack[pos] < this.maxBetInThisPhase - this.chipPutInThisPhase[pos])
                return false;
        }
        return true;
    }

    private int getWinner() {
        int winner = 0;
        for (int i = 1; i < 8; i++) {
            if (!active[i])
                continue;
            winner = HandRanker.getInstance().getRank(Stream.concat(Arrays.asList(this.communityCards).stream(), Arrays.asList(playerCards[winner].getPlayerHand()).stream()).collect(Collectors.toList())).compareTo(HandRanker.getInstance().getRank(Stream.concat(Arrays.asList(this.communityCards).stream(), Arrays.asList(playerCards[i].getPlayerHand()).stream()).collect(Collectors.toList()))) == -1 ? i : winner;
        }
        return winner;
    }

    public int getState() {
        return state;
    }
}

