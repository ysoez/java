package adapter.image;

public class VividFilter implements Filter {

    @Override
    public void apply(Image image) {
        System.out.println("Applied: " + getClass());
    }

}
