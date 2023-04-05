package spring.transaction.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.transaction.event.model.Customer;
import spring.transaction.event.repository.CustomerRepository;
import spring.transaction.event.service.CustomerService;

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