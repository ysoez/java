package bridge;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RemoteControl {

    protected final Device device;

    void turnOn() {
        device.turnOn();
    }

    void turnOff() {
        device.turnOff();
    }

}
