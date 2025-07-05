package bank.es.event;

public interface EventProducer {

    void produce(String topic, AbstractEvent event);

}
