package com.example.pokersc.controller;

import com.example.pokersc.entity.Game;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    // a user creates a game
    @PostMapping("games/")
    public Game createGame() {
        //TODO join after creating game
        return null;
    }

    @GetMapping("games/{game_id}/")
    public Game getGameById(@PathVariable int game_id) {
        //TODO
        return null;
    }

    @PostMapping("games/{game_id}/{user_id}")
    public Game joinGameById(@PathVariable int game_id, @PathVariable int user_id) {
        //TODO
        // call something like addUser();
        return null;
    }

    @PostMapping("games/{game_id}/{user_id}/leave")
    public Game leaveGameById(@PathVariable int game_id, @PathVariable int user_id) {
        //TODO
        // call something like deleteUser();
        return null;
    }

    @PostMapping("games/{game_id}/{user_id}/fold")
    public Game userFold(@PathVariable int game_id, @PathVariable int user_id) {
        //TODO
        return null;
    }

    @PostMapping("games/{game_id}/{user_id}/check")
    public Game userCheck(@PathVariable int game_id, @PathVariable int user_id) {
        //TODO
        return null;
    }

    @PostMapping("games/{game_id}/{user_id}/call")
    public GameController userCall(@PathVariable int game_id, @PathVariable int user_id, @RequestParam int amount) {
        //TODO
        return null;
    }
    @PostMapping("games/{game_id}/{user_id}/raise")
    public GameController userRaise(@PathVariable int game_id, @PathVariable int user_id, @RequestParam int amount) {
        //TODO
        return null;
    }
}
