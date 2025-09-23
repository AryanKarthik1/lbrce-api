package com.lbrce.lbrce_api.dto;

public record CategoryUpdateRequest(
        String name,
        String slug,
        String description,
        String status
) {}

