package persistence.logging.p6spy;

import persistence.AbstractConfigurator;
import persistence.util.DataSourceProxyType;
import persistence.util.model.Post;

class P6spyLogging extends AbstractConfigurator {

    public static void main(String[] args) {
        new P6spyLogging().doInJPA(entityManager -> {
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

    @Override
    protected DataSourceProxyType dataSourceProxyType() {
        return DataSourceProxyType.P6SPY;
    }

}
