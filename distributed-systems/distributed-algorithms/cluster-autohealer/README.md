# AutoHealer

### Run Zookeeper

```
brew services start zookeeper
```

### Build Executable Fat Jar

```
./gradlew distributed-systems:distributed-algorithms:cluster-autohealer:worker:build
./gradlew distributed-systems:distributed-algorithms:cluster-autohealer:watchdog:build
```

### Run Application

```
java -jar distributed-systems/distributed-algorithms/cluster-autohealer/watchdog/build/libs/watchdog-server-1.0.jar 3 "distributed-systems/distributed-algorithms/cluster-autohealer/worker/build/libs/worker-server-1.0.jar" 
```
