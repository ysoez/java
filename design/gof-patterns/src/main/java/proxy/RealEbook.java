package proxy;

class RealEbook implements Ebook {

    final String fileName;

    RealEbook(String fileName) {
        this.fileName = fileName;
        load();
    }

    @Override
    public void show() {
        System.out.println("Showing " + fileName);
    }

    @Override
    public String fileName() {
        return fileName;
    }

    private void load() {
        System.out.println("Loading " + fileName);
    }

}
