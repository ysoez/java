package state;

enum Tool {
    SELECTION {
        @Override
        void mouseDown() {
            System.out.println("Selection icon");
        }
        @Override
        void mouseUp() {
            System.out.println("Draw dashed rectangle");
        }
    },
    BRUSH {
        @Override
        void mouseDown() {
            System.out.println("Brush icon");
        }
        @Override
        void mouseUp() {
            System.out.println("Draw a line");
        }
    },
    ERASER {
        @Override
        void mouseDown() {
            System.out.println("Eraser icon");
        }
        @Override
        void mouseUp() {
            System.out.println("Erase something");
        }
    };

    abstract void mouseDown();

    abstract void mouseUp();

}
