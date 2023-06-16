package transfer.service.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.domain.Currency;
import transfer.domain.WalletType;
import transfer.domain.entity.Transaction;
import transfer.domain.entity.WalletEntity;
import transfer.domain.exception.InsufficientFundsException;
import transfer.repository.WalletRepository;
import transfer.service.transaction.CryptoTransactionService;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class CryptoWalletService implements WalletService {

    private final WalletRepository walletRepository;
    private final CryptoTransactionService cryptoTransactionService;

    @Override
    public WalletEntity findBy(String userId, WalletType walletType, Currency currency) {
        return walletRepository.findByUserIdAndWalletTypeAndCurrency(userId, walletType, currency)
                .orElse(create(userId, walletType, currency));
    }

    @Override
    public void deposit(Transaction transaction) {
        var wallet = findBy(transaction.getUserId(), transaction.getWalletType(), transaction.getCurrency());
        wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        walletRepository.save(wallet);
    }

    @Override
    public void withdraw(Transaction transaction) {
        String userId = transaction.getUserId();
        WalletType walletType = transaction.getWalletType();
        Currency currency = transaction.getCurrency();
        BigDecimal amount = transaction.getAmount();
        var balance = walletRepository.findByUserIdAndWalletTypeAndCurrency(userId, walletType, currency)
                .orElse(create(userId, walletType, currency));
        if (balance.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        balance.setBalance(balance.getBalance().subtract(amount));
        walletRepository.save(balance);
        cryptoTransactionService.complete(transaction.getId());
    }

    private WalletEntity create(String userId, WalletType walletType, Currency currency) {
        walletType.isCurrencySupportedOrThrow(currency);
        return walletRepository.save(WalletEntity.builder()
                .userId(userId)
                .walletType(walletType)
                .currency(currency)
                .build()
        );
    }

}
