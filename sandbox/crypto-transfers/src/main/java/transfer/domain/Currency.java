package transfer.domain;

import java.util.Set;

public enum Currency {
    USD, UAH,
    BTC, ETH;

    public static final Set<Currency> CRYPTO = Set.of(BTC, ETH);
    public static final Set<Currency> FIAT = Set.of(USD, UAH);

    public static boolean isFiat(Currency currency) {
        return FIAT.contains(currency);
    }

    public static boolean isCrypto(Currency currency) {
        return CRYPTO.contains(currency);
    }

}
