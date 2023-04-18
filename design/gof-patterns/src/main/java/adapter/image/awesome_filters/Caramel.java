package adapter.image.awesome_filters;

import adapter.image.Image;

public class Caramel {

    public void init() {
        System.out.println("Initialized");
    }

    public void render(Image image) {
        System.out.println("Applying: " + getClass());
    }

}
