package vertx.quarkus.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Cacheable
public class User extends PanacheEntity {
    @Column(length = 40, unique = true)
    public String name;
}
