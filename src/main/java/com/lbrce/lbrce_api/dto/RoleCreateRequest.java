package com.lbrce.lbrce_api.dto;

public record RoleCreateRequest(
        String name,
        String slug,
        String description,
        String status
) {}
