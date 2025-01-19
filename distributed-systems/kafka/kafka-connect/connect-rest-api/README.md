# Kafka Connect REST API

### Setup Container

```shell
docker run --rm -it --net=host landoop/fast-data-dev:cp3.3.0 bash
```

### Pretty JSON

```shell
apk update && apk add jq
```

### Worker Info

```shell
curl -s 127.0.0.1:8083/ | jq
```

### List Connectors available on a Worker

```shell
curl -s 127.0.0.1:8083/connector-plugins | jq
```
### Active Connectors

```shell
curl -s 127.0.0.1:8083/connectors | jq
```

### Connector Tasks & Config

```shell
curl -s 127.0.0.1:8083/connectors/source-twitter-distributed/tasks | jq
```

### Connector Status

```shell
curl -s 127.0.0.1:8083/connectors/file-stream-demo-distributed/status | jq
```

### Pause & Resume Connector 

```shell
curl -s -X PUT 127.0.0.1:8083/connectors/file-stream-demo-distributed/pause
curl -s -X PUT 127.0.0.1:8083/connectors/file-stream-demo-distributed/resume
```

### Connector Configuration

```shell
curl -s 127.0.0.1:8083/connectors/file-stream-demo-distributed | jq
```

### Delete Connector

```shell
curl -s -X DELETE 127.0.0.1:8083/connectors/file-stream-demo-distributed
```

### Create Connector

```shell
curl -s -X POST -H "Content-Type: application/json" --data '{"name": "file-stream-demo-distributed", "config":{"connector.class":"org.apache.kafka.connect.file.FileStreamSourceConnector","key.converter.schemas.enable":"true","file":"source.txt","tasks.max":"1","value.converter.schemas.enable":"true","name":"file-stream-demo-distributed","topic":"demo-2-distributed","value.converter":"org.apache.kafka.connect.json.JsonConverter","key.converter":"org.apache.kafka.connect.json.JsonConverter"}}' http://127.0.0.1:8083/connectors | jq
```

### Update Configuration

```shell
curl -s -X PUT -H "Content-Type: application/json" --data '{"connector.class":"org.apache.kafka.connect.file.FileStreamSourceConnector","key.converter.schemas.enable":"true","file":"source.txt","tasks.max":"2","value.converter.schemas.enable":"true","name":"file-stream-demo-distributed","topic":"demo-2-distributed","value.converter":"org.apache.kafka.connect.json.JsonConverter","key.converter":"org.apache.kafka.connect.json.JsonConverter"}' 127.0.0.1:8083/connectors/file-stream-demo-distributed/config | jq
```
