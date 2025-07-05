package bridge;

public class SamsungTV implements Device {
    @Override
    public void turnOn() {
        System.out.println("Turn on: " + getClass());
    }

    @Override
    public void turnOff() {
        System.out.println("Turn off: " + getClass());
    }

    @Override
    public void setChannel(int number) {
        System.out.println("setChannel " + number + ": " + getClass());
    }
}
