package data_structure.array;

class StaticArrayTest implements ArrayTest {

    @Override
    public Array<Integer> create(Integer... value) {
        return new StaticArray<>(value);
    }

    @Override
    public Array<Integer> create(int capacity) {
        return new StaticArray<>(capacity);
    }

}
