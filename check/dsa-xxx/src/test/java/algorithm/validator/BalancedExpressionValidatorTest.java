package algorithm.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BalancedExpressionValidatorTest {

    @Test
    void isBalancedTest() {
        var validator = new BalancedExpressionValidator();
        assertTrue(validator.test(""));
        assertTrue(validator.test("(1 + 1)"));
        assertTrue(validator.test("(<1> + <1>)"));

        assertFalse(validator.test("(1 + 1"));
        assertFalse(validator.test("(1 + 1]"));
        assertFalse(validator.test("((1 + 1)"));
        assertFalse(validator.test("(1> + 1"));
        assertFalse(validator.test("(1] + 1"));
        assertFalse(validator.test("((1) + 1"));
        assertFalse(validator.test(")1 + 1("));
    }

}