package spring.transaction.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.transaction.event.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}