# Service Registry & Discovery

### Run Zookeeper

```
brew services start zookeeper
```

### Build Frontend Server

```
./gradlew sandbox:distributed-search:frontend-server:build
```

### Run Frontend Server

```
java -jar sandbox/distributed-search/frontend-server/build/libs/frontend-server-1.0.jar
```

### Build Search Server

```
./gradlew sandbox:distributed-search:search-server:build
```

### Run Leader

```
java -jar sandbox/distributed-search/search-server/build/libs/search-server-1.0.jar
```

### Run Workers

```
java -jar sandbox/distributed-search/search-server/build/libs/search-server-1.0.jar
```
