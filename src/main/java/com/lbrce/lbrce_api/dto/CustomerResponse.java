package com.lbrce.lbrce_api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
