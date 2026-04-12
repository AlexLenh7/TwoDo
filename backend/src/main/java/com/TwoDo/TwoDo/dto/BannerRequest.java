package com.TwoDo.TwoDo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BannerRequest(
    UUID gameId,
    String characterName,
    String bannerType,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
