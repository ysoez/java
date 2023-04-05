package spring.transaction.event.service;

import spring.transaction.event.model.Customer;

public interface TokenGenerator {
    void generateToken(Customer customer);
}