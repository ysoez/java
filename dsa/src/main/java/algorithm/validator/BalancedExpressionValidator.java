package algorithm.validator;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

@RequiredArgsConstructor
class BalancedExpressionValidator implements Predicate<String> {

    private static final List<Character> OPENING_SYMBOLS = List.of('(');
    private static final List<Character> CLOSING_SYMBOLS = List.of(')');

    public boolean test(String expression) {
        var stack = new Stack<Character>();
        for (char ch : expression.toCharArray()) {
            if (isOpeningSymbol(ch)) {
                stack.push(ch);
            }
            if (isClosingSymbol(ch)) {
                if (stack.isEmpty())
                    return false;
                if (!isMatch(ch, stack.pop()))
                    return false;
            }
        }
        return stack.isEmpty();
    }

    private boolean isMatch(char currentChar, Character opening) {
        return opening.equals(OPENING_SYMBOLS.get(CLOSING_SYMBOLS.indexOf(currentChar)));
    }

    private boolean isOpeningSymbol(char ch) {
        return OPENING_SYMBOLS.contains(ch);
    }

    private boolean isClosingSymbol(char ch) {
        return CLOSING_SYMBOLS.contains(ch);
    }

}
