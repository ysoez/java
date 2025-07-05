# Spring Prometheus Grafana 

### Run Compose

``` 
docker compose up
```

### Prometheus UI

```
http://localhost:9090
```

### Grafana UI

```
http://localhost:3000/
```

### Grafana Connection for Prometheus

```
http://prometheus:9090
```

### Application Metrics

```
http://localhost:8080/actuator/prometheus
```

### Generate Traffic

```
sh traffic.sh
```
