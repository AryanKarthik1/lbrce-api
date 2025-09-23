package com.lbrce.lbrce_api.repository;

import com.lbrce.lbrce_api.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsBySlug(String slug);

    Optional<Category> findByCategoryId(UUID id);

    Page<Category> findByStatus(String status, Pageable pageable);
}
