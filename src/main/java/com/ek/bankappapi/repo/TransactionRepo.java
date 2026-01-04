package com.ek.bankappapi.repo;

import com.ek.bankappapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    // Get all transactions for an account (either from or to)
    List<Transaction> findByFromAccountOrToAccountOrderByTransactionDateDesc(
            String fromAccount, String toAccount
    );

    // Get transactions by type
    List<Transaction> findByTransactionTypeOrderByTransactionDateDesc(String type);

    // Get recent transactions (top 10)
    List<Transaction> findTop10ByFromAccountOrToAccountOrderByTransactionDateDesc(
            String fromAccount, String toAccount
    );
}