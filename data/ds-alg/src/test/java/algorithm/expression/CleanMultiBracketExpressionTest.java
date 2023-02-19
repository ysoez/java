package algorithm.expression;

public class CleanMultiBracketExpressionTest implements ExpressionTest {

    @Override
    public Expression create(String value) {
        return new CleanMultiBracketExpression(value);
    }

}
