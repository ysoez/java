package persistence.assertion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import persistence.AbstractConfigurator;
import persistence.util.model.Post;
import persistence.util.validator.SQLStatementCountValidator;

import java.util.List;

import static persistence.util.validator.SQLStatementCountValidator.assertSelectCount;

@Slf4j
class JoinFetchQueryValidator extends AbstractConfigurator {

    public static void main(String[] args) {
        new JoinFetchQueryValidator().doInJPA(entityManager -> {
            log.info("Join fetch to prevent N+1");
            SQLStatementCountValidator.reset();

            List<PostComment> postComments = entityManager.createQuery("""
                            select pc 
                            from PostComment pc 
                            join fetch pc.post
                            """, PostComment.class)
                    .getResultList();

            for (PostComment postComment : postComments) {
                if (postComment.post() == null) {
                    throw new AssertionError();
                }
            }
            assertSelectCount(1);
        });

    }

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
            Post post1 = new Post();
            post1.setId(1L);
            post1.setTitle("Post one");
            entityManager.persist(post1);
            entityManager.persist(new PostComment().id(1L).review("Good").post(post1));

            Post post2 = new Post();
            post2.setId(2L);
            post2.setTitle("Post two");
            entityManager.persist(post2);
            entityManager.persist(new PostComment().id(2L).review("Excellent").post(post2));
        });
    }

    @Entity(name = "PostComment")
    @Table(name = "post_comment")
    @Getter
    @Setter
    @Accessors(fluent = true)
    public static class PostComment {
        @Id
        private Long id;
        @ManyToOne(fetch = FetchType.LAZY)
        private Post post;
        private String review;
    }

}
