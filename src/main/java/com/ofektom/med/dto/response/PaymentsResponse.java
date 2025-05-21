package com.ofektom.med.dto.response;

import com.ofektom.med.enums.PaymentStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentsResponse {
    private Long id;
    private String billNumber;
    private String invoiceNumber;
    private UserResponse patient;
    private UserResponse doctor;
    private RoomAllotmentResponse roomAllotment;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private Double subTotalAmount;
    private Double tax;
    private Double totalAmount;
    private String notes;
    private String termsAndConditions;
    private List<PaymentItemResponse> paymentItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PaymentsResponse() {}

    public PaymentsResponse(Long id, String billNumber, String invoiceNumber, UserResponse patient, UserResponse doctor,
                            RoomAllotmentResponse roomAllotment, LocalDate invoiceDate, LocalDate dueDate, LocalDate paymentDate,
                            String paymentMethod, PaymentStatus paymentStatus, Double subTotalAmount, Double tax, Double totalAmount,
                            String notes, String termsAndConditions, List<PaymentItemResponse> paymentItems,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
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
    public UserResponse getPatient() { return patient; }
    public void setPatient(UserResponse patient) { this.patient = patient; }
    public UserResponse getDoctor() { return doctor; }
    public void setDoctor(UserResponse doctor) { this.doctor = doctor; }
    public RoomAllotmentResponse getRoomAllotment() { return roomAllotment; }
    public void setRoomAllotment(RoomAllotmentResponse roomAllotment) { this.roomAllotment = roomAllotment; }
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
    public List<PaymentItemResponse> getPaymentItems() { return paymentItems; }
    public void setPaymentItems(List<PaymentItemResponse> paymentItems) { this.paymentItems = paymentItems; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}