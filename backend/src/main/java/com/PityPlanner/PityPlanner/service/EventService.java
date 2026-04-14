package com.PityPlanner.PityPlanner.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.PityPlanner.PityPlanner.dto.EventRequest;
import com.PityPlanner.PityPlanner.dto.EventResponse;
import com.PityPlanner.PityPlanner.entity.Event;
import com.PityPlanner.PityPlanner.entity.Game;
import com.PityPlanner.PityPlanner.repository.EventRepository;
import com.PityPlanner.PityPlanner.repository.GameRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final GameRepository gameRepository;

    // constructor
    public EventService(EventRepository eventRepository, GameRepository gameRepository) {
        this.eventRepository = eventRepository;
        this.gameRepository = gameRepository;
    }

    // create Event
    public EventResponse createEvent(EventRequest request) {
        Game game = gameRepository.findById(request.gameId())
            .orElseThrow(() -> new EntityNotFoundException("Game not found: " + request.gameId()));

        Event event = new Event(
            request.name(),
            request.totalCurrency(),
            request.startDate(),
            request.endDate(),
            game
        );
        return toResponse(eventRepository.save(event));
    }

    // Event toResponse
    private EventResponse toResponse(Event event) {
        return new EventResponse(
            event.getId(), 
            event.getGame().getId(), 
            event.getGame().getName(), 
            event.getName(), 
            event.getTotalCurrency(), 
            event.getStartDate(), 
            event.getEndDate()
        );
    }

    // grab event name
    public Optional<EventResponse> getEventById(UUID id) {
        return eventRepository.findById(id).map(this::toResponse);
    }

    // delete a event
    public void deleteEvent(UUID id) {
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }

    // grab active events
    public List<EventResponse> getActiveEvents() {
        return eventRepository.findByEndDateAfter(LocalDateTime.now())
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    // grab event by game
    public List<EventResponse> getEventByGame(UUID gameId) {
        Game game = gameRepository.findById(gameId)
            .orElseThrow(() -> new EntityNotFoundException("Game not found: " + gameId));
        
        return eventRepository.findByGame(game)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public EventResponse updateEvent(UUID id, EventRequest request) {
        Event event = eventRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Event not found with id: " + id));
        Game game = gameRepository.findById(request.gameId()).orElseThrow(()->new EntityNotFoundException("Game not found with id: " + id));

        event.setName(request.name());
        event.setTotalCurrency(request.totalCurrency());
        event.setStartDate(request.startDate());
        event.setEndDate(request.endDate());
        event.setGame(game);

        return toResponse(eventRepository.save(event));
    }

    // grab all events
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}
