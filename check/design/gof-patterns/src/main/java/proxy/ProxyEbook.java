package proxy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ProxyEbook implements Ebook {

    private final String fileName;
    private RealEbook realEbook;

    @Override
    public void show() {
        if (realEbook == null)
            realEbook = new RealEbook(fileName);
        realEbook.show();
    }

    @Override
    public String fileName() {
        return fileName;
    }

}
