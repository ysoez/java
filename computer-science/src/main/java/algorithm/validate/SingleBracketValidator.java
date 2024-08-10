package algorithm.validate;

import java.util.ArrayDeque;

class SingleBracketValidator implements ExpressionValidator {

    @Override
    public boolean isValid(String expression) {
        if (expression == null || expression.equals(""))
            return true;
        var openCharStack = new ArrayDeque<Character>();
        for (char ch : expression.toCharArray()) {
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
