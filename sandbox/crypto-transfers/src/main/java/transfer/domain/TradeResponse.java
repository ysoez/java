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
public class TradeResponse {
    private String transactionId;
    private BigDecimal amount;
    private Currency currency;
    private TradeStatus status;
}
