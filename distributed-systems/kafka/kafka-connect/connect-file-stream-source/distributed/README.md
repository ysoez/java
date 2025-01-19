# Distributed File Stream Connector

### Run Connector Container & Create Topic 

```shell
docker run --rm -it --net=host landoop/fast-data-dev:cp3.3.0 bash && \
kafka-topics --create --topic file-stream-distributed --partitions 3 --replication-factor 1 --zookeeper 127.0.0.1:2181 
```

### Create Connector & Paste Configuration

```shell
http://localhost:3030/
```

### Create File in Kafka Cluster

```shell
docker ps
docker exec -it <containerId> bash
touch source.txt
echo "hello 1" >> source.txt
echo "hello 2" >> source.txt
```

### Run Consumer Container

```shell
docker run --rm -it --net=host landoop/fast-data-dev:cp3.3.0 bash \
&& kafka-console-consumer --topic distributed-topic --from-beginning --bootstrap-server 127.0.0.1:9092 
```
