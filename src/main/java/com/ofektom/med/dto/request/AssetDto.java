package com.ofektom.med.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AssetDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    @NotBlank(message = "Condition is required")
    private String condition;

    @NotBlank(message = "Status is required")
    private String status;

    private String description;

    private Long managedById; // Reference to User ID

    // Constructors
    public AssetDto() {}

    public AssetDto(String name, int quantity, String condition, String status, String description, Long managedById) {
        this.name = name;
        this.quantity = quantity;
        this.condition = condition;
        this.status = status;
        this.description = description;
        this.managedById = managedById;
    }

    // Getters and Setters
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
