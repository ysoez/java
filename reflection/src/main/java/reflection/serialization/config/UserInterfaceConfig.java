package reflection.serialization.config;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class UserInterfaceConfig {
    private String titleText;
    private String[] titleFonts;
    private short[] titleTextSizes;
}
