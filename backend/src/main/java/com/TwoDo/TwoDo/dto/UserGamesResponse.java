package com.TwoDo.TwoDo.dto;

import java.util.UUID;

public record UserGamesResponse (
    UUID id,
    UUID gameId,
    UUID userId,
    String gameName,
    String currencyName,
    int currentCurrency,
    int currentPityCharacter,
    int currentPityWeapon
) {}
