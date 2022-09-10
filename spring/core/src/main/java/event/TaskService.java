package event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Task create(String name) {
        var task = new Task();
        task.setName(name);
        task.setCreated(LocalDateTime.now());

        log.info("Publishing task created event: {}", task);
        eventPublisher.publishEvent(new TaskEvent(task, TaskEvent.Status.CREATED));

        try {
            return taskRepository.save(task);
        } finally {
            log.info("Event published. Saving task: {}", task);
        }
    }

}
