package transfer.domain.entity;

import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import transfer.domain.Currency;
import transfer.domain.WalletType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static jakarta.persistence.EnumType.STRING;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private BigDecimal amount;

    @Enumerated(STRING)
    private Currency currency;

    @Enumerated(STRING)
    private WalletType walletType;

    @CreationTimestamp
    private OffsetDateTime created;

    @UpdateTimestamp
    private OffsetDateTime updated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction other))
            return false;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
