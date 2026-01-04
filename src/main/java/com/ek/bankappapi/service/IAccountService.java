package com.ek.bankappapi.service;

import com.ek.bankappapi.model.Account;

import java.util.List;

public interface IAccountService {
    Account createAccount(String accountNo, Long customerId);
    String deposit(String accountNo, Double amount);
    String withdraw(String accountNo, Double amount);
    Double getBalance(String accountNo);
    Account getAccountDetails(String accountNo);
    List<Account> getAllAccounts();
    List<Account> getAccountsByCustomerId(Long customerId);
    String transfer(String fromAccountNo, String toAccountNo, Double amount);
    String closeAccount(String accountNo);
}