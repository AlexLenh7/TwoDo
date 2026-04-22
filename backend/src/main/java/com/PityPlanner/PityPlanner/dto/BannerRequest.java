package com.PityPlanner.PityPlanner.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BannerRequest(
    UUID gameId,
    String characterName,
    String bannerType,
    Boolean isLimited,
    Boolean isRerun,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
