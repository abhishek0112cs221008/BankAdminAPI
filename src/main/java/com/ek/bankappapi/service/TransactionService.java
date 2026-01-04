package com.ek.bankappapi.service;

import com.ek.bankappapi.model.Transaction;
import com.ek.bankappapi.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountNo) {
        return transactionRepo.findByFromAccountOrToAccountOrderByTransactionDateDesc(
                accountNo, accountNo
        );
    }

    @Override
    public List<Transaction> getRecentTransactionsByAccount(String accountNo) {
        return transactionRepo.findTop10ByFromAccountOrToAccountOrderByTransactionDateDesc(
                accountNo, accountNo
        );
    }

    @Override
    public List<Transaction> getTransactionsByType(String type) {
        return transactionRepo.findByTransactionTypeOrderByTransactionDateDesc(type);
    }
}