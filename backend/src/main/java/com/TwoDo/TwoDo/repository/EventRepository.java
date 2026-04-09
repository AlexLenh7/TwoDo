package com.TwoDo.TwoDo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TwoDo.TwoDo.entity.Event;
import com.TwoDo.TwoDo.entity.Game;

@Repository
public interface EventRepository extends JpaRepository<Event,UUID> {
    List<Event> findByGame(Game game);
    List<Event> findByEndDateAfter(LocalDateTime date);
}
