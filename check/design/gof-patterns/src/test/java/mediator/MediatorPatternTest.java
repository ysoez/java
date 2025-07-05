package mediator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediatorPatternTest {

    @Test
    void articleSelectionTest() {
        var dialog = new ArticleDialogBox();
        dialog.articleListBox.setSelection("Article 1");
        assertEquals("Article 1", dialog.titleTextBox.getContent());
        assertTrue(dialog.saveButton.isEnabled());
    }

    @Test
    void emptyTextBoxShouldDisableButtonTest() {
        var dialog = new ArticleDialogBox();
        dialog.articleListBox.setSelection("Article 1");
        dialog.titleTextBox.setContent("");
        assertFalse(dialog.saveButton.isEnabled());
    }

    @Test
    void changeTextBoxAfterSelection() {
        var dialog = new ArticleDialogBox();
        dialog.articleListBox.setSelection("Article 1");
        dialog.titleTextBox.setContent("Article 2");
        assertTrue(dialog.saveButton.isEnabled());
    }

}