package com.example.phonepaywalletbackend.service;

import com.example.phonepaywalletbackend.entity.Transaction;
import com.example.phonepaywalletbackend.entity.User;
import com.example.phonepaywalletbackend.entity.Wallet;
import com.example.phonepaywalletbackend.repository.TransactionRepository;
import com.example.phonepaywalletbackend.repository.UserRepository;
import com.example.phonepaywalletbackend.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(
            UserRepository userRepository,
            WalletRepository walletRepository,
            TransactionRepository transactionRepository
    ) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void sendMoney(Long senderUserId, String receiverUpi, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        // 1️⃣ Sender
        User sender = userRepository.findById(senderUserId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Wallet senderWallet = walletRepository.findByUser(sender)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        // 2️⃣ Receiver (by UPI)
        User receiver = userRepository.findByUpiId(receiverUpi)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Wallet receiverWallet = walletRepository.findByUser(receiver)
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        // 3️⃣ Balance check
        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // 4️⃣ Update balances
        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        // 5️⃣ Save transaction
        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setSenderUpi(sender.getUpiId());
        tx.setReceiverUpi(receiverUpi);
        tx.setStatus(Transaction.TransactionStatus.SUCCESS);
        tx.setDate(LocalDateTime.now());

        transactionRepository.save(tx);
    }
}