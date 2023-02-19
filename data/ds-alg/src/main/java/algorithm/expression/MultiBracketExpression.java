package algorithm.expression;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
class MultiBracketExpression implements Expression {

    private static final List<Character> OPEN = List.of('(', '<', '[');
    private static final List<Character> CLOSE = List.of(')', '>', ']');

    private final String value;

    @Override
    public boolean isValid() {
        if (value == null || value.equals(""))
            return true;
        var openCharStack = new ArrayDeque<Character>();
        for (char ch : value.toCharArray()) {
            if (OPEN.contains(ch)) {
                openCharStack.push(ch);
                continue;
            }
            int closeIndex = CLOSE.indexOf(ch);
            if (closeIndex != -1) {
                if (openCharStack.isEmpty())
                    return false;
                Character lastOpen = openCharStack.pop();
                Character open = OPEN.get(closeIndex);
                if (!Objects.equals(open, lastOpen))
                    return false;
            }
        }
        return openCharStack.isEmpty();
    }

}
