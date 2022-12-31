package persistence.hibernate.logging;

import jakarta.persistence.*;
import lombok.*;
import org.junit.jupiter.api.Test;
import persistence.AbstractHSQLDbIntegrationTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class SQLStatementCountValidatorTest extends AbstractHSQLDbIntegrationTest {

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            Post.class,
            PostComment.class,
        };
    }

    @Override
    public void afterInit() {
        doInJPA(entityManager -> {
            var post1 = new Post();
            post1.setId(1L);
            post1.setTitle("Post one");
            entityManager.persist(post1);
            entityManager.persist(PostComment.builder()
                    .id(1L)
                    .review("Good")
                    .post(post1)
                    .build()
            );

            var post2 = new Post();
            post2.setId(2L);
            post2.setTitle("Post two");
            entityManager.persist(post2);
            entityManager.persist(PostComment.builder()
                    .id(2L)
                    .review("Excellent")
                    .post(post2)
                    .build()
            );
        });
    }

    @Test
    void testNPlusOne() {
        doInJPA(entityManager -> {
            SQLStatementCountValidator.reset();

            List<PostComment> comments = entityManager.createQuery("""
                select pc
                from PostComment pc
                """, PostComment.class)
            .getResultList();

            assertEquals(2, comments.size());
            SQLStatementCountValidator.assertSelectCount(1);
        });
    }

    @Test
    void testJoinFetch() {
        doInJPA(entityManager -> {
            SQLStatementCountValidator.reset();

            List<PostComment> postComments = entityManager.createQuery("""
                select pc 
                from PostComment pc 
                join fetch pc.post
                """, PostComment.class)
            .getResultList();

            for (PostComment postComment : postComments) {
                assertNotNull(postComment.getPost());
            }
            SQLStatementCountValidator.assertSelectCount(1);
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
    }

    @Entity(name = "PostComment")
    @Table(name = "post_comment")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class PostComment {
        @Id
        private Long id;
        @ManyToOne(fetch = FetchType.LAZY)
        private Post post;
        private String review;
    }
}
