package bridge;

import org.junit.jupiter.api.Test;

class BridgePatternTest {

    @Test
    void test() {
        var remoteControl = new RemoteControl(new SonyTV());
        remoteControl.turnOn();
        remoteControl.turnOff();

        var advancedRemoteControl = new AdvancedRemoteControl(new SamsungTV());
        advancedRemoteControl.turnOn();
        advancedRemoteControl.setChannel(1);
        advancedRemoteControl.turnOff();
    }

}