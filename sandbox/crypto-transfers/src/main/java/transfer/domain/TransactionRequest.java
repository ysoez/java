package transfer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String userId;
    private WalletType walletType;
    private BigDecimal cryptoAmount;
    private Currency cryptoCurrency;
    private BigDecimal fiatAmount;
    private Currency fiatCurrency;
}
