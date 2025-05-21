package com.ofektom.med.dto.response;

import java.util.UUID;

public class AssetResponse {
    private UUID id;
    private String name;
    private int quantity;
    private String condition;
    private String status;
    private String description;
    private Long managedById; // Reference to User ID

    public AssetResponse() {}

    public AssetResponse(UUID id, String name, int quantity, String condition, String status, String description, Long managedById) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.condition = condition;
        this.status = status;
        this.description = description;
        this.managedById = managedById;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getManagedById() { return managedById; }
    public void setManagedById(Long managedById) { this.managedById = managedById; }
}
