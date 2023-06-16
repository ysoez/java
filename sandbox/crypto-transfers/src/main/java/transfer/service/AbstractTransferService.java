package transfer.service;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;
import transfer.domain.TransferRequest;
import transfer.domain.TransferStatus;
import transfer.domain.entity.TransferEntity;
import transfer.repository.TransferRepository;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
abstract class AbstractTransferService implements TransferService {

    final TransferRepository transferRepo;

    @Override
    @Transactional
    public TransferEntity startTransfer(TransferRequest request) {
        var transfer = TransferEntity.builder()
                .type(transferType())
                .walletType(request.getWalletType())
                .status(TransferStatus.PROCESSING)
                .senderWithdrawTxId(createSenderWithdrawTransaction(request))
                .receiverDepositTxId(createReceiverDepositTransaction(request))
                .amount(request.getAmount())
                .cryptoCurrency(request.getCryptoCurrency())
                .fiatCurrency(request.getFiatCurrency())
                .created(OffsetDateTime.now())
                .build();
        onTransferStarted(request, transfer);
        return transferRepo.save(transfer);
    }

    protected void onTransferStarted(TransferRequest transferRequest, TransferEntity transfer) {}

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    public void completeTransfer(TransferEntity transfer) {
        withdrawSenderBalance(transfer);
        depositReceiverBalance(transfer);
        transfer.setStatus(TransferStatus.SUCCESS);
        transferRepo.save(transfer);
    }

    abstract Long createSenderWithdrawTransaction(TransferRequest transferRequest);

    abstract void withdrawSenderBalance(TransferEntity transfer);

    abstract Long createReceiverDepositTransaction(TransferRequest transferRequest);

    abstract void depositReceiverBalance(TransferEntity transfer);

}
