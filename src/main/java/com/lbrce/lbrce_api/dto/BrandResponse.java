package com.lbrce.lbrce_api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record BrandResponse(
        UUID id,
        String name,
        String slug,
        String description,
        String status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
