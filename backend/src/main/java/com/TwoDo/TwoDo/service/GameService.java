package com.TwoDo.TwoDo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.TwoDo.TwoDo.dto.GameRequest;
import com.TwoDo.TwoDo.dto.GameResponse;
import com.TwoDo.TwoDo.entity.Game;
import com.TwoDo.TwoDo.repository.GameRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/* 
Managed by Admin
Game service resposibilities: 
 - Handles adding new games
 - Handles daily game income
 - Handles all game list 
*/ 
@Service
public class GameService {
    private final GameRepository gameRepository;

    // default constructor
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Create the game from request and return it
    public GameResponse createGame(GameRequest game) {
        Game newGame = new Game(
            game.name(),
            game.currencyName(),
            game.pityLimit(),
            game.currencyPerPull(),
            game.dailyIncome()
        );

        return toResponse(gameRepository.save(newGame));
    }

    private GameResponse toResponse(Game game) {
        return new GameResponse(
            game.getId(),
            game.getName(),
            game.getCurrencyName(),
            game.getPityLimit(),
            game.getCurrencyPerPull(),
            game.getDailyIncome()
        );
    }

    // returns a single game
    public Optional<GameResponse> getGameById(UUID id) {
        return gameRepository.findById(id).map(this::toResponse);
    }

    // delete a game
    public void deleteGame(UUID id) {
        if (!gameRepository.existsById(id)) {
            throw new EntityNotFoundException("Game not found with id: " + id);
        }
        gameRepository.deleteById(id);
    }

    // updates pity limit
    @Transactional
    public GameResponse updateGame(UUID id, GameRequest request) {
        // fetch the game by id
        Game game = gameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));
        
        // modify the field
        game.setName(request.name());
        game.setCurrencyName(request.currencyName());
        game.setPityLimit(request.pityLimit());
        game.setCurrencyPerPull(request.currencyPerPull());
        game.setDailyIncome(request.dailyIncome());

        // return the repository
        return toResponse(gameRepository.save(game));
    }

    // grab all games
    public List<GameResponse> getAllGames() {
        return gameRepository.findAll()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}