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
./gradlew distributed-systems:distributed-algorithms:cluster-leader-election:build
```

### Run Application

```
java -jar distributed-systems/distributed-algorithms/cluster-leader-election/build/libs/leader-election-app-1.0.jar
```
