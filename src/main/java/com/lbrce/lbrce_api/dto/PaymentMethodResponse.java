package com.lbrce.lbrce_api.dto;

import java.util.UUID;

public record PaymentMethodResponse(
        UUID id,
        String name,
        String slug,
        String description,
        String status
) {}
