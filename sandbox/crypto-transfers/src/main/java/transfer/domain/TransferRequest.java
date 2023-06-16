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
public class TransferRequest {
    private TransferType type;
    private WalletType walletType;
    private String senderUserId;
    private String receiverUserId;
    private BigDecimal amount;
    private Currency fiatCurrency;
    private Currency cryptoCurrency;
}
