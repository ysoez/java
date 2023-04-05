package spring.transaction.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.transaction.event.model.Customer;
import spring.transaction.event.repository.CustomerRepository;
import spring.transaction.event.service.CustomerService;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("async")
public class CustomerServiceAsyncTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldPersistCustomerWithToken() {
        //when
        final Customer returnedCustomer = customerService.createCustomer("Matt", "matt@gmail.com");

        //then
        assertEquals("matt@gmail.com", returnedCustomer.getEmail());
        assertEquals("Matt", returnedCustomer.getName());

        //and
        await().atMost(5, SECONDS).until(() -> customerTokenIsPersisted(returnedCustomer.getId()));
    }

    private boolean customerTokenIsPersisted(Long id) {
        final Customer persistedCustomer = customerRepository.findById(id).orElseThrow();
        return persistedCustomer.hasToken();
    }

}