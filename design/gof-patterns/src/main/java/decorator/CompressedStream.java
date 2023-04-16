package decorator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CompressedStream implements Stream {

    private final Stream stream;

    @Override
    public void write(String data) {
        System.out.println("Compressed: " + data);
        stream.write(data);
    }

}
