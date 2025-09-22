package com.lbrce.lbrce_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "product_id", nullable = false, updatable = false)
    private UUID productId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "vendor_id")
    private UUID vendorId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "brand_id")
    private UUID brandId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 220, unique = true)
    private String slug;

    @Column(length = 80, unique = true)
    private String sku;

    @Column(columnDefinition = "text")
    private String description;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    // ***** IMPORTANT: your DB has CHAR(3), not VARCHAR(3)
    @Column(name = "currency_code", columnDefinition = "char(3)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String currencyCode;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "created_by")
    private UUID createdBy;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "updated_by")
    private UUID updatedBy;

    // ---- getters & setters ----
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }

    public UUID getBrandId() { return brandId; }
    public void setBrandId(UUID brandId) { this.brandId = brandId; }

    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
}
