# How To Run

### Run & Setup Kafka

```

```

### Build & Run Account Manager Service

``` 
./gradlew distributed-systems:distributed-bank:bank-account:build && \ 
java -jar distributed-systems/distributed-bank/bank-account/build/libs/account-manager-service-1.0.jar
```

### Build & Run Bank API Service

```
./gradlew distributed-systems:distributed-bank:bank-api:build && \
java -jar distributed-systems/distributed-bank/bank-api/build/libs/api-service-1.0.jar
```

### Build & Run Bank Notification Service

```
./gradlew distributed-systems:distributed-bank:bank-notification:build && \
java -jar distributed-systems/distributed-bank/bank-notification/build/libs/notification-service-1.0.jar
```

### Build & Run Bank Reporting Service

```
./gradlew distributed-systems:distributed-bank:bank-reporting:build && \
java -jar distributed-systems/distributed-bank/bank-reporting/build/libs/reporting-service-1.0.jar
```
