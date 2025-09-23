package com.lbrce.lbrce_api.dto;

public record VendorUpdateRequest(
        String name,
        String email,
        String phone,
        String taxId,
        String addressText,
        String status // allow changing to "suspended"/"active"
) {}
