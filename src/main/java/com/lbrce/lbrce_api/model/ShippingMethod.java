package com.lbrce.lbrce_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "shipping_methods")
public class ShippingMethod {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "shipping_method_id", nullable = false, updatable = false)
    private UUID shippingMethodId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private String status; // active / inactive

    // --- Getters & Setters ---
    public UUID getShippingMethodId() { return shippingMethodId; }
    public void setShippingMethodId(UUID shippingMethodId) { this.shippingMethodId = shippingMethodId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
