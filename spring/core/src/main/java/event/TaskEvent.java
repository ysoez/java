package event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class TaskEvent {
    private Task task;
    private Status status;

    enum Status {
        CREATED
    }
}
