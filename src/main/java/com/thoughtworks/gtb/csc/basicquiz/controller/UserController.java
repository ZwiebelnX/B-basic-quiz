package com.thoughtworks.gtb.csc.basicquiz.controller;

import com.thoughtworks.gtb.csc.basicquiz.model.Education;
import com.thoughtworks.gtb.csc.basicquiz.model.User;
import com.thoughtworks.gtb.csc.basicquiz.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

@RestController
// GTB: - 最好是 @RequestMapping("/users")
@RequestMapping("users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    // GTB: 可以了解一下 @ResponseStatus(HttpStatus.CREATED)，另一种写法
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.created(URI.create("")).body(userService.createUser(user));
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("{userId}/educations")
    public ResponseEntity<List<Education>> getUsersEducations(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUsersEducations(userId));
    }

    @PostMapping("{userId}/educations")
    public ResponseEntity<Education> addUserEducations(@PathVariable long userId,
        @Valid @RequestBody Education education) {
        return ResponseEntity.created(URI.create("")).body(userService.addUserEducations(userId, education));
    }
}
