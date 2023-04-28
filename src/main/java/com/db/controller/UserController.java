package com.db.controller;

import com.db.model.User;
import com.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/byEmailAndPassword")
    public ResponseEntity<User> getUserByEmailAndPassword(@RequestBody User user) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()));
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok().body(userRepository.insert(user));
    }
}
