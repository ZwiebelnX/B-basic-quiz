package com.thoughtworks.gtb.csc.basicquiz.controller;

import com.thoughtworks.gtb.csc.basicquiz.model.User;
import com.thoughtworks.gtb.csc.basicquiz.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
