package com.ofektom.med.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.UUID;

@Entity
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Min(value = 0, message = "Quantity cannot be negative")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @NotBlank(message = "Condition is required")
    @Column(name = "condition", nullable = false)
    private String condition;

    @NotBlank(message = "Status is required")
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managed_by", nullable = true)
    private User managedBy;

    // Constructors
    public Asset() {}

    public Asset(@NotBlank @Size(min = 2) String name, @Min(0) int quantity,
                 @NotBlank String condition, @NotBlank String status, String description, User managedBy) {
        this.name = name;
        this.quantity = quantity;
        this.condition = condition;
        this.status = status;
        this.description = description;
        this.managedBy = managedBy;
    }

    // Getters and Setters
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

    public User getManagedBy() { return managedBy; }
    public void setManagedBy(User managedBy) { this.managedBy = managedBy; }
}
