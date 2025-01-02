package bank.account.query.domain;

import bank.domain.AbstractEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<BankAccount, String> {

    Optional<BankAccount> findByAccountHolder(String accountHolder);

    List<AbstractEntity> findByBalanceGreaterThan(double balance);

    List<AbstractEntity> findByBalanceLessThan(double balance);
}
