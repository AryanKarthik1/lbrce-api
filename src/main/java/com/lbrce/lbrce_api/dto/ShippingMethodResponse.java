package com.lbrce.lbrce_api.dto;

import java.util.UUID;

public record ShippingMethodResponse(
        UUID id,
        String name,
        String description,
        String status
) {}
