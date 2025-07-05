package composite;

class Shape implements Component {

    @Override
    public void render() {
        System.out.println("Rendered: " + getClass());
    }

    @Override
    public void move() {
        System.out.println("Moved: " + getClass());
    }

}
