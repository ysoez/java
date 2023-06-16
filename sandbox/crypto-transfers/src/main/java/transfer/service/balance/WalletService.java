package transfer.service.balance;

import transfer.domain.Currency;
import transfer.domain.WalletType;
import transfer.domain.entity.WalletEntity;
import transfer.domain.entity.Transaction;

import java.util.Optional;

public interface WalletService {

    WalletEntity findBy(String userId, WalletType walletType, Currency currency);

    void deposit(Transaction transaction);

    void withdraw(Transaction transaction);

}
