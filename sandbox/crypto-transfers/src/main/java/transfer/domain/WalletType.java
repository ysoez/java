package transfer.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum WalletType {

    SPOT(Currency.CRYPTO),
    FUNDING(Currency.CRYPTO),
    FIAT(Currency.FIAT);

    private final Set<Currency> currencies;

    public void isCurrencySupportedOrThrow(Currency currency) {
        if (!currencies.contains(currency)) {
            throw new IllegalArgumentException(this + " does not support " + currency);
        }
    }

}
