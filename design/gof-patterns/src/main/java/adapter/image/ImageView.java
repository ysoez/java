package adapter.image;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageView {

    private final Image image;

    public void apply(Filter filter) {
        filter.apply(image);
    }

}
