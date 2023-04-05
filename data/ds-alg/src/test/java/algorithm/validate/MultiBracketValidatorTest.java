package algorithm.validate;

import static org.junit.jupiter.api.Assertions.*;

class MultiBracketValidatorTest implements ExpressionValidatorTest{

    @Override
    public ExpressionValidator validator() {
        return new MultiBracketValidator();
    }

}