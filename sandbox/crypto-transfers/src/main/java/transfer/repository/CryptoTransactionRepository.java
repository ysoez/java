package transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transfer.domain.entity.CryptoTransactionEntity;

public interface CryptoTransactionRepository extends JpaRepository<CryptoTransactionEntity, Long> {
}
