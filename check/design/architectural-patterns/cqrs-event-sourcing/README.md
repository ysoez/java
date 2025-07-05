# CQRS & Event Sourcing

### Run Local Infrastructure 

```
cd ./design/architectural-patterns/cqrs-event-sourcing/ && docker compose up
```

### Mongo Express

* username: admin
* password: pass

```
http://localhost:8081
```

### Pg Admin

* username: admin@admin.com
* password: admin

```
http://localhost:5050
```

### Open Account

```
curl -X POST http://localhost:8000/api/v1/account/open \
-H "Content-Type: application/json" \
-d '{
  "accountHolder": "test",
  "accountType": "SAVINGS",
  "openingBalance": 1000
}'
```

### Deposit Funds

```
curl -X PUT http://localhost:8000/api/v1/account/$aggregateId/deposit \
-H "Content-Type: application/json" \
-d '{
  "amount": 50.00
}'
```

// todo: fix query app