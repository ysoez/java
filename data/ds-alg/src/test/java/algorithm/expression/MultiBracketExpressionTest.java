package algorithm.expression;

class MultiBracketExpressionTest implements ExpressionTest{

    @Override
    public Expression create(String value) {
        return new MultiBracketExpression(value);
    }

}