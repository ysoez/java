package vertx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// immutable obj -> json ok, json -> obj not ok
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList implements Json {
    private List<Asset> assets;
}
