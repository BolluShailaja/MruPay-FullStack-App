package com.example.phonepaywalletbackend.service;

import com.example.phonepaywalletbackend.entity.Transaction;
import com.example.phonepaywalletbackend.entity.User;
import com.example.phonepaywalletbackend.entity.Wallet;
import com.example.phonepaywalletbackend.repository.TransactionRepository;
import com.example.phonepaywalletbackend.repository.UserRepository;
import com.example.phonepaywalletbackend.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(
            WalletRepository walletRepository,
            UserRepository userRepository,
            TransactionRepository transactionRepository
    ) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // ================= ADD MONEY =================
    @Transactional
    public Wallet addMoney(Long userId, BigDecimal amount) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepository.findByUser(user)
                .orElseGet(() -> {
                    Wallet w = new Wallet();
                    w.setUser(user);
                    w.setBalance(BigDecimal.ZERO);
                    return w;
                });

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        // ✅ TRANSACTION ENTRY
        Transaction tx = new Transaction();
        tx.setSenderUpi("BANK");
        tx.setReceiverUpi(user.getUpiId());
        tx.setAmount(amount);
        tx.setDate(LocalDateTime.now());
        tx.setStatus(Transaction.TransactionStatus.SUCCESS);

        transactionRepository.save(tx);

        return wallet;
    }

    // ================= SEND MONEY =================
    @Transactional
    public void sendMoney(Long senderId, String receiverUpi, BigDecimal amount) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByUpiId(receiverUpi)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Wallet senderWallet = walletRepository.findByUser(sender)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository.findByUser(receiver)
                .orElseGet(() -> {
                    Wallet w = new Wallet();
                    w.setUser(receiver);
                    w.setBalance(BigDecimal.ZERO);
                    return w;
                });

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        // ✅ TRANSACTION ENTRY
        Transaction tx = new Transaction();
        tx.setSenderUpi(sender.getUpiId());
        tx.setReceiverUpi(receiver.getUpiId());
        tx.setAmount(amount);
        tx.setDate(LocalDateTime.now());
        tx.setStatus(Transaction.TransactionStatus.SUCCESS);

        transactionRepository.save(tx);
    }

    // ================= GET BALANCE =================
    public BigDecimal getBalance(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return wallet.getBalance();
    }
}