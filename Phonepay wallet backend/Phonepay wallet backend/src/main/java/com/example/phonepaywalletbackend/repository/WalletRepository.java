package com.example.phonepaywalletbackend.repository;

import com.example.phonepaywalletbackend.entity.User;
import com.example.phonepaywalletbackend.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUser(User user);
}