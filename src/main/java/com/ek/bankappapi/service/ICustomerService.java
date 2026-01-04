package com.ek.bankappapi.service;

import com.ek.bankappapi.model.Customer;

import java.util.List;

public interface ICustomerService {
    Customer createCustomer(Customer customer);
    String updateCustomer(Long id, Customer customer);
    String deleteCustomer(Long id);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer getCustomerByEmail(String email);
}