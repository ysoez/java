package algorithm.expression;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;

@RequiredArgsConstructor
public class SingleBracketExpression implements Expression {

    private final String value;

    @Override
    public boolean isValid() {
        if (value == null || value.equals(""))
            return true;
        var openCharStack = new ArrayDeque<Character>();
        for (char ch : value.toCharArray()) {
            if (ch == '(') {
                openCharStack.push(ch);
            }
            if (ch == ')') {
                if (openCharStack.isEmpty())
                    return false;
                openCharStack.pop();
            }
        }
        return openCharStack.isEmpty();
    }

}
