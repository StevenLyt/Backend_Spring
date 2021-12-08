package com.example.pokersc.controller;

import com.example.pokersc.entity.*;
import com.example.pokersc.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class GameController {

    @Autowired
    private UsersRepository usersRepository;
    private Game game;
    private Reception reception;
    private GameThread gameThread = new GameThread();

    public class Struct{
        String username;
        int profit;
        public Struct(String s, int p){
            username = s;
            profit = p;
        }
    }
    class StructComparator implements Comparator<Struct>{
        @Override
        public int compare(Struct o1, Struct o2) {
            int result;
            if(o1.profit == o2.profit){
                result = 0;
            }
            else if(o1.profit < o2.profit){
                result = 1;
            }
            else {
                result = -1;
            }
            return result;
        }
    }

    public GameController(){
        this.gameThread.start();
        game = gameThread.game;
        reception = new Reception(game);
        this.reception.start();
    }

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    // a user creates a game
    @PostMapping("/games")
    public String getGameState(@RequestParam String username, @RequestParam String passwordHash) throws JsonProcessingException {
        // return a json that represent the whole game
        Hand hand = this.gameThread.hand;

        for(User user: game.userArr) {
            if(user != null){
                usersRepository.save(user);
            }
        }

        long time = System.currentTimeMillis() / 1000;
        for(int i = 0; i < 8; i++){
            if(time - gameThread.emojiTimes[i] > 3){
                gameThread.emojis[i] = -1;
                gameThread.emojiTimes[i] = time;
            }
        }

        Optional<User> optional = usersRepository.findByUsername(username);
        if(optional.isEmpty()) {
            return "user not found";
        } else if(!optional.get().getPassword().equals(passwordHash)) {
            return "password incorrect";
        } else {
            int position = 0;
            for(User user: game.userArr) {
                if(user!=null && user.getUsername().equals(username)) {
                    break;
                }
                position++;
            }
            if(position == 8){
                StringBuilder gameString = new StringBuilder("{\n" +
                        "    \"users\": [");
                int userPos = 0;
                for(User user: game.userArr) {
                    if(user == null){
                        gameString.append("null,");
                        userPos ++;
                        continue;
                    }
                    gameString.append("{");
                    gameString.append("\"username\": \"").append(user.getUsername()).append("\",");
                    gameString.append("\"currentAction\": \"").append(game.ongoing ? hand.getPlayerActions()[userPos] : "").append("\",");
                    gameString.append("\"currentBet\": ").append(game.ongoing ? hand.getChipPutInThisPhase()[userPos] : 0).append(",");
                    gameString.append("\"remainingChips\": ").append(game.ongoing ? hand.getRemainingStack()[userPos] : game.remainingChips[userPos]).append(",");
                    gameString.append("\"totalProfit\": ").append(user.getTotal_profit()).append(",");
                    gameString.append("\"currentProfit\": ").append(game.ongoing ? hand.getRemainingStack()[userPos] - game.totalBuyin[userPos] : game.remainingChips[userPos] - game.totalBuyin[userPos]).append(",");
                    gameString.append("\"winRate\": ").append((double) user.getTotal_win()/(user.getTotal_round()+1)).append(",");
                    gameString.append("\"hand\": ").append(game.ongoing && hand.getReadyForShowDown()[userPos] ? Arrays.toString(hand.getPlayerCards()[userPos].getPlayerHand()):"[\"\",\"\"]").append(",");
                    gameString.append("\"profileUrl\": \"").append(user.getProfile_url()).append("\",");
                    gameString.append("\"ifFold\": ").append(game.ongoing ? (!hand.getActive()[userPos] && !hand.getReadyForShowDown()[userPos]) : false).append(",");
                    gameString.append("\"isDealer\": ").append(game.ongoing ? userPos == hand.getDealerPos() : false).append(",");
                    gameString.append("\"isSelf\": ").append(user.getUsername() == username).append(",");
                    gameString.append("\"isActive\": ").append(false).append(",");
                    gameString.append("\"emoji\": ").append(gameThread.emojis[userPos]).append(",");
                    gameString.append("\"isWinner\": ").append(game.ongoing ? userPos == hand.getWinnerPos() : false);
                    gameString.append("},");
                    userPos++;
                }
                if(gameString.charAt(gameString.length()-1) == ',') {
                    gameString.deleteCharAt(gameString.length() - 1);
                }
                gameString.append("],\n" + "    \"playersProfits\": [");
                ArrayList<Struct> profitList = new ArrayList<>();
                for(int i=0; i<8; i++) {
                    if(game.getAllUsers()[i]!=null) {
                        Struct s = new Struct(game.getAllUsers()[i].getUsername(), game.remainingChips[i] - game.totalBuyin[i]);
                        profitList.add(s);
                    }
                }
                Collections.sort(profitList, new StructComparator());
                for(Struct struct : profitList){
                    gameString.append("\"").append(struct.username).append(":").append(struct.profit).append("\",");
                }
                if(gameString.charAt(gameString.length()-1) == ',') {
                    gameString.deleteCharAt(gameString.length() - 1);
                }
                gameString.append("],\n    \"selfProfit\": ").append(0);
                gameString.append(",\n    \"gameOn\": ").append(String.valueOf(game.ongoing));
                if(game.ongoing) {
                    gameString.append(",\n    \"remainingChips\": ").append(Arrays.toString(hand.getRemainingStack()));
                    Card[] temp = new Card[5];
                    for(int i = 0; i < hand.getState(); i++){
                        temp[i] = hand.getCommunityCards()[i];
                    }
                    gameString.append(",\n    \"communityCards\": ").append(Arrays.toString(temp));
                    gameString.append(",\n" + "    \"pot\": ").append(hand.getPot());
                    gameString.append(",\n" + "    \"selfHand\": [").append("\"\",\"\"]");
                    gameString.append(",\n" + "    \"selfPosition\":").append(-1);
                    gameString.append(",\n" + "    \"minimumRaiseAmount\":").append(hand.getMaxBetInThisPhase() * 2);
                    gameString.append(",\n" + "    \"actionPosition\":").append(hand.getActionOnWhichPlayer());
                    gameString.append(",\n" + "    \"dealerPosition\":").append(game.dealerPos);
                    gameString.append(",\n" + "    \"state\":").append(hand.getState());
                    gameString.append(",\n" + "    \"winner\":").append(hand.getWinnerPos());
                    gameString.append(",\n" + "    \"timeLeft\":").append(game.handend ? "\"\"": hand.timeLeft);
                    gameString.append(",\n" + "    \"canLeave\":").append(true);
                    gameString.append(",\n" + "    \"numActionLeft\":").append(hand.numActionLeft);
                    gameString.append("\n}");
                }
                else{
                    gameString.append(",\n    \"remainingChips\": ").append(Arrays.toString(game.remainingChips));
                    gameString.append(",\n    \"communityCards\": ").append("\"\"");
                    gameString.append(",\n" + "    \"pot\": ").append("\"\"");
                    gameString.append(",\n" + "    \"selfHand\": [").append("\"\"");
                    gameString.append("],\n" + "    \"selfPosition\":").append(-1);
                    gameString.append(",\n" + "    \"minimumRaiseAmount\":").append("\"\"");
                    gameString.append(",\n" + "    \"actionPosition\":").append("\"\"");
                    gameString.append(",\n" + "    \"dealerPosition\":").append(game.dealerPos);
                    gameString.append(",\n" + "    \"state\":").append("\"\"");
                    gameString.append(",\n" + "    \"timeLeft\":").append("\"\"");
                    gameString.append(",\n" + "    \"numActionLeft\":").append("\"\"");
                    gameString.append(",\n" + "    \"canLeave\":").append(true);
                    gameString.append("\n}");
                }
                return gameString.toString();


            }
            StringBuilder gameString = new StringBuilder("{\n" +
                    "    \"users\": [");
            int userPos = 0;
            for(User user: game.userArr) {
                if(user == null){
                    gameString.append("null,");
                    userPos ++;
                    continue;
                }
                gameString.append("{");
                gameString.append("\"username\": \"").append(user.getUsername()).append("\",");
                gameString.append("\"currentAction\": \"").append(game.ongoing ? hand.getPlayerActions()[userPos] : "").append("\",");
                gameString.append("\"currentBet\": ").append(game.ongoing ? hand.getChipPutInThisPhase()[userPos] : 0).append(",");
                gameString.append("\"remainingChips\": ").append(game.ongoing ? hand.getRemainingStack()[userPos] : game.remainingChips[userPos]).append(",");
                gameString.append("\"totalProfit\": ").append(user.getTotal_profit()).append(",");
                gameString.append("\"currentProfit\": ").append(game.ongoing ? hand.getRemainingStack()[userPos] - game.totalBuyin[userPos] : game.remainingChips[userPos] - game.totalBuyin[userPos]).append(",");
                gameString.append("\"winRate\": ").append((double) user.getTotal_win()/(user.getTotal_round()+1)).append(",");

                gameString.append("\"hand\": ").append(game.ongoing && (hand.getReadyForShowDown()[userPos] || user.getUsername().equals(username)) ? Arrays.toString(hand.getPlayerCards()[userPos].getPlayerHand()):"[\"\",\"\"]").append(",");
                gameString.append("\"profileUrl\": \"").append(user.getProfile_url()).append("\",");
                gameString.append("\"ifFold\": ").append(game.ongoing ? (!hand.getActive()[userPos] && !hand.getReadyForShowDown()[userPos]) : false).append(",");
                gameString.append("\"isDealer\": ").append(game.ongoing ? userPos == hand.getDealerPos() : false).append(",");
                gameString.append("\"isSelf\": ").append(user.getUsername().equals(username)).append(",");
                gameString.append("\"isActive\": ").append(!game.handend ? hand.getActionOnWhichPlayer() == userPos : false).append(",");
                gameString.append("\"emoji\": ").append(gameThread.emojis[userPos]).append(",");
                gameString.append("\"isWinner\": ").append(game.ongoing ? userPos == hand.getWinnerPos() : false);
                gameString.append("},");
                userPos++;
            }
            if(gameString.charAt(gameString.length()-1) == ',') {
                gameString.deleteCharAt(gameString.length() - 1);
            }
            gameString.append("],\n" + "    \"playersProfits\": [");
            ArrayList<Struct> profitList = new ArrayList<>();
            for(int i=0; i<8; i++) {
                if(game.getAllUsers()[i]!=null) {
                    Struct s = new Struct(game.getAllUsers()[i].getUsername(), game.remainingChips[i] - game.totalBuyin[i]);
                    profitList.add(s);
                }
            }
            profitList.sort(new StructComparator());
            for(Struct struct : profitList){
                gameString.append("\"").append(struct.username).append(":").append(struct.profit).append("\",");
            }
            if(gameString.charAt(gameString.length()-1) == ',') {
                gameString.deleteCharAt(gameString.length() - 1);
            }
            gameString.append("],\n    \"selfProfit\": ").append(game.remainingChips[position] - game.totalBuyin[position]);
            gameString.append(",\n    \"gameOn\": ").append(String.valueOf(game.ongoing));
            if(game.ongoing) {
                gameString.append(",\n    \"remainingChips\": ").append(Arrays.toString(hand.getRemainingStack()));
                Card[] temp = new Card[5];
                for(int i = 0; i < hand.getState(); i++){
                    temp[i] = hand.getCommunityCards()[i];
                }
                gameString.append(",\n    \"communityCards\": ").append(Arrays.toString(temp));
                gameString.append(",\n" + "    \"pot\": ").append(hand.getPot());
                gameString.append(",\n" + "    \"selfHand\": [").append(hand.getPlayerCards()[position].getPlayerHand()[0]).append(",").append((hand.getPlayerCards()[position].getPlayerHand()[1]));
                gameString.append("],\n" + "    \"selfPosition\":").append(position);
                gameString.append(",\n" + "    \"minimumRaiseAmount\":").append(hand.getMaxBetInThisPhase() * 2);
                gameString.append(",\n" + "    \"actionPosition\":").append(game.handend ? -1 : hand.getActionOnWhichPlayer());
                gameString.append(",\n" + "    \"dealerPosition\":").append(game.dealerPos);
                gameString.append(",\n" + "    \"state\":").append(hand.getState());
                gameString.append(",\n" + "    \"winner\":").append(hand.getWinnerPos());
                gameString.append(",\n" + "    \"numActionLeft\":").append(hand.numActionLeft);
                gameString.append(",\n" + "    \"potForPlayers\":").append(Arrays.toString(hand.getPotForEachPlayer()));
                gameString.append(",\n" + "    \"startingStack\":").append(Arrays.toString(hand.getStartingStack()));
                gameString.append(",\n" + "    \"isAllin\":").append(Arrays.toString(hand.getIsAllin()));
                gameString.append(",\n" + "    \"isFinished\":").append(Boolean.toString(hand.isFinished()));
                gameString.append(",\n" + "    \"canCheck\":").append(hand.getMaxBetInThisPhase() == 0 || position == hand.getBigBlind() && hand.getState() == 0 && hand.getMaxBetInThisPhase() == 2);
                gameString.append(",\n" + "    \"timeLeft\":").append(game.handend ? "\"\"":hand.timeLeft);
                gameString.append(",\n" + "    \"canLeave\":").append(game.handend ? true: false);
                gameString.append(",\n" + "    \"numPlayers\":").append(hand.getNumPlayers());

                gameString.append("\n}");
            }
            else{
                gameString.append(",\n    \"remainingChips\": ").append(Arrays.toString(game.remainingChips));
                gameString.append(",\n    \"communityCards\": ").append("\"\"");
                gameString.append(",\n" + "    \"pot\": ").append("\"\"");
                gameString.append(",\n" + "    \"selfHand\": [").append("\"\"");
                gameString.append("],\n" + "    \"selfPosition\":").append(position);
                gameString.append(",\n" + "    \"minimumRaiseAmount\":").append("\"\"");
                gameString.append(",\n" + "    \"actionPosition\":").append("\"\"");
                gameString.append(",\n" + "    \"dealerPosition\":").append(game.dealerPos);
                gameString.append(",\n" + "    \"state\":").append("\"\"");
                gameString.append(",\n" + "    \"canCheck\":").append(false);
                gameString.append(",\n" + "    \"timeLeft\":").append("\"\"");
                gameString.append(",\n" + "    \"canLeave\":").append(true);
                gameString.append(",\n" + "    \"numActionLeft\":").append("\"\"");
                gameString.append("\n}");
            }
            return gameString.toString();
        }

    }

    @PostMapping("/games/join")
    public String joinGameByUsername(@RequestParam String username, @RequestParam int position, @RequestParam int buyin) {
        Optional<User> optional = usersRepository.findByUsername(username);
        if(optional.isPresent()) {
            User user = optional.get();
            //TODO: call something like addUser();
            for(User u: game.userArr){
                if(u!=null && u.getUsername().equals(username)){
                    return "failure";
                }
            }
            if(game.userArr[position]!=null){
                return "failure";
            }
            if (position<0 || position >7){
                return "failure";
            }
            reception.addPlayer(user,buyin,position);
            return "success";
        } else {
            return "failure";
        }
    }

    @PostMapping("/games/buyin")
    public String userBuyin(@RequestParam String username, @RequestParam int amount) {
        // TODO buyin during game

        String result = game.rebuy(username,amount);
        return result;
    }

    @PostMapping("/games/leave")
    public String leaveGameByUsername(@RequestParam String username) {
        User user = game.getUserByUsername(username);
        if(user==null){return "failure";}
        reception.removePlayer(user);
        return "success";
    }

    @PostMapping("/games/fold")
    public String userFold(@RequestParam String username) {
        Hand hand = this.gameThread.hand;
        int pos = hand.getActionOnWhichPlayer();
        if(hand.getActive()[pos] && hand.getPlayerArr()[pos].getUsername().equals(username) && hand.getMaxBetInThisPhase() != 0) {
            Action action = new Action(Action.Act.FOLD);
            hand.addAction(action);
            return "success";
        } else {
            return "failure";
        }
    }

    @PostMapping("/games/check")
    public String userCheck(@RequestParam String username) {
        Hand hand = this.gameThread.hand;
        int pos = hand.getActionOnWhichPlayer();
        Action action = new Action(Action.Act.CHECK);
        if(hand.getActive()[pos] && hand.getPlayerArr()[pos].getUsername().equals(username) && hand.checkAction(pos,action)) {
            hand.addAction(action);
            return "success";
        } else {
            return "failure";
        }
    }

    @PostMapping("/games/call")
    public String userCall( @RequestParam String username) {
        Hand hand = this.gameThread.hand;
        int pos = hand.getActionOnWhichPlayer();
        Action action = new Action(Action.Act.CALL);
        if(hand.getActive()[pos] && hand.getPlayerArr()[pos].getUsername().equals(username) && hand.checkAction(pos,action)) {
            hand.addAction(action);
            return "success";
        } else {
            return "failure";
        }
    }

    @PostMapping("games/raise")
    public String userRaise(@RequestParam String username, @RequestParam int amount) {
        Hand hand = this.gameThread.hand;
        int pos = hand.getActionOnWhichPlayer();
        Action action = new Action(Action.Act.RAISE, amount);
        if(hand.getActive()[pos] && hand.getPlayerArr()[pos].getUsername().equals(username) && hand.checkAction(pos,action)) {
            hand.addAction(action);
            return "success";
        } else {
            return "failure";
        }
    }

    @PostMapping("games/emoji")
    public String sendEmoji(@RequestParam String username, @RequestParam int emoji) {
        int position = 0;
        for(User user: game.userArr) {
            if(user!=null && user.getUsername().equals(username)) {
                break;
            }
            position++;
        }
        if(position == 8){
            return "failure";
        }
        else{
            long timestamp = System.currentTimeMillis() / 1000;
            this.gameThread.emojis[position] = emoji;
            this.gameThread.emojiTimes[position] = timestamp;
            return "success";
        }
    }
}
