package com.TwoDo.TwoDo.dto;

import java.util.UUID;

public record GameResponse(
    UUID id,
    String name,
    String currencyName,
    int pityLimit,
    int currencyPerPull,
    int dailyIncome
) {}