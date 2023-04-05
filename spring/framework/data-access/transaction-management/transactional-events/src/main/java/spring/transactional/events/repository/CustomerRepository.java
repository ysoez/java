package spring.transactional.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.transactional.events.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}