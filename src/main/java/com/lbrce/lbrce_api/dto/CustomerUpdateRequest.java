package com.lbrce.lbrce_api.dto;

public record CustomerUpdateRequest(
        String firstName,
        String lastName,
        String email,
        String phone,
        String status
) {}
