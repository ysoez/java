package transfer.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CryptoTransactionStatus {

    PROCESSING(false),
    SUCCESS(true);

    private final boolean isFinal;

}
