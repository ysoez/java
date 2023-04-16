package adapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ImageView {

    private final Image image;

    void apply(Filter filter) {
        filter.apply(image);
    }

}
