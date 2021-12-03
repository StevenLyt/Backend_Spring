package com.example.pokersc.controller;

import com.example.pokersc.entity.*;
import com.example.pokersc.repository.GameResultsRepository;
import com.example.pokersc.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping(name ="/api")
public class GameController {

    @Autowired
    private GameResultsRepository gameResultsRepository;
    @Autowired
    private UsersRepository usersRepository;
    private Game game = new Game();
    private Hand hand = new Hand();
    private Reception reception = new Reception(game);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    // a user creates a game
    @PostMapping("/games")
    public String getGameState(@RequestParam String username, @RequestParam String passwordHash) throws JsonProcessingException {
        //TODO return a json that represent the whole game
        Optional<User> optional = usersRepository.findByUsername(username);
        if(optional.isEmpty()) {
            return "no user found";
        } else if(!optional.get().getPassword().equals(passwordHash)) {
            return "password incorrect" + "\n correct is:"+optional.get().getPassword() +" real:"+passwordHash;
        } else {
            int position = 0;
            for(User user: hand.getPlayerArr()) {
                if(user!=null && user.getUsername().equals(username)) {
                    break;
                }
                position++;
            }
            StringBuilder gameString = new StringBuilder("{\n" +
                    "    users: [");
            for(User user: game.getAllUsers()) {
                gameString.append(objectMapper.writeValueAsString(user)).append(",");
            }
            if(gameString.charAt(gameString.length()-1) == ',') {
                gameString.deleteCharAt(gameString.length() - 1);
            }
            gameString.append("],\n" + "    profits: {");
            for(int i=0; i<8; i++) {
                if(game.getAllUsers()[i]!=null) {
                    gameString.append(username).append(":").append(game.remainingChips[i] - game.totalBuyin[i]).append(",");
                }
            }
            if(gameString.charAt(gameString.length()-1) == ',') {
                gameString.deleteCharAt(gameString.length() - 1);
            }
            gameString.append("},\n    communityCards: ").append(Arrays.toString(hand.getCommunityCards()));
            gameString.append(",\n" + "    pot: ").append(hand.getPot());
            gameString.append(",\n" + "    selfHand: [").append(hand.getPlayerCards()[0].getPlayerHand()[0]).append(",").append((hand.getPlayerCards()[0].getPlayerHand()[1]));
            gameString.append("],\n" + "    selfPosition:").append(position);
            gameString.append(",\n" + "    minimumRaiseAmount:").append(hand.getMaxBetInThisPhase()*2);
            gameString.append(",\n" + "    actionPosition:").append(hand.getActionOnWhichPlayer()); // TODO
            gameString.append(",\n" + "    dealerPosition:").append(game.dealerPos);

            gameString.append("\n}");
            return gameString.toString();
        }

    }

    @PostMapping("/games/join")
    public String joinGameById(@RequestParam String username, @RequestParam int position, @RequestParam int buyin) {
        Optional<User> optional = usersRepository.findByUsername(username);
        if(optional.isPresent()) {
            User user = optional.get();
            //TODO: call something like addUser();
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
        reception.removePlayer(user);
        return "success";
    }

    @PostMapping("/games/fold")
    public String userFold(@RequestParam String username) {
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
        int pos = hand.getActionOnWhichPlayer();
        if(hand.getActive()[pos] && hand.getPlayerArr()[pos].getUsername().equals(username)) {
            Action action = new Action(Action.Act.CHECK);
            hand.addAction(action);
            return "success";
        } else {
            return "failure";
        }
    }

    @PostMapping("/games/call")
    public String userCall( @RequestParam String username) {
        int pos = hand.getActionOnWhichPlayer();
        if(hand.getActive()[pos] && hand.getPlayerArr()[pos].getUsername().equals(username)) {
            Action action = new Action(Action.Act.CALL);
            hand.addAction(action);
            return "success";
        } else {
            return "failure";
        }
    }

    @PostMapping("games/raise")
    public String userRaise(@RequestParam String username, @RequestParam int amount) {
        int pos = hand.getActionOnWhichPlayer();
        if(hand.getActive()[pos] && hand.getPlayerArr()[pos].getUsername().equals(username)) {
            Action action = new Action(Action.Act.RAISE, amount);
            hand.addAction(action);
            return "success";
        } else {
            return "failure";
        }
    }
}
