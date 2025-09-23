package com.lbrce.lbrce_api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record VendorResponse(
        UUID id,
        String name,
        String email,
        String phone,
        String taxId,
        String addressText,
        String status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
