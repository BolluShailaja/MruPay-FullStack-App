package com.example.phonepaywalletbackend.controller;

import com.example.phonepaywalletbackend.dto.SendMoneyRequest;
import com.example.phonepaywalletbackend.service.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/send")
    public void sendMoney(@RequestBody SendMoneyRequest request) {
        transactionService.sendMoney(
                request.getSenderUserId(),
                request.getReceiverUpi(),
                request.getAmount()
        );
    }
}