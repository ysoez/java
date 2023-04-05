package spring.transaction.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import spring.transaction.event.model.Customer;
import spring.transaction.event.repository.CustomerRepository;

@Service
@Profile("async")
@RequiredArgsConstructor
public class AsyncTokenGenerator implements TokenGenerator {

    private final CustomerRepository customerRepository;

    public void generateToken(Customer customer) {
        final String token = String.valueOf(customer.hashCode());
        customer.activatedWith(token);
        customerRepository.save(customer);
    }

}