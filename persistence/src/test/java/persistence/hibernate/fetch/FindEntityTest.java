package persistence.hibernate.fetch;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import persistence.AbstractPostgreSQLTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class FindEntityTest extends AbstractPostgreSQLTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
                Post.class,
                PostComment.class,
        };
    }

    @Override
    public void afterInit() {
        doInJPA(entityManager -> {
            Post post = new Post();
            post.setTitle(String.format("post-%d", 1));
            entityManager.persist(post);
        });
    }

    @Test
    void testFind() {
        doInJPA(entityManager -> {
            Post post = entityManager.find(Post.class, 1L);
            assertNotNull(post);
        });
    }

    @Test
    void testFindWithQuery() {
        doInJPA(entityManager -> {
            Post post = entityManager.createQuery("select p from Post p where p.id = :id", Post.class)
                    .setParameter("id", 1L)
                    .getSingleResult();
            assertNotNull(post);
        });
    }

    @Test
    void testGetReference() {
        doInJPA(entityManager -> {
            Post post = entityManager.getReference(Post.class, 1L);
            assertNotNull(post.getTitle());
        });
    }

    @Test
    public void testLoad() {
        doInJPA(entityManager -> {
            Session session = entityManager.unwrap(Session.class);
            var post = new Post();
            session.load(post, 1L);
            assertNotNull(post.getTitle());
        });
    }

    @Test
    public void testGetReferenceAndPersist() {
        doInJPA(entityManager -> {
            Post post = entityManager.getReference(Post.class, 1L);
            var comment = new PostComment("nice post");
            comment.setPost(post);
            entityManager.persist(comment);
        });
    }

    @Test
    void testTransientAndPersist() {
        doInJPA(entityManager -> {
            var post = new Post();
            post.setId(1L);
            var comment = new PostComment("nice post");
            comment.setPost(post);
            entityManager.persist(comment);
            assertNull(comment.getPost().getTitle());
        });
    }

    @Entity(name = "Post")
    @Table(name = "post")
    @Getter
    @Setter
    public static class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String title;

    }

    @Entity(name = "PostComment")
    @Table(name = "post_comment")
    @Getter
    @Setter
    public static class PostComment {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @ManyToOne
        private Post post;

        private String review;

        public PostComment() {
        }

        public PostComment(String review) {
            this.review = review;
        }
    }
}
