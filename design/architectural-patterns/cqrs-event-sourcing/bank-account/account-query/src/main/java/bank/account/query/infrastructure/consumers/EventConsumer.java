package bank.account.query.infrastructure.consumers;

import bank.domain.event.AccountClosedEvent;
import bank.domain.event.AccountOpenedEvent;
import bank.domain.event.FundsDepositedEvent;
import bank.domain.event.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
