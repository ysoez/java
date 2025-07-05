package db.mongo;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import de.flapdoodle.embed.mongo.commands.ServerAddress;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.transitions.Mongod;
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess;
import de.flapdoodle.reverse.TransitionWalker;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.concurrent.CountDownLatch;

@Slf4j
class MongoAsyncDriver {

    public static void main(String[] args) throws InterruptedException {
        var latch = new CountDownLatch(1);
        try (TransitionWalker.ReachedState<RunningMongodProcess> running = Mongod.instance().start(Version.Main.PRODUCTION)) {
            ServerAddress serverAddress = running.current().getServerAddress();
            try (MongoClient mongoClient = MongoClients.create("mongodb://" + serverAddress.toString())) {
                MongoDatabase database = mongoClient.getDatabase("mydb");
                MongoCollection<Document> collection = database.getCollection("test");
                var doc = new Document("name", "MongoDB")
                        .append("type", "database")
                        .append("count", 1)
                        .append("info", new Document("x", 203).append("y", 102));
                collection.insertOne(doc, (result, t) -> {
                    if (t != null) {
                        System.err.println("Insert failed: " + t.getMessage());
                        //
                        // ~ ensure the latch is decremented on error
                        //
                        latch.countDown();
                        return;
                    }
                    log.info("Inserted successfully");
                    collection.count(new SingleResultCallback<Long>() {
                        @Override
                        public void onResult(final Long count, final Throwable t) {
                            if (t != null) {
                                System.err.println("Count failed: " + t.getMessage());
                            } else {
                                log.info("Count: {}", count);
                            }
                            //
                            // ~ decrement latch in any case
                            //
                            latch.countDown();
                        }
                    });
                });
                latch.await();
            }
        }
    }

}
