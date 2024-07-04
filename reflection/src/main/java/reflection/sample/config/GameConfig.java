package reflection.sample.config;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class GameConfig {
    private int releaseYear;
    private String gameName;
    private double price;
    private String[] characterNames;
}
