package command.undo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BoldCommand implements UndoableCommand {

    private final HtmlDocument document;
    private final History history;
    private String prevContent;

    @Override
    public void execute() {
        prevContent = document.content;
        document.makeBold();
        history.push(this);
    }

    @Override
    public void undo() {
        document.content = prevContent;
    }

}
