package com.PityPlanner.PityPlanner.dto;

public record GameRequest (
    String name,
    String currencyName,
    int pityLimit,
    int currencyPerPull,
    int dailyIncome
) {}
