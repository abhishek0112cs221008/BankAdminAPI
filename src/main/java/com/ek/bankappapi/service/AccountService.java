package com.ek.bankappapi.service;

import com.ek.bankappapi.model.Account;
import com.ek.bankappapi.model.Customer;
import com.ek.bankappapi.model.Transaction;
import com.ek.bankappapi.repo.AccountRepo;
import com.ek.bankappapi.repo.CustomerRepo;
import com.ek.bankappapi.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public Account createAccount(String accountNo, Long customerId) {
        // Check if account number already exists
        if (accountRepo.existsByAccountNumber(accountNo)) {
            throw new RuntimeException("Account number already exists: " + accountNo);
        }

        // Find customer
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        // Create account
        Account account = new Account();
        account.setAccountNumber(accountNo);
        account.setCustomer(customer);
        account.setBalance(0.0);
        account.setCreatedAt(new Date());

        return accountRepo.save(account);
    }

    @Transactional
    @Override
    public String deposit(String accountNo, Double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        Account account = accountRepo.findById(accountNo)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNo));

        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);

        // Record transaction
        transactionRepo.save(
                new Transaction(null, accountNo, amount, "DEPOSIT", new Date())
        );

        return "Deposit successful. New balance: " + account.getBalance();
    }

    @Transactional
    @Override
    public String withdraw(String accountNo, Double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        Account account = accountRepo.findById(accountNo)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNo));

        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            accountRepo.save(account);

            // Record transaction
            transactionRepo.save(
                    new Transaction(accountNo, null, amount, "WITHDRAW", new Date())
            );

            return "Withdrawal successful. New balance: " + account.getBalance();
        } else {
            throw new RuntimeException("Insufficient balance. Current balance: " + account.getBalance());
        }
    }

    @Override
    public Double getBalance(String accountNo) {
        Account account = accountRepo.findById(accountNo)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNo));
        return account.getBalance();
    }

    @Override
    public Account getAccountDetails(String accountNo) {
        return accountRepo.findById(accountNo)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNo));
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public List<Account> getAccountsByCustomerId(Long customerId) {
        return accountRepo.findByCustomer_CustomerId(customerId);
    }

    @Transactional
    @Override
    public String transfer(String fromAccountNo, String toAccountNo, Double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        if (fromAccountNo.equals(toAccountNo)) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        Account fromAccount = accountRepo.findById(fromAccountNo)
                .orElseThrow(() -> new RuntimeException("Source account not found: " + fromAccountNo));

        Account toAccount = accountRepo.findById(toAccountNo)
                .orElseThrow(() -> new RuntimeException("Destination account not found: " + toAccountNo));

        if (fromAccount.getBalance() >= amount) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            accountRepo.save(fromAccount);
            accountRepo.save(toAccount);

            // Record transaction
            transactionRepo.save(
                    new Transaction(fromAccountNo, toAccountNo, amount, "TRANSFER", new Date())
            );

            return "Transfer successful. New balance: " + fromAccount.getBalance();
        } else {
            throw new RuntimeException("Insufficient balance. Current balance: " + fromAccount.getBalance());
        }
    }

    @Transactional
    @Override
    public String closeAccount(String accountNo) {
        Account account = accountRepo.findById(accountNo)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNo));

        if (account.getBalance() > 0) {
            throw new RuntimeException("Cannot close account with remaining balance: " + account.getBalance());
        }

        accountRepo.delete(account);
        return "Account closed successfully";
    }
}