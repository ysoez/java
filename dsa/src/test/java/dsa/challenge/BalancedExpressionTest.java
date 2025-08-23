package dsa.challenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalancedExpressionTest {

    private final BalancedExpression validator = new BalancedExpression();

    @Test
    void testPositiveCases() {
        assertTrue(validator.test(""));
        assertTrue(validator.test("(1 + 2)"));
        assertTrue(validator.test("[1 + 2]"));
        assertTrue(validator.test("{1 + 2}"));
        assertTrue(validator.test("<1 + 2>"));
        assertTrue(validator.test("{[()]}"));
        assertTrue(validator.test("()[]{}<>"));
        assertTrue(validator.test("a+(b*c)-{d/[e<f>]}"));
    }

    @Test
    void testNegativeCases() {
        assertFalse(validator.test("("));
        assertFalse(validator.test(")"));
        assertFalse(validator.test(")("));
        assertFalse(validator.test("(]"));
        assertFalse(validator.test("(>"));
        assertFalse(validator.test("(}"));
        assertFalse(validator.test("(<"));
        assertFalse(validator.test("([)]"));
        assertFalse(validator.test("()[])"));
        assertFalse(validator.test("(()"));
        assertFalse(validator.test("}}}}"));
    }

    @Test
    void testCornerCases() {
        assertThrows(IllegalArgumentException.class, () -> validator.test(null));
        assertTrue(validator.test("   "));
        assertTrue(validator.test("a"));
    }

}