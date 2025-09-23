package com.lbrce.lbrce_api.dto;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String slug,
        String description,
        String status
) {}

