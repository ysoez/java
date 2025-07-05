package state.abuse;

import lombok.RequiredArgsConstructor;

class SmellStopwatch {

    private State currentState;

    void click() {
        currentState.click();

    }

    interface State {
        void click();
    }

    @RequiredArgsConstructor
    static class StopState implements State {
        private final SmellStopwatch stopwatch;
        @Override
        public void click() {
            stopwatch.currentState = new RunningState(stopwatch);
        }
    }

    @RequiredArgsConstructor
    static class RunningState implements State {
        private final SmellStopwatch stopwatch;
        @Override
        public void click() {
            stopwatch.currentState = new StopState(stopwatch);
        }
    }

}
