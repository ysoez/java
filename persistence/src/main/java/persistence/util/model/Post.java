package persistence.util.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Post")
@Table(name = "post")
@Getter
@Setter
public class Post {

    @Id
    private Long id;

    private String title;

    @Version
    private short version;

}