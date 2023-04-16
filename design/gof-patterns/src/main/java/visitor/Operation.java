package visitor;

interface Operation {

    void apply(HeadingNode node);
    void apply(AnchorNode node);

}
