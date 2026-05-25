package com.example.phonepaywalletbackend.dto;

import java.math.BigDecimal;

public class SendMoneyRequest {

    private Long senderUserId;
    private String receiverUpi;
    private BigDecimal amount;

    public Long getSenderUserId() {
        return senderUserId;
    }

    public String getReceiverUpi() {
        return receiverUpi;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}