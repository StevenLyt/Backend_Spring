package com.example.pokersc.controller;

import com.example.pokersc.entity.Game;
import com.example.pokersc.repository.GameResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(name ="/api")
public class GameController {

    @Autowired
    private GameResultsRepository gameResultsRepository;
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
    public Game joinGameById(@PathVariable int user_id, @RequestParam int position, @RequestParam int buyin) {
        //TODO
        // call something like addUser();
        return null;
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
