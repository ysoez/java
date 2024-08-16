# HTTP Client

### Build 

``` 
./gradlew distributed-systems:network:http-client:build
```

### Run Servers

``` 
java -jar distributed-systems/network/http-server/build/libs/http-server-1.0.jar 8081
java -jar distributed-systems/network/http-server/build/libs/http-server-1.0.jar 8082
```

### Run Client

``` 
java -jar distributed-systems/network/http-client/build/libs/http-client-1.0.jar
```
