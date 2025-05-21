package com.ofektom.med.dto.response;

import java.time.LocalDateTime;

public class PaymentItemResponse {
    private Long id;
    private String itemName;
    private Integer quantity;
    private Double unitCost;
    private Double charges;
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PaymentItemResponse() {}

    public PaymentItemResponse(Long id, String itemName, Integer quantity, Double unitCost, Double charges,
                               Double total, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.charges = charges;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getUnitCost() { return unitCost; }
    public void setUnitCost(Double unitCost) { this.unitCost = unitCost; }
    public Double getCharges() { return charges; }
    public void setCharges(Double charges) { this.charges = charges; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}