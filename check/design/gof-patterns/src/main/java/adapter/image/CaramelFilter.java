package adapter.image;

import adapter.image.awesome_filters.Caramel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CaramelFilter implements Filter {

    private final Caramel caramel;

    @Override
    public void apply(Image image) {
        caramel.init();
        caramel.render(image);
    }

}
