# Service Registry & Discovery

### Run Zookeeper

```
brew services start zookeeper
```

### Build Executable Fat Jar

```
./gradlew distributed-systems:distributed-algorithms:cluster-service-registry:build
```

### Run Application

```
java -jar distributed-systems/distributed-algorithms/cluster-service-registry/build/libs/service-registry-app-1.0.jar
```
