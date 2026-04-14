package com.TwoDo.TwoDo.dto;

import java.util.UUID;

public record TrackingRequest (
    UUID bannerId
) {}
