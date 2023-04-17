package command.framework;

public class Button {

    private String label;
    private Command command;

    void click() {
        command.execute();
    }

}
