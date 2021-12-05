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
    public long timeLeft = 60;
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

    public String[] getPlayerActions(){
        return playerActions;
    }

    public boolean[] getReadyForShowDown(){
        return readyForShowDown;
    }

    public boolean[] getIsAllin(){
        return isAllin;
    }

    public int[] getPotForEachPlayer(){
        return potForEachPlayer;
    }

    public int[] getStartingStack(){
        return startingStack;
    }
    private Card[] communityCards;
    private int pot;
    private int[] potForEachPlayer;
    private int[] startingStack;
    private int actionOnWhichPlayer;
    private int smallBlind;
    private int bigBlind;
    private int[] chipPutInThisPhase;
    private int maxBetInThisPhase;
    private String[] playerActions;
    private boolean[] readyForShowDown;
    private boolean[] isAllin;
    private Action currentAction = null;
    // for testing

    public Hand(User[] userList, int[] chips, int dPos, int numP){
        this.playerArr = userList;
        this.remainingStack = chips;
        this.startingStack = chips.clone();
        this.dealerPos = dPos;
        this.numPlayers = numP;
        this.playerCards = new PlayerCards[8];
        this.active = new boolean[8];
        this.chipPutInThisPhase = new int[8];
        this.playerActions = new String[8];
        this.potForEachPlayer = new int[8];
        this.readyForShowDown = new boolean[8];
        this.isAllin = new boolean[8];
        for(int i = 0; i < 8; i++){
            playerCards[i] = new PlayerCards();
            chipPutInThisPhase[i] = 0;
            potForEachPlayer[i] = 0;
            readyForShowDown[i] = false;
            isAllin[i] = false;
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
        this.potForEachPlayer[smallBlind] += 1;
        this.chipPutInThisPhase[bigBlind] = 2;
        this.remainingStack[bigBlind] -= 2;
        this.potForEachPlayer[bigBlind] += 2;
        this.maxBetInThisPhase = 2;
        numActionLeft = numPlayers;
        while(numActionLeft > 0){
            this.timeLeft = 60;
            boolean didAction = false;
            long timestamp = System.currentTimeMillis() / 1000;
            while (this.timeLeft > 0){
                if (currentAction != null) {
                    didAction = true;
                    break;
                }
                timeLeft = 60 - (System.currentTimeMillis() / 1000 - timestamp);
            }
            if(didAction == false){
                Action action = new Action(Action.Act.FOLD);
                addAction(action);
            }
            doAction(actionOnWhichPlayer);
            actionOnWhichPlayer ++; //first action on UTG;
            actionOnWhichPlayer %= 8;
            while(!active[actionOnWhichPlayer]){
                actionOnWhichPlayer ++;
                actionOnWhichPlayer %= 8;
            }
            this.currentAction = null;
        }
        //end flop

        state = 3;
        //flop
        if(!isFinished()) {
            this.initializePhase();
            numActionLeft = numPlayers;
            this.actionOnWhichPlayer = smallBlind;
            while (!active[actionOnWhichPlayer]) {
                actionOnWhichPlayer++;
                actionOnWhichPlayer %= 8;
            }
            while (numActionLeft > 0) {
                while (true) {
                    if (currentAction != null) {
                        break;
                    }
                }
                doAction(actionOnWhichPlayer);
                actionOnWhichPlayer++; //first action on UTG;
                actionOnWhichPlayer %= 8;
                while (playerArr[actionOnWhichPlayer] == null) {
                    actionOnWhichPlayer++;
                    actionOnWhichPlayer %= 8;
                }
                this.currentAction = null;
            }
        }
        //end flop

        state = 4;
        //turn
        if(!isFinished()) {
            this.initializePhase();
            numActionLeft = numPlayers;
            this.actionOnWhichPlayer = smallBlind;
            while (!active[actionOnWhichPlayer]) {
                actionOnWhichPlayer++;
                actionOnWhichPlayer %= 8;
            }
            while (numActionLeft > 0) {
                while (true) {
                    if (currentAction != null) {
                        break;
                    }
                }
                doAction(actionOnWhichPlayer);
                actionOnWhichPlayer++; //first action on UTG;
                actionOnWhichPlayer %= 8;
                while (playerArr[actionOnWhichPlayer] == null) {
                    actionOnWhichPlayer++;
                    actionOnWhichPlayer %= 8;
                }
                this.currentAction = null;
            }
        }
        // end turn

        state = 5;
        //river
        if(!isFinished()) {
            this.initializePhase();
            numActionLeft = numPlayers;
            this.actionOnWhichPlayer = smallBlind;
            while (!active[actionOnWhichPlayer]) {
                actionOnWhichPlayer++;
                actionOnWhichPlayer %= 8;
            }
            while (numActionLeft > 0) {
                while (true) {
                    if (currentAction != null) {
                        break;
                    }
                }
                doAction(actionOnWhichPlayer);
                actionOnWhichPlayer++; //first action on UTG;
                actionOnWhichPlayer %= 8;
                while (playerArr[actionOnWhichPlayer] == null) {
                    actionOnWhichPlayer++;
                    actionOnWhichPlayer %= 8;
                }
                this.currentAction = null;
            }
        }
        //end river

        int numActive = 0;
        for(int i = 0; i < 8; i++){
            if(active[i]){
                numActive ++;
                readyForShowDown[i] = true;
            }
        }

        if(numActive > 1) {
            this.winnerPos = getWinner();
        }
        else {
            for(int i = 0; i < 8; i++) {
                if (active[i]) {
                    this.winnerPos = i;
                }
            }
        }
        User winner =  playerArr[winnerPos];
        winner.setTotal_win(winner.getTotal_win() + 1);
        distributePotToWinners(winnerPos);
    }

    private void distributePotToWinners(int pos){
        System.out.println(pos);
        if(pos == -1){
            return;
        }
        User winner =  playerArr[pos];
        int maxWinFromEachPlayer = potForEachPlayer[pos];
        int potForPlayer = 0;
        System.out.println(Arrays.toString(potForEachPlayer));
        for(int i = 0; i < 8; i++){
            if(potForEachPlayer[i] <= maxWinFromEachPlayer){
                potForPlayer += potForEachPlayer[i];
                potForEachPlayer[i] = 0;
            }
            else{
                potForPlayer += maxWinFromEachPlayer;
                potForEachPlayer[i] -= maxWinFromEachPlayer;
            }
        }
        System.out.println(Arrays.toString(potForEachPlayer));
        this.remainingStack[pos] += potForPlayer;
        this.active[pos] = false;
        distributePotToWinners(getWinner());
    }

    public boolean isFinished(){
        int num = 0;
        for(boolean bool : active){
            if(bool){
                num ++;
            }
        }
        return num <= 1 || numPlayers <= 1;
    }

    // initialize phase (flop, turn, river);
    private void initializePhase(){
        for(int i = 0; i < 8; i++){
            this.chipPutInThisPhase[i] = 0;
            this.playerActions[i] = null;
        }
        this.maxBetInThisPhase = 0;
    }

    // check if every player puts same amount or folds
   /* private boolean readyForNextRound(){
        int amount = 0;
        for(int i = 0; i < 8; i++){
            if(active[i] && !isAllin[i] ) {
                amount = chipPutInThisPhase[i];
                break;
            }
        }
        for(int i = 0; i < 8; i++){
            if(active[i] && !isAllin[i] && chipPutInThisPhase[i] != amount) {
               return false;
            }
        }
        return true;
    }*/


    // update active arr, chipPutInThisPhase arr, maxBetInThisPhase, pot, numPlayer
    public void doAction(int pos){
        String[] actions = {"raise", "check", "fold", "call"};
        playerActions[pos] = actions[currentAction.getAct().ordinal()];
        if(currentAction.getAct() == Action.Act.FOLD){
            System.out.println("folded");
            System.out.println(pos);
            this.active[pos] = false;
            numActionLeft --;
            this.numPlayers --;
        }
        else if(currentAction.getAct() == Action.Act.CALL){
            //all in
            if(remainingStack[pos] < maxBetInThisPhase - chipPutInThisPhase[pos]) {
                this.pot += remainingStack[pos];
                this.potForEachPlayer[pos] = startingStack[pos];
                this.chipPutInThisPhase[pos] += remainingStack[pos];
                this.remainingStack[pos] = 0;
                this.isAllin[pos] = true;
                numActionLeft --;
                this.numPlayers --;
            }
            else{
                this.pot += (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
                this.potForEachPlayer[pos] += (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
                this.remainingStack[pos] -= (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
                this.chipPutInThisPhase[pos] = maxBetInThisPhase;
                numActionLeft --;
            }

        }
        else if(currentAction.getAct() == Action.Act.RAISE){
            maxBetInThisPhase = currentAction.getAmount();
            this.remainingStack[pos] -= (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
            this.pot += (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
            this.potForEachPlayer[pos] += (maxBetInThisPhase - this.chipPutInThisPhase[pos]);
            this.chipPutInThisPhase[pos] = maxBetInThisPhase;
            numActionLeft = numPlayers - 1;
            if(this.remainingStack[pos] == 0){
                numPlayers--;
                this.isAllin[pos] = true;
            }
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
            if (action.getAmount() < 2 * this.maxBetInThisPhase) {
                // not allin and less than mini-raise
                if(action.getAmount() != (this.remainingStack[pos] + this.chipPutInThisPhase[pos]))
                    return false;
            }
            else if (this.remainingStack[pos] + this.chipPutInThisPhase[pos] < action.getAmount())
                return false;
        } else if (action.getAct() == Action.Act.CALL) {
            // all in
           // if (this.remainingStack[pos] < this.maxBetInThisPhase - this.chipPutInThisPhase[pos])
           //     return false;
        }
        return true;
    }

    private int getWinner() {
        int winner = -1;
        for (int i = 1; i < 8; i++) {
            if (!active[i])
                continue;
            if (winner == -1){
                winner = i;
                continue;
            }
            winner = HandRanker.getInstance().getRank(Stream.concat(Arrays.asList(this.communityCards).stream(), Arrays.asList(playerCards[winner].getPlayerHand()).stream()).collect(Collectors.toList())).compareTo(HandRanker.getInstance().getRank(Stream.concat(Arrays.asList(this.communityCards).stream(), Arrays.asList(playerCards[i].getPlayerHand()).stream()).collect(Collectors.toList()))) == -1 ? i : winner;
        }
        return winner;
    }

    public int getState() {
        return state;
    }
}

