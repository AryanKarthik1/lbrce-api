package com.lbrce.lbrce_api.dto;

public record WarehouseUpdateRequest(
        String name,
        String address,
        String city,
        String state,
        String postalCode,
        String status
) {}
