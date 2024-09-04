package persistence.logging.p6spy;

import persistence.AbstractConfigurator;
import persistence.util.DataSourceProxyType;
import persistence.util.model.Post;

import java.util.Properties;

class P6spyBatchLogging extends AbstractConfigurator {

    public static void main(String[] args) {
        new P6spyBatchLogging().doInJPA(entityManager -> {
            for (long i = 0; i < 3; i++) {
                var post = new Post();
                post.setId(i);
                post.setTitle(String.format("Post-%d", i));
                entityManager.persist(post);
            }
        });
    }

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{Post.class};
    }

    @Override
    protected void additionalProperties(Properties properties) {
        properties.put("hibernate.jdbc.batch_size", "5");
    }

    @Override
    protected DataSourceProxyType dataSourceProxyType() {
        return DataSourceProxyType.P6SPY;
    }

}
