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
./gradlew distributed-systems:leader-distributed:build
```

### Run Application

```
java -jar distributed-systems/leader-distributed/build/libs/leader-distributed-app-1.0.jar
```
