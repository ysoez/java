package algorithm.expression;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.List;

@RequiredArgsConstructor
public class CleanMultiBracketExpression implements Expression {

    private static final List<Character> OPEN = List.of('(', '<', '[');
    private static final List<Character> CLOSE = List.of(')', '>', ']');

    private final String value;

    @Override
    public boolean isValid() {
        if (value == null || value.equals(""))
            return true;
        var openCharStack = new ArrayDeque<Character>();
        for (char ch : value.toCharArray()) {
            if (isLeftBracket(ch))
                openCharStack.push(ch);
            if (isRightBracket(ch)) {
                if (openCharStack.isEmpty())
                    return false;
                var lastOpen = openCharStack.pop();
                if (!isBracketsMatch(lastOpen, ch))
                    return false;
            }
        }
        return openCharStack.isEmpty();
    }

    private static boolean isLeftBracket(char ch) {
        return OPEN.contains(ch);
    }

    private static boolean isRightBracket(char ch) {
        return CLOSE.contains(ch);
    }

    private static boolean isBracketsMatch(char open, char close) {
        return OPEN.indexOf(open) == CLOSE.indexOf(close);
    }

}
