package com.thoughtworks.gtb.csc.basicquiz.controller;

import com.thoughtworks.gtb.csc.basicquiz.model.Education;
import com.thoughtworks.gtb.csc.basicquiz.model.User;
import com.thoughtworks.gtb.csc.basicquiz.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.created(URI.create("")).body(userService.createUser(user));
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("{userId}/educations")
    public ResponseEntity<List<Education>> getUsersEducations(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUsersEducations(userId));
    }

    @PostMapping("{userId}/educations")
    public ResponseEntity<Education> addUserEducations(@PathVariable int userId,
        @Valid @RequestBody Education education) {
        return ResponseEntity.created(URI.create("")).body(userService.addUserEducations(userId, education));
    }
}
