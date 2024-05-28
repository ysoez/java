package vertx;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Map;

class VertxJson {

    public static void main(String[] args) {
        var jsonObject = new JsonObject();
        jsonObject.put("id", 1);
        jsonObject.put("type", "object");

        System.out.printf("Encoded: %s\n", jsonObject.encode());
        System.out.printf("Decoded: %s\n", new JsonObject(jsonObject.encode()));

        System.out.printf("Decoded: %s\n", new JsonObject(Map.of("1", "A", "2", "B")));

        JsonArray jsonArray = new JsonArray()
                .add("String value")
                .add(123)
                .add(true)
                .add(new JsonObject().put("key", "value"));
        System.out.println(jsonArray.encodePrettily());
    }

}
