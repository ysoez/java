# HTTP Server

### Build 

``` 
./gradlew distributed-systems:network:http-server:build
```

### Run

``` 
java -jar distributed-systems/network/http-server/build/libs/http-server-1.0.jar 8081
```

### Test

``` 
curl -v localhost:8081/test
```

### Status

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
