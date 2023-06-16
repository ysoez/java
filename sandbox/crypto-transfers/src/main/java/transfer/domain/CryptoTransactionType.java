package transfer.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CryptoTransactionType {
    TRANSFER_IN, TRANSFER_OUT,
    BUY_CRYPTO, SELL_CRYPTO
}
