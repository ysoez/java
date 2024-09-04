package persistence.logging.dsproxy;

import persistence.AbstractConfigurator;
import persistence.util.model.Post;

class DataSourceProxyLogging extends AbstractConfigurator {

    public static void main(String[] args) {
        new DataSourceProxyLogging().doInJPA(entityManager -> {
            var post = new Post();
            post.setId(1L);
            post.setTitle("Post-1");
            entityManager.persist(post);
        });
    }

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{Post.class};
    }

}
