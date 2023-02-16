package data_structure.linked_list;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class UnaryNode<T> {
    final T value;
    @Setter
    UnaryNode<T> next;
}
