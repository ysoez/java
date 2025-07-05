package bank.es;

import bank.es.event.AbstractEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Getter
public abstract class AggregateRoot {

    protected String id;
    private int version = -1;

    private final List<AbstractEvent> changes = new ArrayList<>();

    public List<AbstractEvent> getUncommittedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(AbstractEvent event, Boolean isNewEvent) {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            log.warn("The apply({}) method was not found in the aggregate {}", event.getClass().getName(), getClass());
        } catch (Exception e) {
            log.error("Cannot apply event to the aggregate", e);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    public void raiseEvent(AbstractEvent event) {
        applyChange(event, true);
    }

    public void replayEvents(Collection<AbstractEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        events.forEach(event -> applyChange(event, false));
        var latestVersion = events.stream().map(AbstractEvent::getVersion).max(Comparator.naturalOrder());
        this.version = latestVersion.get();
    }
}
