package memento;

class Document {

    String content;
    String fontName;
    String fontSize;

    public DocumentState createState() {
        return new DocumentState(content, fontName, fontSize);
    }

    public void restore(DocumentState state) {
        content = state.content();
        fontName = state.fontName();
        fontSize = state.fontSize();
    }

}
