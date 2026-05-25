package com.example.phonepaywalletbackend.repository;

import com.example.phonepaywalletbackend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}