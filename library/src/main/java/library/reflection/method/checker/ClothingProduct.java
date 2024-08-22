package library.reflection.method.checker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class ClothingProduct extends Product {

    private Size size;
    private String color;

    enum Size {
        SMALL,
        MEDIUM,
        LARGE,
    }

}
