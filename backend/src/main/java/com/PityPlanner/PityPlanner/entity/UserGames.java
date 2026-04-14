package com.PityPlanner.PityPlanner.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="UserGames")
public class UserGames {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    // Current currency 
    @Column(nullable=false)
    private int currentCurrency;

    // Current pity for character banner
    @Column(nullable=false)
    private int currentPityCharacter;

    // Current pity for character weapon
    private int currentPityWeapon;

    public UserGames() {}

    public UserGames(int currentCurrency, int currentPityCharacter, int currentPityWeapon, User user, Game game) {
        this.currentCurrency = currentCurrency;
        this.currentPityCharacter = currentPityCharacter;
        this.currentPityWeapon = currentPityWeapon;
        this.user = user;
        this.game = game;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getCurrentCurrency() {
        return currentCurrency;
    }

    public void setCurrentCurrency(int totalCurrency) {
        this.currentCurrency = totalCurrency;
    }

    public int getCurrentPityWeapon() {
        return currentPityWeapon;
    }

    public void setCurrentPityWeapon(int currentPityWeapon) {
        this.currentPityWeapon = currentPityWeapon;
    }

    public int getCurrentPityCharacter() {
        return currentPityCharacter;
    }

    public void setCurrentPityCharacter(int currentPityCharacter) {
        this.currentPityCharacter = currentPityCharacter;
    }
}