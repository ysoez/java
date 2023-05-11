package kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

@Testcontainers
public abstract class KafkaTest {

    public static final String TEST_TOPIC = "test_topic";
    @Container
    protected static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.0.0"));
    private static AdminClient ADMIN;

    @BeforeAll
    static void beforeAll() {
        ADMIN = AdminClient.create(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA.getBootstrapServers()));
    }

    public static void createTopic(String topic, int partitions) {
        CreateTopicsResult result = ADMIN.createTopics(singletonList(new NewTopic(topic, partitions, (short) 1)));
        try {
            result.all().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}

