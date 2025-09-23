package com.lbrce.lbrce_api.dto;

public record VendorCreateRequest(
        String name,
        String email,
        String phone,
        String taxId,
        String addressText,
        String status // optional; default "active" if null/blank
) {}
