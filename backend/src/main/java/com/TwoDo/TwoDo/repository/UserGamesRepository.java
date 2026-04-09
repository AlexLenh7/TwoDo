package com.TwoDo.TwoDo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TwoDo.TwoDo.entity.UserGames;

@Repository
public interface UserGamesRepository extends JpaRepository<UserGames,UUID> {
    
}
