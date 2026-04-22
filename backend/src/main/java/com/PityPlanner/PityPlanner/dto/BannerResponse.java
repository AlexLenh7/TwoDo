package com.PityPlanner.PityPlanner.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BannerResponse(
    UUID id,
    UUID gameId,
    String gameName,
    String characterName,
    String bannerType,
    Boolean isLimited,
    Boolean isRerun,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
