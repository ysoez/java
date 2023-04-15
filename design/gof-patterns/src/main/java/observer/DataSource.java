package observer;

import lombok.Getter;

class DataSource extends Subject {
    @Getter
    private int value;

    void setValue(int value) {
        this.value = value;
        notifyObservers();
    }

}
