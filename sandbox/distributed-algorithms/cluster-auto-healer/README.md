# Auto Healer

### Build

```
./gradlew sandbox:distributed-algorithms:cluster-auto-healer:worker:build
./gradlew sandbox:distributed-algorithms:cluster-auto-healer:watchdog:build
```

### Run

```
java -jar sandbox/distributed-algorithms/cluster-auto-healer/watchdog/build/libs/watchdog-server-1.0.jar 3 "sandbox/distributed-algorithms/cluster-auto-healer/worker/build/libs/worker-server-1.0.jar" 
```
