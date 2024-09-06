package guava.base;

import static com.google.common.base.Preconditions.*;

class GuavaPreconditions {

    public static void main(String[] args) {
        //
        // ~ validate assumptions
        // ~ fail fast if not valid
        //
        new Car(new Engine()).drive(10);
    }

    private static class Car {

        private final Engine engine;

        Car(Engine engine) {
            this.engine = checkNotNull(engine);
        }

        void drive(double speed) {
            engine.start();
            checkState(engine.isRunning, "engine must be running");
            checkArgument(speed > 0, "speed (%s) must be positive", speed);
        }

    }

    private static class Engine {

        private boolean isRunning;

        void start() {
            isRunning = true;
        }

        void stop() {
            isRunning = false;
        }

    }

}