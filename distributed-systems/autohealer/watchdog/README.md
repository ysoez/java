# Leader Election

### Install Zookeeper

``` 
brew install kafka
```

### Run Zookeeper

```
brew services start zookeeper
```

### Build Executable Fat Jar

```
./gradlew distributed-systems:autohealer:worker:build
./gradlew distributed-systems:autohealer:watchdog:build
```

### Run Application

```
java -jar distributed-systems/autohealer/watchdog/build/libs/watchdog-server-1.0.jar 3 "distributed-systems/autohealer/worker/build/libs/worker-server-1.0.jar" 
```
