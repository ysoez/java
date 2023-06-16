package transfer.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import transfer.domain.Currency;
import transfer.domain.TransferStatus;
import transfer.domain.TransferType;
import transfer.domain.WalletType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = STRING)
    @NotNull
    private WalletType walletType;

    @Enumerated(value = STRING)
    @NotNull
    private TransferType type;

    @Enumerated(value = STRING)
    @NotNull
    private TransferStatus status;

    private Long senderFiatTxId;

    private Long senderBuyTxId;

    private Long senderWithdrawTxId;

    private Long receiverDepositTxId;

    @Positive
    private BigDecimal amount;

    private Currency cryptoCurrency;

    private Currency fiatCurrency;

    private OffsetDateTime created;

    private OffsetDateTime updated;

}
