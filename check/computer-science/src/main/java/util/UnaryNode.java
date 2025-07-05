package util;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class UnaryNode<T> {
    public final T value;
    public UnaryNode<T> next;
}