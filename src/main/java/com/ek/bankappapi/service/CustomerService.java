package com.ek.bankappapi.service;

import com.ek.bankappapi.model.Customer;
import com.ek.bankappapi.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public Customer createCustomer(Customer customer) {
        // Check if email already exists
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("Email already exists: " + customer.getEmail());
        }
        return customerRepo.save(customer);
    }

    @Override
    public String updateCustomer(Long id, Customer customer) {
        Optional<Customer> optional = customerRepo.findById(id);
        if (optional.isPresent()) {
            Customer existingCustomer = optional.get();

            // Update fields
            existingCustomer.setCustomerName(customer.getCustomerName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setCustomerPhone(customer.getCustomerPhone());
            existingCustomer.setCustomerAddress(customer.getCustomerAddress());

            customerRepo.save(existingCustomer);
            return "Customer updated successfully";
        }
        return "Customer not found with id: " + id;
    }

    @Override
    public String deleteCustomer(Long id) {
        Optional<Customer> optional = customerRepo.findById(id);
        if (optional.isPresent()) {
            customerRepo.deleteById(id);
            return "Customer deleted successfully";
        }
        return "Customer not found with id: " + id;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));
    }
}