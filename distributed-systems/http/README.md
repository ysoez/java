# HTTP

### Build Server

``` 
./gradlew distributed-systems:http:http-server:build
```

### Build Client

``` 
./gradlew distributed-systems:http:http-client:build
```

### Run Server

``` 
java -jar distributed-systems/http/http-server/build/libs/http-server-1.0.jar 8081
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
