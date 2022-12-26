package persistence.fetching;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import persistence.AbstractPostgreSQLIntegrationTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class HibernateProxyTest extends AbstractPostgreSQLIntegrationTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
                Post.class,
                PostComment.class,
        };
    }

    @Test
    void entityProxyTest() {
        Post _post = doInJPA(entityManager -> {
            var post = new Post();
            post.setId(1L);
            post.setTitle("Hello World");
            entityManager.persist(post);
            return post;
        });

        doInJPA(entityManager -> {
            Post post = entityManager.getReference(Post.class, 1L);

            var comment = new PostComment();
            comment.setId(1L);
            comment.setPost(post);
            comment.setReview("Nice");
            entityManager.persist(comment);
        });

        doInJPA(entityManager -> {
            PostComment comment = entityManager.find(
                    PostComment.class,
                    1L
            );
            log.info("Loading the Post Proxy");
            assertEquals(
                    _post.getTitle(),
                    comment.getPost().getTitle()
            );
        });

        doInJPA(entityManager -> {
            Post post = entityManager.getReference(Post.class, 1L);
            log.info("Post entity class: {}", post.getClass().getName());
            assertFalse(_post.equals(post));
            assertTrue(_post.equals(Hibernate.unproxy(post)));
        });
    }

    @Entity(name = "Post")
    @Table(name = "post")
    @Getter
    @Setter
    static class Post {

        @Id
        private Long id;

        private String title;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Post)) return false;
            // intentionally uses field to prove how Proxy works
            return id != null && id.equals(((Post) o).id);
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }
    }

    @Entity(name = "PostComment")
    @Table(name = "post_comment")
    @Getter
    @Setter
    static class PostComment {
        @Id
        private Long id;
        @ManyToOne(fetch = FetchType.LAZY)
        private Post post;
        private String review;
    }
}
