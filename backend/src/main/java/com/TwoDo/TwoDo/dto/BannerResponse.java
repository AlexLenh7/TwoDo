package com.TwoDo.TwoDo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BannerResponse(
    UUID id,
    UUID gameId,
    String gameName,
    String characterName,
    String bannerType,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
