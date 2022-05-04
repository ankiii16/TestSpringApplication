package com.example.TestApplication.controller;

import com.example.TestApplication.entity.User;
import com.example.TestApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    // get all users

    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    //get user by id

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable(value = "id") long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new ResolutionException("User Not Found By Given Id:-" + userId));
    }

    //create user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    //update user
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
        User existingUser = this.userRepository.findById(userId).orElseThrow(() -> new ResolutionException("User Not Found By Given Id:-" + userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return this.userRepository.save(existingUser);
    }

    //delete user by id

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long userId) {
        User existingUser = this.userRepository.findById(userId).orElseThrow(() -> new ResolutionException("User Not Found By Given Id:-" + userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
