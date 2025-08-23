package dsa.challenge;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.List;
import java.util.function.Predicate;

import static dsa.Algorithm.Complexity.LINEAR;

@RequiredArgsConstructor
class BalancedExpression implements Predicate<String> {

    private static final List<Character> OPENING_SYMBOLS = List.of('(', '<', '[', '{');
    private static final List<Character> CLOSING_SYMBOLS = List.of(')', '>', ']', '}');

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public boolean test(String expression) {
        if (expression == null)
            throw new IllegalArgumentException();
        if (expression.isBlank())
            return true;
        var stack = new ArrayDeque<Character>();
        for (char ch : expression.toCharArray()) {
            if (isOpeningSymbol(ch))
                stack.push(ch);
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
