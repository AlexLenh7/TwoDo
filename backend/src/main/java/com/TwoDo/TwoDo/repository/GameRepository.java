package com.TwoDo.TwoDo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TwoDo.TwoDo.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game,UUID> {
    List<Game> findByName(String name);
}
