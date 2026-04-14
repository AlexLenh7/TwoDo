package com.TwoDo.TwoDo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.TwoDo.TwoDo.dto.UserGamesRequest;
import com.TwoDo.TwoDo.dto.UserGamesResponse;
import com.TwoDo.TwoDo.entity.Game;
import com.TwoDo.TwoDo.entity.User;
import com.TwoDo.TwoDo.entity.UserGames;
import com.TwoDo.TwoDo.repository.GameRepository;
import com.TwoDo.TwoDo.repository.UserGamesRepository;
import com.TwoDo.TwoDo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserGamesService {
    private final UserGamesRepository userGamesRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    // default constructor
    public UserGamesService(UserGamesRepository userGamesRepository, GameRepository gameRepository, UserRepository userRepository) {
        this.userGamesRepository = userGamesRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    // create userGames
    public UserGamesResponse createUserGames(UUID userId, UserGamesRequest request) {
        // grab the user information 
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        
        // grab game information
        Game game = gameRepository.findById(request.gameId())
            .orElseThrow(() -> new EntityNotFoundException("Game not found: " + request.gameId()));
            
        UserGames userGames = new UserGames(
            request.currentCurrency(),
            request.currentPityWeapon(),
            request.currentPityCharacter(),
            user,
            game
        );
        
        return toResponse(userGamesRepository.save(userGames));
    }

    // UserGames toResponse
    private UserGamesResponse toResponse(UserGames userGames) {
        return new UserGamesResponse(
            userGames.getId(),
            userGames.getGame().getId(),
            userGames.getUser().getId(),
            userGames.getGame().getName(),
            userGames.getGame().getCurrencyName(),
            userGames.getCurrentCurrency(),
            userGames.getCurrentPityCharacter(),
            userGames.getCurrentPityWeapon()
        );
    }

    // grab all games from user
    public Optional<UserGamesResponse> getUserGamesById(UUID id) {
        return userGamesRepository.findById(id).map(this::toResponse);
    } 

    // grab game by user id
    public List<UserGamesResponse> getUserGamesByUser(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        return userGamesRepository.findByUser(user)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    // update user games
    @Transactional
    public UserGamesResponse updateUserGames(UUID id, UserGamesRequest request) {
        UserGames userGames = userGamesRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found with id: " + id));
        Game game = gameRepository.findById(request.gameId()).orElseThrow(()->new EntityNotFoundException("Game not found with id: " + id));
        
        userGames.setGame(game);
        userGames.setCurrentCurrency(request.currentCurrency());
        userGames.setCurrentPityCharacter(request.currentPityCharacter());
        userGames.setCurrentPityWeapon(request.currentPityWeapon());
        
        return toResponse(userGamesRepository.save(userGames));
    }

    // delete a game from user
    public void deleteUserGames(UUID id) {
        if (!userGamesRepository.existsById(id)) {
            throw new EntityNotFoundException("Game not found with id: " + id);
        }
        userGamesRepository.deleteById(id);
    }
}   
