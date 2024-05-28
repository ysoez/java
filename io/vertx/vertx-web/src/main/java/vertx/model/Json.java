package vertx.model;

import io.vertx.core.json.JsonObject;

public interface Json {

    default JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }

}
