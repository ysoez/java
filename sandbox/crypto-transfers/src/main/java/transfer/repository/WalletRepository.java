package transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transfer.domain.Currency;
import transfer.domain.WalletType;
import transfer.domain.entity.WalletEntity;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    Optional<WalletEntity> findByUserIdAndWalletTypeAndCurrency(String userId, WalletType walletType, Currency currency);

}
