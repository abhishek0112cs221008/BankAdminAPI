package com.ek.bankappapi.rest;

import com.ek.bankappapi.model.Transaction;
import com.ek.bankappapi.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/transaction")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    // Get all transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // Get transactions by account number
    @GetMapping("/account/{accountNo}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(
            @PathVariable String accountNo) {
        List<Transaction> transactions = transactionService.getTransactionsByAccount(accountNo);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // Get recent transactions (top 10)
    @GetMapping("/account/{accountNo}/recent")
    public ResponseEntity<List<Transaction>> getRecentTransactions(
            @PathVariable String accountNo) {
        List<Transaction> transactions = transactionService.getRecentTransactionsByAccount(accountNo);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // Get transactions by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            @PathVariable String type) {
        List<Transaction> transactions = transactionService.getTransactionsByType(type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}