package guava.base;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

class GuavaStopwatch {

    public static void main(String[] args) throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

}