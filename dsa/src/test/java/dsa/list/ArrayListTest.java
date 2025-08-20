package dsa.list;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListTest extends ListTest {

    @Override
    public List<Integer> newList() {
        return new ArrayList<>();
    }

}