package com.lbrce.lbrce_api.repository;

import com.lbrce.lbrce_api.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {

    boolean existsBySlug(String slug);

    boolean existsBySlugAndBrandIdNot(String slug, UUID brandId);

    Optional<Brand> findByBrandIdAndIsDeletedFalse(UUID id);

    Page<Brand> findByIsDeletedFalse(Pageable pageable);

    @Query("""
           select b from Brand b
           where b.isDeleted = false and (
                 lower(b.name) like lower(concat('%', :q, '%')) or
                 lower(b.slug) like lower(concat('%', :q, '%'))
           )
           """)
    Page<Brand> search(@Param("q") String q, Pageable pageable);
}
