package algorithm.validate;

import java.util.ArrayDeque;
import java.util.List;

class MultiBracketValidator implements ExpressionValidator {

    private static final List<Character> OPEN = List.of('(', '<', '[');
    private static final List<Character> CLOSE = List.of(')', '>', ']');

    @Override
    public boolean isValid(String expression) {
        if (expression == null || expression.isBlank())
            return true;
        var openCharStack = new ArrayDeque<Character>();
        for (char ch : expression.toCharArray()) {
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
