package bank.domain.event;

import bank.es.event.AbstractEvent;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccountClosedEvent extends AbstractEvent {
}
