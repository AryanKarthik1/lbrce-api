package com.lbrce.lbrce_api.dto;

import java.util.UUID;

public record WarehouseResponse(
        UUID warehouseId,
        String name,
        String address,
        String city,
        String state,
        String postalCode,
        String status
) {}
