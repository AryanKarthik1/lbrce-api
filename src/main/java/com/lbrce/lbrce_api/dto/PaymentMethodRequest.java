package com.lbrce.lbrce_api.dto;

public record PaymentMethodRequest(
        String name,
        String slug,
        String description,
        String status
) {}
