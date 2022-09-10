package event;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
class Task {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private LocalDateTime created;
}
