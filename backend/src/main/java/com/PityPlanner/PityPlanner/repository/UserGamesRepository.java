package com.PityPlanner.PityPlanner.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PityPlanner.PityPlanner.entity.Game;
import com.PityPlanner.PityPlanner.entity.User;
import com.PityPlanner.PityPlanner.entity.UserGames;

@Repository
public interface UserGamesRepository extends JpaRepository<UserGames,UUID> {
    List<UserGames> findByUser(User user);
    Optional<UserGames> findByUserAndGame(User user, Game game);
}
