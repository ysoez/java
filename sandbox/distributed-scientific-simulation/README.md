# Scientific Simulation

### Build Server

``` 
./gradlew sandbox:distributed-scientific-simulation:server:build
```

### Run Server

``` 
java -jar sandbox/distributed-scientific-simulation/server/build/libs/server-1.0.jar 8081
java -jar sandbox/distributed-scientific-simulation/server/build/libs/server-1.0.jar 8082
```

### Test

``` 
curl -v localhost:8081/test
```

### Health Endpoint

```
curl -v localhost:8081/status
```

### Calculate Numbers

```
curl --request POST -v --data '50,100' localhost:8081/task
```

### Test Mode

```
curl --request POST -v --header "X-Test: true" --data '50,100' localhost:8081/task
```

### Debug Mode

```
curl --request POST -v --header "X-Debug: true" --data '50,100' localhost:8081/task
```

### Build Client

``` 
./gradlew sandbox:distributed-scientific-simulation:client:build
```

### Run Client

``` 
java -jar sandbox/distributed-scientific-simulation/client/build/libs/client-1.0.jar
```
