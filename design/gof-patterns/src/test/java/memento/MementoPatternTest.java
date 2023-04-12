package memento;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MementoPatternTest {

    @Test
    void undoTest() {
        var editor = new Editor();
        var history = new EditorHistory();

        editor.setContent("a");
        history.push(editor.createState());

        editor.setContent("b");
        history.push(editor.createState());

        editor.setContent("c");

        editor.restore(history.pop());
        assertEquals("b", editor.getContent());

        editor.restore(history.pop());
        assertEquals("a", editor.getContent());
    }

}