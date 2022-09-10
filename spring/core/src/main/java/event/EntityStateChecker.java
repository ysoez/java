package event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class EntityStateChecker {

    private final EntityManager entityManager;

    public boolean isTransient(Task task) {
        return task.getId() == null;
    }

    public boolean isManaged(Task task) {
        return entityManager.contains(task);
    }

    public boolean isDetached(Task task) {
        return !isTransient(task)
                && !isManaged(task)
                && exists(task);
    }

    private boolean exists(Task task) {
        return entityManager.find(Task.class, task.getId()) != null;
    }

}
