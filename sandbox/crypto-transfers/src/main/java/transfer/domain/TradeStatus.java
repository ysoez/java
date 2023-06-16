package transfer.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TradeStatus {

    PENDING(false),
    COMPLETED(true);

    private final boolean isFinal;

}
