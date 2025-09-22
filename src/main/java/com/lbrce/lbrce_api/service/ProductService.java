package com.lbrce.lbrce_api.service;

import com.lbrce.lbrce_api.dto.ProductCreateRequest;
import com.lbrce.lbrce_api.dto.ProductResponse;
import com.lbrce.lbrce_api.dto.ProductUpdateRequest;
import com.lbrce.lbrce_api.model.Product;
import com.lbrce.lbrce_api.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    /* ---------- Create ---------- */
    @Transactional
    public ProductResponse create(ProductCreateRequest r) {
        if (repo.existsBySku(r.sku())) throw new IllegalArgumentException("SKU already exists");
        if (repo.existsBySlug(r.slug())) throw new IllegalArgumentException("Slug already exists");

        var now = OffsetDateTime.now(ZoneOffset.UTC);

        Product p = new Product();
        p.setName(r.name());
        p.setSlug(r.slug());
        p.setSku(r.sku());
        p.setPrice(r.price());
        p.setCurrencyCode(r.currencyCode());
        p.setDescription(r.description());
        p.setVendorId(r.vendorId());
        p.setBrandId(r.brandId());
        p.setCategoryId(r.categoryId());
        p.setIsActive(true);
        p.setIsDeleted(false);
        p.setCreatedAt(now);
        p.setUpdatedAt(now);

        p = repo.save(p);
        return toResponse(p);
    }

    /* ---------- Read ---------- */
    @Transactional(readOnly = true)
    public ProductResponse get(UUID id) {
        Product p = repo.findByProductIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return toResponse(p);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> list(String q, Pageable pageable) {
        Page<Product> page = (q == null || q.isBlank())
                ? repo.findByIsDeletedFalse(pageable)
                : repo.search(q.trim(), pageable);
        return page.map(this::toResponse);
    }

    /* ---------- Update ---------- */
    @Transactional
    public ProductResponse update(UUID id, ProductUpdateRequest r) {
        Product p = repo.findByProductIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (r.sku() != null && repo.existsBySkuAndProductIdNot(r.sku(), id))
            throw new IllegalArgumentException("SKU already exists");
        if (r.slug() != null && repo.existsBySlugAndProductIdNot(r.slug(), id))
            throw new IllegalArgumentException("Slug already exists");

        if (r.name() != null)         p.setName(r.name());
        if (r.slug() != null)         p.setSlug(r.slug());
        if (r.sku() != null)          p.setSku(r.sku());
        if (r.price() != null)        p.setPrice(r.price());
        if (r.currencyCode() != null) p.setCurrencyCode(r.currencyCode());
        if (r.description() != null)  p.setDescription(r.description());
        if (r.vendorId() != null)     p.setVendorId(r.vendorId());
        if (r.brandId() != null)      p.setBrandId(r.brandId());
        if (r.categoryId() != null)   p.setCategoryId(r.categoryId());
        if (r.isActive() != null)     p.setIsActive(r.isActive());

        p.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        p = repo.save(p);
        return toResponse(p);
    }

    /* ---------- Delete (soft) ---------- */
    @Transactional
    public void delete(UUID id) {
        Product p = repo.findByProductIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        p.setIsDeleted(true);
        p.setIsActive(false);
        p.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        repo.save(p);
    }

    /* ---------- mapper ---------- */
    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getProductId(),
                p.getName(),
                p.getSlug(),
                p.getSku(),
                p.getPrice(),
                p.getCurrencyCode(),
                p.getDescription(),
                p.getIsActive(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
