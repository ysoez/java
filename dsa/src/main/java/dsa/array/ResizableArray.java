package dsa.array;

import dsa.list.List;

interface ResizableArray<E> extends Array<E>, List<E> {

    void trimToSize();

}
