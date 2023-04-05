package spring.transactional.events.service;

import spring.transactional.events.model.Customer;

public interface TokenGenerator {
    void generateToken(Customer customer);
}