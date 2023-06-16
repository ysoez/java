package transfer.service;

import org.springframework.stereotype.Service;
import transfer.domain.TransactionRequest;
import transfer.domain.TransferRequest;
import transfer.domain.TransferType;
import transfer.domain.entity.TransferEntity;
import transfer.domain.exception.InsufficientFundsException;
import transfer.domain.exception.TransferException;
import transfer.repository.TransferRepository;
import transfer.service.balance.CryptoWalletService;
import transfer.service.transaction.CryptoTransactionService;

import static transfer.domain.CryptoTransactionType.TRANSFER_IN;
import static transfer.domain.CryptoTransactionType.TRANSFER_OUT;
import static transfer.domain.exception.TransferException.ErrorReason.INVALID_TRANSFER_AMOUNT;
import static transfer.domain.exception.TransferException.ErrorReason.SENDER_INSUFFICIENT_FUNDS;
import static transfer.domain.exception.TransferException.ErrorReason.TRANSACTION_NOT_FOUND;

@Service
class InstantCryptoTransferService extends AbstractTransferService {

    final CryptoTransactionService cryptoInvestTransactionService;
    final CryptoWalletService investBalanceService;

    InstantCryptoTransferService(CryptoWalletService investBalanceService,
                                 TransferRepository transferRepository,
                                 CryptoTransactionService cryptoTransactionService) {
        super(transferRepository);
        this.investBalanceService = investBalanceService;
        this.cryptoInvestTransactionService = cryptoTransactionService;
    }

    @Override
    Long createSenderWithdrawTransaction(TransferRequest transferRequest) {
        var txRequest = TransactionRequest.builder()
                .userId(transferRequest.getSenderUserId())
                .walletType(transferRequest.getWalletType())
                .cryptoAmount(transferRequest.getAmount())
                .cryptoCurrency(transferRequest.getCryptoCurrency())
                .build();
        return cryptoInvestTransactionService.start(txRequest, TRANSFER_OUT).getId();
    }

    @Override
    void withdrawSenderBalance(TransferEntity transfer) {
        try {
            var withdrawTx = cryptoInvestTransactionService.findById(transfer.getSenderWithdrawTxId())
                    .orElseThrow(() -> new TransferException(TRANSACTION_NOT_FOUND, ""));
            investBalanceService.withdraw(withdrawTx);
        } catch (InsufficientFundsException e) {
            throw new TransferException(SENDER_INSUFFICIENT_FUNDS, "");
        } catch (IllegalArgumentException e) {
            throw new TransferException(INVALID_TRANSFER_AMOUNT, e.getMessage());
        }
    }

    @Override
    Long createReceiverDepositTransaction(TransferRequest transferRequest) {
        var txRequest = TransactionRequest.builder()
                .userId(transferRequest.getReceiverUserId())
                .walletType(transferRequest.getWalletType())
                .cryptoAmount(transferRequest.getAmount())
                .cryptoCurrency(transferRequest.getCryptoCurrency())
                .build();
        return cryptoInvestTransactionService.start(txRequest, TRANSFER_IN).getId();
    }

    @Override
    void depositReceiverBalance(TransferEntity transfer) {
        var depositTx = cryptoInvestTransactionService.findById(transfer.getReceiverDepositTxId())
                .orElseThrow(() -> new TransferException(TRANSACTION_NOT_FOUND, ""));
        investBalanceService.deposit(depositTx);
    }

    @Override
    public TransferType transferType() {
        return TransferType.CRYPTO_TO_CRYPTO;
    }

    @Override
    public boolean isInstant() {
        return true;
    }

}
