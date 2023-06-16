package transfer.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import transfer.CryptoTransferAppTests;
import transfer.domain.Currency;
import transfer.domain.TransferRequest;
import transfer.domain.TransferResponse;
import transfer.domain.TransferType;
import transfer.domain.WalletType;
import transfer.domain.exception.TransferException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static transfer.domain.TransferType.CRYPTO_TO_CRYPTO;
import static transfer.domain.exception.TransferException.ErrorReason.SENDER_INSUFFICIENT_FUNDS;

class TransferManagerTest extends CryptoTransferAppTests {

    static final String SENDER_USER_ID = "sender-user";
    static final String RECEIVER_USER_ID = "receiver-user";

    @Autowired
    private TransferManager transferManager;

    @ParameterizedTest
    @EnumSource(TransferType.class)
    void senderHasInsufficientFunds(TransferType transferType) {
        var response = transferManager.transfer(TransferRequest.builder()
                .senderUserId(SENDER_USER_ID)
                .receiverUserId(RECEIVER_USER_ID)
                .type(transferType)
                .walletType(WalletType.SPOT)
                .amount(BigDecimal.TEN)
                .cryptoCurrency(Currency.BTC)
                .build()
        );

        assertEquals(SENDER_INSUFFICIENT_FUNDS, response.getReason());
    }

    @Nested
    class InstantCryptoTransfer {

        @ParameterizedTest
        @EnumSource(WalletType.class)
        void successTransfer(WalletType walletType) {
            transferManager.transfer(TransferRequest.builder()
                    .senderUserId(SENDER_USER_ID)
                    .receiverUserId(RECEIVER_USER_ID)
                    .type(CRYPTO_TO_CRYPTO)
                    .walletType(walletType)
                    .amount(BigDecimal.TEN)
                    .cryptoCurrency(Currency.BTC)
                    .build()
            );
        }
    }

    @Nested
    class FiatToCryptoTransfer {

    }

    @Nested
    class FiatToFiatTransfer {

    }

}
