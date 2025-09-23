package com.lbrce.lbrce_api.service;

import com.lbrce.lbrce_api.dto.BrandCreateRequest;
import com.lbrce.lbrce_api.dto.BrandResponse;
import com.lbrce.lbrce_api.dto.BrandUpdateRequest;
import com.lbrce.lbrce_api.model.Brand;
import com.lbrce.lbrce_api.repository.BrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class BrandService {

    private final BrandRepository repo;

    public BrandService(BrandRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public BrandResponse create(BrandCreateRequest r) {
        if (repo.existsBySlug(r.slug())) throw new IllegalArgumentException("Slug already exists");

        var now = OffsetDateTime.now(ZoneOffset.UTC);

        Brand b = new Brand();
        b.setName(r.name());
        b.setSlug(r.slug());
        b.setDescription(r.description());
        b.setStatus(r.status() != null ? r.status() : "active");
        b.setIsDeleted(false);
        b.setCreatedAt(now);
        b.setUpdatedAt(now);

        b = repo.save(b);
        return toResponse(b);
    }

    @Transactional(readOnly = true)
    public BrandResponse get(UUID id) {
        Brand b = repo.findByBrandIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        return toResponse(b);
    }

    @Transactional(readOnly = true)
    public Page<BrandResponse> list(String q, Pageable pageable) {
        Page<Brand> page = (q == null || q.isBlank())
                ? repo.findByIsDeletedFalse(pageable)
                : repo.search(q.trim(), pageable);
        return page.map(this::toResponse);
    }

    @Transactional
    public BrandResponse update(UUID id, BrandUpdateRequest r) {
        Brand b = repo.findByBrandIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        if (r.slug() != null && repo.existsBySlugAndBrandIdNot(r.slug(), id))
            throw new IllegalArgumentException("Slug already exists");

        if (r.name() != null)        b.setName(r.name());
        if (r.slug() != null)        b.setSlug(r.slug());
        if (r.description() != null) b.setDescription(r.description());
        if (r.status() != null)      b.setStatus(r.status());

        b.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        b = repo.save(b);
        return toResponse(b);
    }

    @Transactional
    public void delete(UUID id) {
        Brand b = repo.findByBrandIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        b.setIsDeleted(true);
        b.setStatus("inactive");
        b.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        repo.save(b);
    }

    private BrandResponse toResponse(Brand b) {
        return new BrandResponse(
                b.getBrandId(),
                b.getName(),
                b.getSlug(),
                b.getDescription(),
                b.getStatus(),
                b.getCreatedAt(),
                b.getUpdatedAt()
        );
    }
}
