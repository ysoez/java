package persistence.hibernate.fetching;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import persistence.AbstractPostgreSQLIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class FindEntityTest extends AbstractPostgreSQLIntegrationTest {

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
            post.setTitle(String.format("Post nr. %d", 1));
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
    void testGet() {
        doInJPA(entityManager -> {
            Session session = entityManager.unwrap(Session.class);
            Post post = session.get(Post.class, 1L);
            assertNotNull(post);
        });
    }

    @Test
    void testFindWithQuery() {
        doInJPA(entityManager -> {
            Long postId = 1L;
            Post post = entityManager.createQuery("select p from Post p where p.id = :id", Post.class)
                    .setParameter("id", postId)
                    .getSingleResult();
            assertNotNull(post);
        });
    }

    @Test
    void testGetReference() {
        doInJPA(entityManager -> {
            Post post = entityManager.getReference(Post.class, 1L);
            log.info("Loaded post entity");
            log.info("The post title is '{}'", post.getTitle());
        });
    }

    @Test
    void testByIdGetReference() {
        doInJPA(entityManager -> {
            Session session = entityManager.unwrap(Session.class);
            Post post = session.byId(Post.class).getReference(1L);
            log.info("Loaded post entity");
            log.info("The post title is '{}'", post.getTitle());
        });
    }

    @Test
    void testLoad() {
        doInJPA(entityManager -> {
            Session session = entityManager.unwrap(Session.class);
            Post post = session.load(Post.class, 1L);
            log.info("Loaded post entity");
            log.info("The post title is '{}'", post.getTitle());
        });
    }

    @Test
    void testGetReferenceAndPersist() {
        doInJPA(entityManager -> {
            log.info("Persisting a post comment");
            Post post = entityManager.getReference(Post.class, 1L);
            var postComment = new PostComment("Excellent reading!");
            postComment.setPost(post);
            entityManager.persist(postComment);
        });
    }

    @Test
    void testTransientAndPersist() {
        doInJPA(entityManager -> {
            log.info("Persisting a post comment");
            Post post = new Post();
            post.setId(1L);
            var postComment = new PostComment("Excellent reading!");
            postComment.setPost(post);
            entityManager.persist(postComment);
            assertNull(postComment.getPost().getTitle());
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
