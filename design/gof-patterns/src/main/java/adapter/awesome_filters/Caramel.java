package adapter.awesome_filters;

import adapter.Image;

public class Caramel {

    public void init() {
        System.out.println("Initialized");
    }

    public void render(Image image) {
        System.out.println("Applying: " + getClass());
    }

}
