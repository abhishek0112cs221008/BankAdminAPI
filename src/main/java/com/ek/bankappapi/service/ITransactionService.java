package com.ek.bankappapi.service;

import com.ek.bankappapi.model.Transaction;

import java.util.List;

public interface ITransactionService {
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByAccount(String accountNo);
    List<Transaction> getRecentTransactionsByAccount(String accountNo);
    List<Transaction> getTransactionsByType(String type);
}