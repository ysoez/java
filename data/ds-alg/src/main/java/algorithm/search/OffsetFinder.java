package algorithm.search;

interface OffsetFinder<I, O> {
    O search(I input, int offset);
}
