package com.PityPlanner.PityPlanner.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponse (
    UUID id,
    UUID gameId,
    String gameName,
    String name,
    int totalCurrency,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
