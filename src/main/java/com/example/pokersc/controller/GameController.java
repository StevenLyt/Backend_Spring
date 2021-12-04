package com.example.pokersc.controller;

import com.example.pokersc.entity.*;
import com.example.pokersc.repository.GameResultsRepository;
import com.example.pokersc.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(name ="/api")
public class GameController {

    @Autowired
    private GameResultsRepository gameResultsRepository;
    @Autowired
    private UsersRepository usersRepository;
    private Game game;
    private Reception reception;
    private GameThread gameThread = new GameThread();

    private final ObjectMapper objectMapper = new ObjectMapper();

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
        //game.addUser(usersRepository.findByUsername("gyx").get(), 100,0);

        //game.addUser(usersRepository.findByUsername("gyx2").get(), 100,1);
        //game.addUser(usersRepository.findByUsername("gyx3").get(), 100,2);

    }

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    // a user creates a game
    @PostMapping("/games")
    public String getGameState(@RequestParam String username, @RequestParam String passwordHash) throws JsonProcessingException {
        //TODO return a json that represent the whole game
        Hand hand = this.gameThread.hand;
       // if(hand == null){
        //    return String.valueOf(game.numPlayers);
       // }
        Optional<User> optional = usersRepository.findByUsername(username);
        if(optional.isEmpty()) {
            return "no user found";
        } else if(!optional.get().getPassword().equals(passwordHash)) {
            return "password incorrect" + "\n correct is:"+optional.get().getPassword() +" real:"+passwordHash;
        } else {
            int position = 0;
            for(User user: game.userArr) {
                if(user!=null && user.getUsername().equals(username)) {
                    break;
                }
                position++;
            }
            if(position == 8){
                return "player is not in the game";
            }
            StringBuilder gameString = new StringBuilder("{\n" +
                    "    \"users\": [");
            for(User user: game.getAllUsers()) {
                gameString.append(objectMapper.writeValueAsString(user)).append(",");
            }
            if(gameString.charAt(gameString.length()-1) == ',') {
                gameString.deleteCharAt(gameString.length() - 1);
            }
            gameString.append("],\n" + "    \"profits\": [");

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
            gameString.append("],\n    \"gameOn\": ").append(String.valueOf(game.ongoing));
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
                gameString.append(",\n" + "    \"actionPosition\":").append(hand.getActionOnWhichPlayer());
                gameString.append(",\n" + "    \"dealerPosition\":").append(game.dealerPos);
                gameString.append(",\n" + "    \"state\":").append(hand.getState());
                gameString.append(",\n" + "    \"numActionLeft\":").append(hand.numActionLeft);
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
    public void userBuyin(@RequestParam String username, @RequestParam int amount) {
        // TODO buyin during game
        game.rebuy(username,amount);
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
        if(hand.getActive()[pos] && hand.getPlayerArr()[pos].getUsername().equals(username)) {
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
}
