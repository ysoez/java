package memento;

import lombok.Getter;
import lombok.Setter;

class Editor {

    @Getter
    @Setter
    private String content;

    public EditorState createState() {
        return new EditorState(content);
    }

    public void restore(EditorState state) {
        content = state.getContent();
    }

}
