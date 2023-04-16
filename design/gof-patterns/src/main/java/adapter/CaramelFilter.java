package adapter;

import adapter.awesome_filters.Caramel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CaramelFilter implements Filter {

    private final Caramel caramel;

    @Override
    public void apply(Image image) {
        caramel.init();
        caramel.render(image);
    }

}
