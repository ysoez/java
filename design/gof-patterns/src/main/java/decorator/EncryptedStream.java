package decorator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EncryptedStream extends CloudStream {

    private final Stream stream;

    @Override
    public void write(String data) {
        System.out.println("Encrypted: " + data);
        stream.write(data);
    }

}
