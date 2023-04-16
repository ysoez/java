package mediator.fx;

import lombok.Getter;

public class ListBox extends UIControl {

    @Getter
    private String selection;

    public void setSelection(String selection) {
        this.selection = selection;
        notifyEventHandlers();
    }

}
