package adapter.image;

import adapter.image.awesome_filters.Caramel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CaramelAdapter extends Caramel implements Filter {

    @Override
    public void apply(Image image) {
        init();
        render(image);
    }

}
