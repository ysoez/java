package command.undo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UndoableCommandTest {

    @Test
    void undoTest() {
        var history = new History();
        var document = new HtmlDocument();
        document.content = "Hello World";

        var boldCommand = new BoldCommand(document, history);
        boldCommand.execute();
        assertEquals("<b>Hello World</b>", document.content);

        var undoCommand = new UndoCommand(history);
        undoCommand.execute();
        assertEquals("Hello World", document.content);

    }

}