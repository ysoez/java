package spring.transactional.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.transactional.events.model.Customer;
import spring.transactional.events.repository.CustomerRepository;
import spring.transactional.events.service.CustomerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldPersistCustomerWithToken() {
        //when
        final Customer returnedCustomer = customerService.createCustomer("Matt", "matt@gmail.com");

        //then
        final Customer persistedCustomer = customerRepository.findById(returnedCustomer.getId()).orElseThrow();
        assertEquals("matt@gmail.com", returnedCustomer.getEmail());
        assertEquals("Matt", returnedCustomer.getName());
        assertTrue(returnedCustomer.hasToken());
        assertEquals(returnedCustomer, persistedCustomer);
    }

}