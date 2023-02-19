package algorithm.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

interface ExpressionTest {

    Expression create(String value);

    @Test
    default void isValid() {
        assertTrue(create("").isValid());
        assertTrue(create("(1 + 1)").isValid());
        assertTrue(create("(<1> + <1>)").isValid());

        assertFalse(create("(1 + 1").isValid());
        assertFalse(create("(1> + 1").isValid());
        assertFalse(create("(1] + 1").isValid());
        assertFalse(create("((1) + 1").isValid());
        assertFalse(create(")1 + 1(").isValid());
    }

}