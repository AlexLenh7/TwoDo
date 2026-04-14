package com.PityPlanner.PityPlanner.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventRequest (
    UUID gameId,
    String name,
    int totalCurrency,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
