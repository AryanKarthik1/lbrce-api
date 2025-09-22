package com.lbrce.lbrce_api.repository;

import com.lbrce.lbrce_api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsBySku(String sku);
    boolean existsBySlug(String slug);

    boolean existsBySkuAndProductIdNot(String sku, UUID productId);
    boolean existsBySlugAndProductIdNot(String slug, UUID productId);

    Optional<Product> findByProductIdAndIsDeletedFalse(UUID id);

    Page<Product> findByIsDeletedFalse(Pageable pageable);

    @Query("""
           select p from Product p
           where p.isDeleted = false and (
                 lower(p.name) like lower(concat('%', :q, '%')) or
                 lower(p.slug) like lower(concat('%', :q, '%')) or
                 lower(p.sku)  like lower(concat('%', :q, '%'))
           )
           """)
    Page<Product> search(@Param("q") String q, Pageable pageable);
}
