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

    @Test
    void undoDocumentTest() {
        var document = new Document();
        var history = new DocumentHistory();

        document.content = "hello";
        document.fontName = "Default";
        document.fontSize = "24px";
        history.push(document.createState());

        document.fontSize = "12px";
        history.push(document.createState());

        document.fontName = "Non-Default";
        history.push(document.createState());

        document.fontName = "Unknown";
        assertEquals("hello", document.content);
        assertEquals("Unknown", document.fontName);
        assertEquals("12px", document.fontSize);

        document.restore(history.pop());
        assertEquals("hello", document.content);
        assertEquals("Non-Default", document.fontName);
        assertEquals("12px", document.fontSize);

        document.restore(history.pop());
        assertEquals("hello", document.content);
        assertEquals("Default", document.fontName);
        assertEquals("12px", document.fontSize);

        document.restore(history.pop());
        assertEquals("hello", document.content);
        assertEquals("Default", document.fontName);
        assertEquals("24px", document.fontSize);
    }

}