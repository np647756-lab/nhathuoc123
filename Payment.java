package com.hkcare.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Payment {
    private String id;
    private String method;
    private long amount;
    private String status;
    private String orderInfo;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
    private Map<String,String> bankInfo;
    private String qrFallback;

    public Payment() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getOrderInfo() { return orderInfo; }
    public void setOrderInfo(String orderInfo) { this.orderInfo = orderInfo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
    public Map<String,String> getBankInfo() { return bankInfo; }
    public void setBankInfo(Map<String,String> bankInfo) { this.bankInfo = bankInfo; }
    public String getQrFallback() { return qrFallback; }
    public void setQrFallback(String qrFallback) { this.qrFallback = qrFallback; }
}
