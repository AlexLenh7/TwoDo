package com.PityPlanner.PityPlanner.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PityPlanner.PityPlanner.dto.UserGamesRequest;
import com.PityPlanner.PityPlanner.dto.UserGamesResponse;
import com.PityPlanner.PityPlanner.service.UserGamesService;

@RestController
@RequestMapping("/api/user/{userId}/games")
@CrossOrigin
public class UserGamesController {
    private final UserGamesService userGamesService;

    public UserGamesController(UserGamesService userGamesService) {
        this.userGamesService = userGamesService;
    }

    // GET request returns game by userId
    @GetMapping
    public List<UserGamesResponse> getUserGamesByUser(@PathVariable UUID userId) {
        return userGamesService.getUserGamesByUser(userId);
    }

    // GET request returns game with gameId
    @GetMapping("/{id}")
    public ResponseEntity<UserGamesResponse> getUserGamesById(@PathVariable UUID id) {
        return userGamesService.getUserGamesById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // POST request creates game
    @PostMapping
    public ResponseEntity<UserGamesResponse> createUserGames(@PathVariable UUID userId, @RequestBody UserGamesRequest userGames) {
        return ResponseEntity.status(201).body(userGamesService.createUserGames(userId, userGames));
    }

    // UPDATE request updates user games
    @PutMapping("/{id}")
    public ResponseEntity<UserGamesResponse> updateUserGames(@PathVariable UUID id, @RequestBody UserGamesRequest userGames) {
        return ResponseEntity.ok(userGamesService.updateUserGames(id, userGames));
    }

    // DELETE request deletes game
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserGames(@PathVariable UUID id) {
        userGamesService.deleteUserGames(id);
        return ResponseEntity.noContent().build();
    }
}
