package transfer.service.transaction;

import transfer.domain.TransactionRequest;
import transfer.domain.entity.Transaction;

import java.util.Optional;

public interface TransactionService<TX extends Transaction, TYPE extends Enum<TYPE>> {

    Optional<TX> findById(Long txId);

    TX start(TransactionRequest request, TYPE type);

    void complete(Long txId);

}
