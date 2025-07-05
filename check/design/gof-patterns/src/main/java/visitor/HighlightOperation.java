package visitor;

class HighlightOperation implements Operation {

    @Override
    public void apply(HeadingNode node) {
        System.out.println("highlight-heading");
    }

    @Override
    public void apply(AnchorNode node) {
        System.out.println("highlight-anchor");
    }

}
