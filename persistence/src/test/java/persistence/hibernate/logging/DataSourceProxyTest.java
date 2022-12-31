package persistence.hibernate.logging;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import persistence.AbstractHSQLDbIntegrationTest;
import persistence.util.DataSourceProxyType;

import java.util.Properties;

class DataSourceProxyTest extends AbstractHSQLDbIntegrationTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Post.class
        };
    }

    @Override
    protected void additionalProperties(Properties properties) {
        properties.put("hibernate.jdbc.batch_size", "5");
    }

    @Override
    protected DataSourceProxyType dataSourceProxyType() {
        return DataSourceProxyType.DATA_SOURCE_PROXY;
    }

    @Test
    void test() {
        doInJPA(entityManager -> {
            Post post = new Post();
            post.setId(1L);
            post.setTitle("Post it!");
            entityManager.persist(post);
        });
    }

    @Test
    void testBatch() {
        doInJPA(entityManager -> {
            for (long i = 0; i < 3; i++) {
                Post post = new Post();
                post.setId(i);
                post.setTitle(String.format("Post no. %d", i));
                entityManager.persist(post);
            }
        });
    }

    @Entity(name = "Post")
    @Table(name = "post")
    @Getter
    @Setter
    public static class Post {
        @Id
        private Long id;
        private String title;
        @Version
        private short version;
    }
}
