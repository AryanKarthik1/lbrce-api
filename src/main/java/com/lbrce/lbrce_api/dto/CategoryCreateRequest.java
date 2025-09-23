package com.lbrce.lbrce_api.dto;

public record CategoryCreateRequest(
        String name,
        String slug,
        String description,
        String status
) {}
