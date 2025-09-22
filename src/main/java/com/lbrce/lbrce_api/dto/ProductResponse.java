package com.lbrce.lbrce_api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String slug,
        String sku,
        BigDecimal price,
        String currencyCode,
        String description,
        Boolean isActive,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
