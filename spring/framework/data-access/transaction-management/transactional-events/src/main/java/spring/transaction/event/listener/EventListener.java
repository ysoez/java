package spring.transaction.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import spring.transaction.event.model.CustomerCreatedEvent;
import spring.transaction.event.service.TokenGenerator;

@Slf4j
@Component
@Profile("!async")
@RequiredArgsConstructor
public class EventListener {

    private final TokenGenerator tokenGenerator;

    @TransactionalEventListener
    public void processCustomerCreatedEvent(CustomerCreatedEvent event) {
        log.info("Received event: {}", event);
        tokenGenerator.generateToken(event.customer());
    }
}