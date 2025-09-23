package com.lbrce.lbrce_api.service;

import com.lbrce.lbrce_api.dto.VendorCreateRequest;
import com.lbrce.lbrce_api.dto.VendorResponse;
import com.lbrce.lbrce_api.dto.VendorUpdateRequest;
import com.lbrce.lbrce_api.model.Vendor;
import com.lbrce.lbrce_api.repository.VendorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;

@Service
public class VendorService {

    private static final Set<String> ALLOWED_STATUS = Set.of("active","suspended");

    private final VendorRepository repo;

    public VendorService(VendorRepository repo) { this.repo = repo; }

    private static String normalizeStatus(String s) {
        if (s == null || s.isBlank()) return "active";
        String v = s.trim().toLowerCase();
        if (!ALLOWED_STATUS.contains(v)) throw new IllegalArgumentException("Invalid status: " + s);
        return v;
    }

    /* Create */
    @Transactional
    public VendorResponse create(VendorCreateRequest r) {
        if (r.name() == null || r.name().isBlank()) throw new IllegalArgumentException("Name is required");
        if (repo.existsByNameIgnoreCase(r.name())) throw new IllegalArgumentException("Vendor name already exists");
        if (r.email() != null && !r.email().isBlank() && repo.existsByEmailIgnoreCase(r.email()))
            throw new IllegalArgumentException("Email already exists");

        var now = OffsetDateTime.now(ZoneOffset.UTC);
        Vendor v = new Vendor();
        v.setName(r.name().trim());
        v.setEmail(r.email());
        v.setPhone(r.phone());
        v.setTaxId(r.taxId());
        v.setAddressText(r.addressText());
        v.setStatus(normalizeStatus(r.status()));
        v.setCreatedAt(now);
        v.setUpdatedAt(now);

        v = repo.save(v);
        return toResponse(v);
    }

    /* Read */
    @Transactional(readOnly = true)
    public VendorResponse get(UUID id) {
        Vendor v = repo.findByVendorIdAndStatus(id, "active")
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
        return toResponse(v);
    }

    @Transactional(readOnly = true)
    public Page<VendorResponse> list(String q, Pageable pageable) {
        Page<Vendor> page = (q == null || q.isBlank())
                ? repo.findByStatus("active", pageable)
                : repo.search(q.trim(), "active", pageable);
        return page.map(this::toResponse);
    }

    /* Update */
    @Transactional
    public VendorResponse update(UUID id, VendorUpdateRequest r) {
        Vendor v = repo.findByVendorIdAndStatus(id, "active")
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));

        if (r.name() != null)  v.setName(r.name());
        if (r.email() != null) v.setEmail(r.email());
        if (r.phone() != null) v.setPhone(r.phone());
        if (r.taxId() != null) v.setTaxId(r.taxId());
        if (r.addressText() != null) v.setAddressText(r.addressText());
        if (r.status() != null) v.setStatus(normalizeStatus(r.status()));

        v.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        v = repo.save(v);
        return toResponse(v);
    }

    /* Delete (soft -> suspended) */
    @Transactional
    public void delete(UUID id) {
        Vendor v = repo.findByVendorIdAndStatus(id, "active")
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
        v.setStatus("suspended");
        v.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        repo.save(v);
    }

    /* mapper */
    private VendorResponse toResponse(Vendor v) {
        return new VendorResponse(
                v.getVendorId(),
                v.getName(),
                v.getEmail(),
                v.getPhone(),
                v.getTaxId(),
                v.getAddressText(),
                v.getStatus(),
                v.getCreatedAt(),
                v.getUpdatedAt()
        );
    }
}
