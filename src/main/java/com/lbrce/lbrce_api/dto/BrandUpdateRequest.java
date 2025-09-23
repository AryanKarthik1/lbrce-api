package com.lbrce.lbrce_api.dto;

public record BrandUpdateRequest(
        String name,
        String slug,
        String description,
        String status
) {}
