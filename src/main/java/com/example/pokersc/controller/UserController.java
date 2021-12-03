package com.example.pokersc.controller;

import com.example.pokersc.Utils;
import com.example.pokersc.entity.User;
import com.example.pokersc.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(name ="/api")
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String username, @RequestParam String password) {
        String hash = Utils.sha256(password);
        Optional<User> optional = usersRepository.findByUsername(username);
        if(optional.isPresent() && hash.equals(optional.get().getPassword())) {
            return hash;
        } else {
            return "failure";
        }
    }

    @PostMapping("/signup")
    public String userSignup(@RequestParam String username, @RequestParam String password, @RequestParam String profile_url) {
        // check if username already exists
        if(usersRepository.findByUsername(username).isPresent()) {
            return "failure";
        }
        String hash = Utils.sha256(password);
        User user = new User(username, hash, profile_url);
        usersRepository.save(user);
        return hash;
    }

    @GetMapping("/users")
    public Iterable<User> getAllUser() {
        return usersRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return usersRepository.findById(id).orElse(null);
    }

//    @PostMapping("/users/{id}/buyin")
//    public User buyinByid(@PathVariable int id, @RequestParam double amount) {
//        Optional<User> optional = usersRepository.findById(id);
//        if(optional.isPresent()) {
//            User user = optional.get();
//            user.addAmount(amount);
//            return usersRepository.save(user);
//        }else {
//            return null;
//        }
//    }

}
