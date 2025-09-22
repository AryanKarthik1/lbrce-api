package com.lbrce.lbrce_api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCreateRequest(
        String name,
        String slug,
        String sku,
        BigDecimal price,
        String currencyCode,
        String description,
        UUID vendorId,
        UUID brandId,
        UUID categoryId
) {}
