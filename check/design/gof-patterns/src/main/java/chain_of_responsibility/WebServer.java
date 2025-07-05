package chain_of_responsibility;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class WebServer {

    private final Handler handler;

    void handle(HttpRequest request) {
        handler.handle(request);
    }

}
