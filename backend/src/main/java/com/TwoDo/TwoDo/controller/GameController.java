package com.TwoDo.TwoDo.controller;

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

import com.TwoDo.TwoDo.dto.GameRequest;
import com.TwoDo.TwoDo.dto.GameResponse;
import com.TwoDo.TwoDo.service.GameService;

@RestController
@RequestMapping("/api/games")
@CrossOrigin
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // GET request returns all games
    @GetMapping
    public List<GameResponse> getAllGames() {
        return gameService.getAllGames();
    }

    // GET request returns single game
    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGame(@PathVariable UUID id) {
        return gameService.getGameById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST request creates game
    @PostMapping
    public ResponseEntity<GameResponse> createGame(@RequestBody GameRequest game) {
        return ResponseEntity.status(201).body(gameService.createGame(game));
    }

    // PUT request updates a game
    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> updateGame(@PathVariable UUID id, @RequestBody GameRequest game) {
        return ResponseEntity.ok(gameService.updateGame(id, game));
    }

    // DELETE request deletes a game
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
