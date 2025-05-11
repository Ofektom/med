package com.ofektom.med.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_items")
public class PaymentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    @NotNull
    private Payments payment;

    @NotBlank
    private String itemName;

    private Integer quantity;

    private Double unitCost;

    @NotNull
    private Double charges;

    private Double discount;

    @NotNull
    private Double total;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PaymentItem(){}

    public PaymentItem(Long id, Payments payment, String itemName, Integer quantity, Double unitCost, Double charges, Double discount, Double total, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.payment = payment;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.charges = charges;
        this.discount = discount;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Payments getPayment() {
        return payment;
    }

    public void setPayment(@NotNull Payments payment) {
        this.payment = payment;
    }

    public @NotBlank String getItemName() {
        return itemName;
    }

    public void setItemName(@NotBlank String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public @NotNull Double getCharges() {
        return charges;
    }

    public void setCharges(@NotNull Double charges) {
        this.charges = charges;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public @NotNull Double getTotal() {
        return total;
    }

    public void setTotal(@NotNull Double total) {
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
