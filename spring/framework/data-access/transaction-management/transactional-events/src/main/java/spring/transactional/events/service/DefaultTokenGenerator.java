package spring.transactional.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.transactional.events.model.Customer;
import spring.transactional.events.repository.CustomerRepository;

@Service
@Profile("!async")
@RequiredArgsConstructor
public class DefaultTokenGenerator implements TokenGenerator {

    private final CustomerRepository customerRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateToken(Customer customer) {
        final String token = String.valueOf(customer.hashCode());
        customer.activatedWith(token);
        customerRepository.save(customer);
    }
}