package mediator.fx;

import lombok.Getter;

public class Button extends UIControl {

    @Getter
    private boolean isEnabled;

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
        notifyEventHandlers();
    }

}
