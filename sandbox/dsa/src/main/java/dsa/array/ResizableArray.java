package dsa.array;

import dsa.list.List;

interface ResizableArray<E> extends Array<E>, List<E> {

    @Override
    void set(int index, E value);

    @Override
    E get(int index);

    void trimToSize();

}
