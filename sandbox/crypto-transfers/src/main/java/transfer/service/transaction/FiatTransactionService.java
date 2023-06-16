package transfer.service.transaction;

import org.springframework.stereotype.Service;
import transfer.domain.entity.FiatTransactionEntity;

import java.util.Optional;

@Service
public class FiatTransactionService {
    public Optional<FiatTransactionEntity> findByExternalId(String transactionId) {
        return null;
    }

}
