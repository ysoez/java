package spring.transaction.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.transaction.event.model.Customer;
import spring.transaction.event.model.CustomerCreatedEvent;
import spring.transaction.event.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Customer createCustomer(String name, String email) {
        final Customer newCustomer = customerRepository.save(new Customer(name, email));;
        applicationEventPublisher.publishEvent(new CustomerCreatedEvent(newCustomer));
        return newCustomer;
    }
}