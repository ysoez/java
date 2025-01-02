package bank.account.cmd;

import bank.es.event.AbstractEvent;
import bank.es.event.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, AbstractEvent event) {
        this.kafkaTemplate.send(topic, event);
    }

}
