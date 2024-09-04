package persistence.logging.dsproxy;

import persistence.AbstractConfigurator;
import persistence.util.model.Post;

import java.util.Properties;

class DataSourceProxyBatchLogging extends AbstractConfigurator {

    public static void main(String[] args) {
        new DataSourceProxyBatchLogging().doInJPA(entityManager -> {
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

}
