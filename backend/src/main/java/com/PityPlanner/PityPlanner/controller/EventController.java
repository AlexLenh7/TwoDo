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

import com.PityPlanner.PityPlanner.dto.EventRequest;
import com.PityPlanner.PityPlanner.dto.EventResponse;
import com.PityPlanner.PityPlanner.service.EventService;

@RestController
@RequestMapping("/api/events")
@CrossOrigin
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // GET request returns all events
    @GetMapping
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    // GET request returns event by id
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable UUID id) {
        return eventService.getEventById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // GET request returns event by game
    @GetMapping("/game/{gameId}")
    public List<EventResponse> getEventByGame(@PathVariable UUID gameId) {
        return eventService.getEventByGame(gameId);
    }

    // GET request returns event by active
    @GetMapping("/active")
    public List<EventResponse> getActiveEvent() {
        return eventService.getActiveEvents();
    }

    // POST request creates a event
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest event) {
        return ResponseEntity.status(201).body(eventService.createEvent(event));
    }

    // PUT request updates a event
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable UUID id, @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    // DELETE request deletes a event
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
