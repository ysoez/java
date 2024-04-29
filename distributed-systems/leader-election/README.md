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
./gradlew distributed-systems:leader-election:build
```

### Run Application

```
java -jar distributed-systems/leader-election/build/libs/leader-election-app-1.0.jar
```

### How To Test

* run 4 instances
* expect 4 nodes: 1 leader & 3 workers
