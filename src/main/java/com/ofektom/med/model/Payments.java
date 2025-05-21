package com.ofektom.med.model;

import com.ofektom.med.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String billNumber;

    @NotBlank
    @Column(unique = true)
    private String invoiceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_allotment_id", nullable = false)
    @NotNull
    private RoomAllotment roomAllotment;

    @NotNull
    private LocalDate invoiceDate;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private LocalDate paymentDate;

    @NotBlank
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private Double subTotalAmount;

    @NotNull
    private Double tax;

    @NotNull
    private Double totalAmount;

    private String notes;

    private String termsAndConditions;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentItem> paymentItems;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Payments(){}

    public Payments(Long id, String billNumber, String invoiceNumber, User patient, User doctor, RoomAllotment roomAllotment,
                    LocalDate invoiceDate, LocalDate dueDate, LocalDate paymentDate, String paymentMethod, PaymentStatus paymentStatus,
                    Double subTotalAmount, Double tax, Double totalAmount, String notes, String termsAndConditions,
                    List<PaymentItem> paymentItems, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.billNumber = billNumber;
        this.invoiceNumber = invoiceNumber;
        this.patient = patient;
        this.doctor = doctor;
        this.roomAllotment = roomAllotment;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.subTotalAmount = subTotalAmount;
        this.tax = tax;
        this.totalAmount = totalAmount;
        this.notes = notes;
        this.termsAndConditions = termsAndConditions;
        this.paymentItems = paymentItems;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    public User getPatient() { return patient; }
    public void setPatient(User patient) { this.patient = patient; }
    public User getDoctor() { return doctor; }
    public void setDoctor(User doctor) { this.doctor = doctor; }
    public RoomAllotment getRoomAllotment() { return roomAllotment; }
    public void setRoomAllotment(RoomAllotment roomAllotment) { this.roomAllotment = roomAllotment; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public Double getSubTotalAmount() { return subTotalAmount; }
    public void setSubTotalAmount(Double subTotalAmount) { this.subTotalAmount = subTotalAmount; }
    public Double getTax() { return tax; }
    public void setTax(Double tax) { this.tax = tax; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getTermsAndConditions() { return termsAndConditions; }
    public void setTermsAndConditions(String termsAndConditions) { this.termsAndConditions = termsAndConditions; }
    public List<PaymentItem> getPaymentItems() { return paymentItems; }
    public void setPaymentItems(List<PaymentItem> paymentItems) { this.paymentItems = paymentItems; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}