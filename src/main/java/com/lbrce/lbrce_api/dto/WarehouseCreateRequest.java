package com.lbrce.lbrce_api.dto;

public record WarehouseCreateRequest(
        String name,
        String address,
        String city,
        String state,
        String postalCode,
        String status
) {}
