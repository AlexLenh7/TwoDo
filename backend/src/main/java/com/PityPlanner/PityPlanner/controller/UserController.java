package com.PityPlanner.PityPlanner.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PityPlanner.PityPlanner.dto.UserRequest;
import com.PityPlanner.PityPlanner.dto.UserResponse;
import com.PityPlanner.PityPlanner.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET request returns user profile 
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable UUID id) {
        return userService.getUserProfile(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // PUT request updates username
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserName(@PathVariable UUID id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateUserName(id, request));
    }

    // DELETE request deletes user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    } 
}
