package persistence;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
abstract class TestContainers {

    @Container
    protected static final PostgreSQLContainer POSTGRESQL_CONTAINER = new PostgreSQLContainer("postgres:11.1");

}
