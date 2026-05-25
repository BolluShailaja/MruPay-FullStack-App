package com.example.phonepaywalletbackend.controller;

import com.example.phonepaywalletbackend.dto.AddMoneyRequest;
import com.example.phonepaywalletbackend.entity.Wallet;
import com.example.phonepaywalletbackend.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // ================= ADD MONEY =================
    @PostMapping("/add")
    public Wallet addMoney(@RequestBody AddMoneyRequest request) {
        return walletService.addMoney(
                request.getUserId(),
                request.getAmount()   // BigDecimal ✅
        );
    }

    // ================= GET BALANCE =================
    @GetMapping("/balance/{userId}")
    public BigDecimal getBalance(@PathVariable Long userId) {
        return walletService.getBalance(userId); // BigDecimal ✅
    }
}