package adapter;

import adapter.awesome_filters.Caramel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CaramelAdapter extends Caramel implements Filter {

    @Override
    public void apply(Image image) {
        init();
        render(image);
    }

}
