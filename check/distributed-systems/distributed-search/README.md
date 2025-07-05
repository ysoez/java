# Service Registry & Discovery

### Run Zookeeper

```
brew services start zookeeper
```

### Build Frontend Server

```
./gradlew distributed-systems:distributed-search:frontend-server:build
```

### Run Frontend Server

```
java -jar distributed-systems/distributed-search/frontend-server/build/libs/frontend-server-app-1.0.jar
```

### Build Search Cluster

```
./gradlew distributed-systems:distributed-search:search-cluster:build
```

### Run Leader

```
java -jar distributed-systems/distributed-search/search-cluster/build/libs/search-cluster-app-1.0.jar
```

### Run Workers

```
java -jar distributed-systems/distributed-search/search-cluster/build/libs/search-cluster-app-1.0.jar
```
