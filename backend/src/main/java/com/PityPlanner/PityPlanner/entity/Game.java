package com.PityPlanner.PityPlanner.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name="Games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @OneToMany(mappedBy="game")
    private List<Banner> banners = new ArrayList<>();

    @OneToMany(mappedBy = "game")
    private List<Event> events = new ArrayList<>();

    @Column(length=50, nullable=false)
    private String currencyName;

    @Column(nullable=false)
    private int pityLimit;

    @Column(nullable=false)
    private int currencyPerPull;

    @Column(nullable=false)
    private int dailyIncome;

    // Managed by JPA
    public Game() {}

    public Game(String name, String currencyName, int pityLimit, int currencyPerPull, int dailyIncome) {
        this.name = name;
        this.currencyName = currencyName;
        this.pityLimit = pityLimit;
        this.currencyPerPull = currencyPerPull;
        this.dailyIncome = dailyIncome;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public int getPityLimit() {
        return pityLimit;
    }

    public void setPityLimit(int pityLimit) {
        this.pityLimit = pityLimit;
    }

    public int getCurrencyPerPull() {
        return currencyPerPull;
    }

    public void setCurrencyPerPull(int currencyPerPull) {
        this.currencyPerPull = currencyPerPull;
    }

    public int getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(int dailyIncome) {
        this.dailyIncome = dailyIncome;
    }
}
