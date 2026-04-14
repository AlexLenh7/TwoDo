package com.PityPlanner.PityPlanner.dto;

import java.util.UUID;

public record TrackingResponse (
    UUID id,
    UUID userId,
    UUID bannerId,
    String characterName,
    String gameName,
    String bannerType
) {}
