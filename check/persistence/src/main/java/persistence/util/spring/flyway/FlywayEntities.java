package persistence.util.spring.flyway;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlywayEntities {

    @Entity(name = "Post")
    @Table(name = "post")
    @Getter
    @Setter
    public static class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
        @SequenceGenerator(name = "post_seq", allocationSize = 1)
        private Long id;

        private String title;

        public Post() {
        }

        public Post(Long id) {
            this.id = id;
        }

        public Post(String title) {
            this.title = title;
        }

        @OneToMany(cascade = CascadeType.ALL, mappedBy = "post",
                orphanRemoval = true)
        private List<PostComment> comments = new ArrayList<>();

        @OneToOne(cascade = CascadeType.ALL, mappedBy = "post",
                orphanRemoval = true, fetch = FetchType.LAZY)
        private PostDetails details;

        @ManyToMany
        @JoinTable(name = "post_tag",
                joinColumns = @JoinColumn(name = "post_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id")
        )
        private List<Tag> tags = new ArrayList<>();

        public void addComment(PostComment comment) {
            comments.add(comment);
            comment.setPost(this);
        }

        public void addDetails(PostDetails details) {
            this.details = details;
            details.setPost(this);
        }

        public void removeDetails() {
            this.details.setPost(null);
            this.details = null;
        }
    }

    @Entity(name = "PostDetails")
    @Table(name = "post_details")
    @Getter
    @Setter
    public static class PostDetails {

        @Id
        private Long id;

        @Column(name = "created_on")
        private Date createdOn;

        @Column(name = "created_by")
        private String createdBy;

        public PostDetails() {
            createdOn = new Date();
        }

        @OneToOne(fetch = FetchType.LAZY)
        @MapsId
        @JoinColumn(name = "id")
        private Post post;

    }

    @Entity(name = "PostComment")
    @Table(name = "post_comment")
    @Setter
    @Getter
    public static class PostComment {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_comment_seq")
        @SequenceGenerator(name = "post_comment_seq", allocationSize = 1)
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

    @Entity(name = "Tag")
    @Table(name = "tag")
    @Setter
    @Getter
    public static class Tag {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
        @SequenceGenerator(name = "tag_seq", allocationSize = 1)
        private Long id;

        private String name;
    }

    @Entity(name = "User")
    @Table(name = "users")
    @Setter
    @Getter
    public static class User {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
        @SequenceGenerator(name = "user_seq", allocationSize = 1)
        private Long id;

        private String name;
    }
}
