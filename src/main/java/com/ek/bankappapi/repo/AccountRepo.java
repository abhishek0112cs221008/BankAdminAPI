package com.ek.bankappapi.repo;

import com.ek.bankappapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, String> {
    List<Account> findByCustomer_CustomerId(Long customerId);
    boolean existsByAccountNumber(String accountNumber);
}