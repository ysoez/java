package mediator;

import mediator.fx.*;

class ArticleDialogBox {

    ListBox articleListBox = new ListBox();
    TextBox titleTextBox = new TextBox();
    Button saveButton = new Button();

    public ArticleDialogBox() {
        articleListBox.addEventHandler(this::onArticleSelected);
        titleTextBox.addEventHandler(this::onTitleChanged);
    }


    private void onArticleSelected() {
        titleTextBox.setContent(articleListBox.getSelection());
        saveButton.setEnabled(true);
    }

    private void onTitleChanged() {
        var content = titleTextBox.getContent();
        var isEmpty = content == null || content.isEmpty();
        saveButton.setEnabled(!isEmpty);
    }

}
