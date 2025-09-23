package com.lbrce.lbrce_api.repository;

import com.lbrce.lbrce_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    boolean existsByName(String name);
    boolean existsBySlug(String slug);
}
