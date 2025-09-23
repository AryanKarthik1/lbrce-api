package com.lbrce.lbrce_api.repository;

import com.lbrce.lbrce_api.model.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByEmailIgnoreCase(String email);

    Optional<Vendor> findByVendorIdAndStatus(UUID id, String status);

    Page<Vendor> findByStatus(String status, Pageable pageable);

    @Query("""
           select v from Vendor v
           where v.status = :status and (
                 lower(v.name) like lower(concat('%', :q, '%')) or
                 lower(v.email) like lower(concat('%', :q, '%')) or
                 lower(v.phone) like lower(concat('%', :q, '%')) or
                 lower(v.taxId) like lower(concat('%', :q, '%'))
           )
           """)
    Page<Vendor> search(@Param("q") String q,
                        @Param("status") String status,
                        Pageable pageable);
}
