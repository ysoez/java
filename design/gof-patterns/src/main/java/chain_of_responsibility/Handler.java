package chain_of_responsibility;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class Handler {

    private final Handler next;

    void handle(HttpRequest request) {
        if (doHandle(request))
            return;
        if (next != null)
            next.handle(request);
    }

    abstract boolean doHandle(HttpRequest request);

}
