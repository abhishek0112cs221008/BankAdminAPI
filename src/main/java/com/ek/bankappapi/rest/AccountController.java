package com.ek.bankappapi.rest;

import com.ek.bankappapi.model.Account;
import com.ek.bankappapi.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    // Open new account
    @PostMapping
    public ResponseEntity<?> openAccount(
            @RequestParam String accountNo,
            @RequestParam Long customerId) {
        try {
            Account account = accountService.createAccount(accountNo, customerId);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("null", HttpStatus.BAD_REQUEST);
        }
    }

    // Deposit money
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(
            @RequestParam String accountNo,
            @RequestParam Double amount) {
        try {
            String result = accountService.deposit(accountNo, amount);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Withdraw money
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(
            @RequestParam String accountNo,
            @RequestParam Double amount) {
        try {
            String result = accountService.withdraw(accountNo, amount);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get balance
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam String accountNo) {
        try {
            Double balance = accountService.getBalance(accountNo);
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("null", HttpStatus.NOT_FOUND);
        }
    }

    // Get account details
    @GetMapping("/{accountNo}")
    public ResponseEntity<?> getAccountDetails(@PathVariable String accountNo) {
        try {
            Account account = accountService.getAccountDetails(accountNo);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("null", HttpStatus.NOT_FOUND);
        }
    }

    // Get all accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // Get accounts by customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomer(@PathVariable Long customerId) {
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // Transfer money
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam String fromAccountNo,
            @RequestParam String toAccountNo,
            @RequestParam Double amount) {
        try {
            String result = accountService.transfer(fromAccountNo, toAccountNo, amount);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Close account
    @DeleteMapping("/{accountNo}")
    public ResponseEntity<String> closeAccount(@PathVariable String accountNo) {
        try {
            String result = accountService.closeAccount(accountNo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}