package observer;

import java.util.ArrayList;
import java.util.List;

// Observable
abstract class Subject {

    private final List<Observer> observers = new ArrayList<>();

    void add(Observer observer) {
        observers.add(observer);
    }

    void remove(Observer observer) {
        observers.remove(observer);
    }

    void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
