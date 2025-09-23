package com.lbrce.lbrce_api.dto;

public record BrandCreateRequest(
        String name,
        String slug,
        String description,
        String status
) {}
