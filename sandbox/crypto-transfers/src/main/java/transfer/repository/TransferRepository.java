package transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transfer.domain.entity.TransferEntity;

import java.util.Optional;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

    Optional<TransferEntity> findBySenderBuyTxId(Long senderBuyTxId);

}
