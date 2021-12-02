package com.example.pokersc.controller;

import com.example.pokersc.entity.Game;
import com.example.pokersc.entity.User;
import com.example.pokersc.repository.GameResultsRepository;
import com.example.pokersc.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(name ="/api")
public class GameController {

    @Autowired
    private GameResultsRepository gameResultsRepository;
    private UsersRepository usersRepository;
    private Game game;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    // a user creates a game
    @PostMapping("/games")
    public Game getGameState() {
        //TODO return a json that represent the whole game
        return null;
    }

    @PostMapping("/games/{user_id}")
    public String joinGameById(@PathVariable int user_id, @RequestParam int position, @RequestParam int buyin) {
        Optional<User> optional = usersRepository.findById(user_id);
        if(optional.isPresent()) {
            User user = optional.get();
            //TODO: call something like addUser();
            return "success";
        } else {
            return "failure";
        }
    }

    @PostMapping("/games/{user_id}/buyin")
    public void userBuyin(@PathVariable int user_id, @RequestParam int amount) {
        // TODO buyin during game
    }

    @PostMapping("/games/{user_id}/leave")
    public Game leaveGameById(@PathVariable int user_id) {
        //TODO
        // call something like deleteUser();
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
