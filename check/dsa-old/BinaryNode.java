package util;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class BinaryNode<T> {
    public final T value;
    public BinaryNode<T> left;
    public BinaryNode<T> right;
}