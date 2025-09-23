package com.lbrce.lbrce_api.service;

import com.lbrce.lbrce_api.dto.CategoryCreateRequest;
import com.lbrce.lbrce_api.dto.CategoryResponse;
import com.lbrce.lbrce_api.dto.CategoryUpdateRequest;
import com.lbrce.lbrce_api.model.Category;
import com.lbrce.lbrce_api.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    /* Create */
    @Transactional
    public CategoryResponse create(CategoryCreateRequest r) {
        if (repo.existsBySlug(r.slug())) throw new IllegalArgumentException("Slug already exists");

        Category c = new Category();
        c.setName(r.name());
        c.setSlug(r.slug());
        c.setDescription(r.description());
        c.setStatus(r.status() != null ? r.status() : "active");

        c = repo.save(c);
        return toResponse(c);
    }

    /* Read (by ID) */
    @Transactional(readOnly = true)
    public CategoryResponse get(UUID id) {
        Category c = repo.findByCategoryId(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return toResponse(c);
    }

    /* List */
    @Transactional(readOnly = true)
    public Page<CategoryResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    /* Update */
    @Transactional
    public CategoryResponse update(UUID id, CategoryUpdateRequest r) {
        Category c = repo.findByCategoryId(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (r.name() != null) c.setName(r.name());
        if (r.slug() != null) c.setSlug(r.slug());
        if (r.description() != null) c.setDescription(r.description());
        if (r.status() != null) c.setStatus(r.status());

        c = repo.save(c);
        return toResponse(c);
    }

    /* Delete */
    @Transactional
    public void delete(UUID id) {
        Category c = repo.findByCategoryId(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        repo.delete(c);
    }

    /* Mapper */
    private CategoryResponse toResponse(Category c) {
        return new CategoryResponse(
                c.getCategoryId(),
                c.getName(),
                c.getSlug(),
                c.getDescription(),
                c.getStatus()
        );
    }
}
