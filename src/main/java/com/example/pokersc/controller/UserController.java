package com.example.pokersc.controller;

import com.example.pokersc.Utils;
import com.example.pokersc.entity.User;
import com.example.pokersc.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/login")
    public User userLogin(@RequestParam String username, @RequestParam String password) {
        String hash = Utils.sha256(password);
        Optional<User> optional = usersRepository.findByUsername(username);
        if(optional.isPresent() && hash.equals(optional.get().getPassword())) {
            return optional.get();
        } else {
            return null;
        }
    }

    @PostMapping("/signup")
    public String userSignup(@RequestParam String username, @RequestParam String password, @RequestParam String profile_url) {
        User user = new User(username, Utils.sha256(password), profile_url);
        usersRepository.save(user);
        return "success";
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return usersRepository.findById(id).orElse(null);
    }

    @PostMapping("/users/{id}/buyin")
    public User buyinByid(@PathVariable int id, @RequestParam double amount) {
        Optional<User> optional = usersRepository.findById(id);
        if(optional.isPresent()) {
            User user = optional.get();
            user.addAmount(amount);
            return usersRepository.save(user);
        }else {
            return null;
        }
    }

}