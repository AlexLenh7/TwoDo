package com.TwoDo.TwoDo.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="Events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    // Event name
    private String name;

    // Event total currency
    @Column(nullable=false)
    private int totalCurrency;

    // Event start time
    private LocalDateTime startDate;

    // Event end time
    private LocalDateTime endDate;

    public Event() {}

    public Event(String name, int totalCurrency, LocalDateTime startDate, LocalDateTime endDate, Game game) {
        this.name = name;
        this.totalCurrency = totalCurrency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.game = game;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String characterName) {
        this.name = characterName;
    }

    public int getTotalCurrency() {
        return totalCurrency;
    }

    public void setTotalCurrency(int totalCurrency) {
        this.totalCurrency = totalCurrency;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
