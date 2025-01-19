# Standalone File Stream Source

### Run Connector Container

```shell
docker run --rm -it -v "$(pwd)":/connector --net=host landoop/fast-data-dev:cp3.3.0 bash
```

### Create Topic

```shell
kafka-topics --create --topic standalone-topic --partitions 3 --replication-factor 1 --zookeeper 127.0.0.1:2181
```

### Navigate Config Folder

```shell

```

### Run Standalone Kafka Connect Cluster

```shell
cd /connector/config && connect-standalone worker.properties connector.properties
```

### Modify File & Check Topic Content

```shell
localhost:3030
```
