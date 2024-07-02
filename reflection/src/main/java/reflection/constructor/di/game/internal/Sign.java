package reflection.constructor.di.game.internal;

enum Sign {
    EMPTY(' '),
    X('X'),
    Y('Y');

    private final char value;

    Sign(char value) {
        this.value = value;
    }

    public char getValue() {
        return this.value;
    }
}
