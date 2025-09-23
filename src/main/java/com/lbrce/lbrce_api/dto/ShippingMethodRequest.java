package com.lbrce.lbrce_api.dto;

public record ShippingMethodRequest(
        String name,
        String description,
        String status
) {}
