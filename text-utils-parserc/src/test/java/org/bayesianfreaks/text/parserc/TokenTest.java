package org.bayesianfreaks.text.parserc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void testEquals() {
        final Token t1 = Token.ofChar('a');
        final Token t2 = Token.of("a");
        final Token t3 = Token.ofChar('b');
        assertEquals(t1, t2);
        assertNotSame(t1, t3);

    }

    @Test
    void of() {
        final var token = Token.of("a");
        assertEquals("a", token.getValue());
    }

    @Test
    void ofChar() {
        final var token = Token.ofChar('a');
        assertEquals("a", token.getValue());
    }

    @Test
    void ofBreak() {
        final var token = Token.ofBreak();
        assertTrue(token.isBreak());
    }

    @Test
    void getValue() {
        final var token = Token.of("a");
        assertEquals("a", token.getValue());
    }

    @Test
    void isBreak() {
        final var token = Token.ofBreak();
        assertTrue(token.isBreak());
    }

    @Test
    void isNotBreak() {
        final var token = Token.ofBreak();
        assertFalse(token.isNotBreak());
    }
}