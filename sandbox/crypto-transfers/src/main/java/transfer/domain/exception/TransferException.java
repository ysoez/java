package transfer.domain.exception;

import lombok.Getter;

public class TransferException extends RuntimeException {

    @Getter
    private final ErrorReason reason;

    public TransferException(ErrorReason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public enum ErrorReason {
        UNKNOWN_TRANSFER_TYPE,
        SENDER_INSUFFICIENT_FUNDS,
        TRANSACTION_NOT_FOUND,
        INVALID_TRANSFER_AMOUNT,
        BUY_CRYPTO_FAILED
    }

}
