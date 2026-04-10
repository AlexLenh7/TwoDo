package com.TwoDo.TwoDo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TwoDo.TwoDo.entity.Game;
import com.TwoDo.TwoDo.entity.User;
import com.TwoDo.TwoDo.entity.UserGames;

@Repository
public interface UserGamesRepository extends JpaRepository<UserGames,UUID> {
    List<UserGames> findByUser(User user);
    Optional<UserGames> findByUserAndGame(User user, Game game);
}
