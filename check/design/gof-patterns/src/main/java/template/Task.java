package template;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class Task {

    private final AuditTrail audit;

    public void execute() {
        audit.record();
        doExecute();
    }

    protected abstract void doExecute();

}
