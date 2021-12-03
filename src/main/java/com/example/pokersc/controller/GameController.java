package com.example.pokersc.controller;

import com.example.pokersc.entity.Game;
import com.example.pokersc.entity.Hand;
import com.example.pokersc.entity.Reception;
import com.example.pokersc.entity.User;
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
    private Game game;
    private Hand hand;
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
                if(user.getUsername().equals(username)) {
                    break;
                }
                position++;
            }
            StringBuilder gameString = new StringBuilder("{\n" +
                    "    users: [");
            for(User user: game.getAllUsers()) {
                gameString.append(objectMapper.writeValueAsString(user));
            }
            gameString.append("],\n" + "    profits: {\"test1\":\"+100\"},");
            gameString.append("communityCards: [").append(Arrays.toString(hand.getCommunityCards()));
            gameString.append("],\n" + "    pot: ").append(hand.getPot());
            gameString.append(",\n" + "    selfHand: [").append(hand.getPlayerCards()[position].getPlayerHand()[0]).append((hand.getPlayerCards()[position].getPlayerHand()[1]));
            gameString.append("],\n" + "    selfPosition:").append(position);
            gameString.append(",\n" + "    minimumRaiseAmount:").append(hand.getMaxBetInThisPhase()*2);
            gameString.append(",\n" + "    actionPosition:").append(hand.getActionOnWhichPlayer()); // TODO
            gameString.append(",\n" + "    dealerPosition:").append(game.dealerPos);

            gameString.append("\n}");
            return gameString.toString();
        }

    }

    @PostMapping("/games/{user_id}")
    public String joinGameById(@PathVariable int user_id, @RequestParam int position, @RequestParam int buyin) {
        Optional<User> optional = usersRepository.findById(user_id);
        if(optional.isPresent()) {
            User user = optional.get();
            //TODO: call something like addUser();
            reception.addPlayer(user,buyin,position);
            return "success";
        } else {
            return "failure";
        }
    }

    @PostMapping("/games/{user_id}/buyin")
    public void userBuyin(@PathVariable int user_id, @RequestParam int amount) {
        // TODO buyin during game
        game.rebuy(user_id,amount);
    }

    @PostMapping("/games/{user_id}/leave")
    public Game leaveGameById(@PathVariable int user_id) {
        //TODO
        // call something like deleteUser();
        User user = game.getUserById(user_id);
        reception.removePlayer(user);
        return null;
    }

    @PostMapping("/games/{user_id}/fold")
    public Game userFold(@PathVariable int user_id) {
        //TODO
        return null;
    }

    @PostMapping("/games/{user_id}/check")
    public Game userCheck(@PathVariable int user_id) {
        //TODO
        return null;
    }

    @PostMapping("/games/{user_id}/call")
    public GameController userCall( @PathVariable int user_id, @RequestParam int amount) {
        //TODO
        return null;
    }

    @PostMapping("games/{user_id}/raise")
    public GameController userRaise(@PathVariable int user_id, @RequestParam int amount) {
        //TODO
        return null;
    }
}
