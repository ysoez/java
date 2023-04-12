package memento;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EditorState {
    @Getter
    private final String content;
}
