package mediator.fx;

import lombok.Getter;

public class TextBox extends UIControl {

    @Getter
    private String content;

    public void setContent(String content) {
        this.content = content;
        notifyEventHandlers();
    }

}
