package transfer.service.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.domain.CryptoTransactionType;
import transfer.domain.TransactionRequest;
import transfer.domain.entity.CryptoTransactionEntity;
import transfer.repository.CryptoTransactionRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

import static transfer.domain.CryptoTransactionStatus.PROCESSING;
import static transfer.domain.CryptoTransactionStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class CryptoTransactionService implements TransactionService<CryptoTransactionEntity, CryptoTransactionType> {

    private final CryptoTransactionRepository cryptoTransactionRepository;

    @Override
    public Optional<CryptoTransactionEntity> findById(Long transactionId) {
        return cryptoTransactionRepository.findById(transactionId);
    }

    @Override
    public CryptoTransactionEntity start(TransactionRequest request, CryptoTransactionType type) {
        return cryptoTransactionRepository.save(CryptoTransactionEntity.builder()
                .userId(request.getUserId())
                .walletType(request.getWalletType())
                .type(type)
                .status(PROCESSING)
                .amount(request.getCryptoAmount())
                .currency(request.getCryptoCurrency())
                .fiatAmount(request.getFiatAmount())
                .fiatCurrency(request.getFiatCurrency())
                .created(OffsetDateTime.now())
                .build());
    }

    @Override
    public void complete(Long transactionId) {
        findById(transactionId).ifPresent(tx -> {
            tx.setStatus(SUCCESS);
            cryptoTransactionRepository.save(tx);
        });
    }

}
