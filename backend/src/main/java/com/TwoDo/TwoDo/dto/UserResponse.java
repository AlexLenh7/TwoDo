package com.TwoDo.TwoDo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse (
    UUID id,
    String email,
    String name,
    String picture,
    LocalDateTime createdAt
) {}
