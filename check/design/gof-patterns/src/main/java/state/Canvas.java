package state;

class Canvas {

    private Tool currentTool;

    void mouseDown() {
        currentTool.mouseDown();
    }

    void mouseUp() {
        currentTool.mouseUp();
    }

}
