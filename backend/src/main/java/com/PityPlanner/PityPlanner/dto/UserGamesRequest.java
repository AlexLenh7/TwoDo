package com.PityPlanner.PityPlanner.dto;

import java.util.UUID;

public record UserGamesRequest (
    UUID gameId,
    int currentCurrency,
    int currentPityCharacter,
    int currentPityWeapon,
    int pityLimit
) {}
