package algorithm.validate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

interface ExpressionValidatorTest {

    ExpressionValidator validator();

    @Test
    default void isValid() {
        assertTrue(validator().isValid(""));
        assertTrue(validator().isValid("(1 + 1)"));
        assertTrue(validator().isValid("(<1> + <1>)"));

        assertFalse(validator().isValid("(1 + 1"));
        assertFalse(validator().isValid("(1> + 1"));
        assertFalse(validator().isValid("(1] + 1"));
        assertFalse(validator().isValid("((1) + 1"));
        assertFalse(validator().isValid(")1 + 1("));
    }

}