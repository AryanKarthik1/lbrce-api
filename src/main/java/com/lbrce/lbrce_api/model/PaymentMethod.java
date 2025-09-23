package com.lbrce.lbrce_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "payment_method_id", updatable = false, nullable = false)
    private UUID paymentMethodId;

    @Column(nullable = false, unique = true, length = 50)
    private String name;   // UPI, Credit Card, NetBanking, COD, Wallet, etc.

    @Column(length = 120, unique = true)
    private String slug;   // e.g. "upi", "credit-card", "cod"

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false, length = 20)
    private String status = "active"; // active | inactive

    // Getters & Setters
    public UUID getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(UUID paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
