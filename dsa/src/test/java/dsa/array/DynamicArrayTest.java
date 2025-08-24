package dsa.array;

class DynamicArrayTest extends ResizableArrayTest {

    @Override
    public ResizableArray<Integer> newList() {
        return new DynamicArray<>();
    }

}