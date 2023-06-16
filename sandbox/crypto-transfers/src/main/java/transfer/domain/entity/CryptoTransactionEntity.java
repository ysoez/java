package transfer.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import transfer.domain.CryptoTransactionStatus;
import transfer.domain.CryptoTransactionType;
import transfer.domain.Currency;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoTransactionEntity extends Transaction {

    @Enumerated(STRING)
    private CryptoTransactionType type;

    @Enumerated(STRING)
    private CryptoTransactionStatus status;

    private BigDecimal fiatAmount;

    private Currency fiatCurrency;

}
