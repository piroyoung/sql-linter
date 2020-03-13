package org.bayesianfreaks.nlp.parserc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class TokenTest {

    @Test
    void testEquals() {
        final Token t1 = Token.ofChar('a');
        final Token t2 = Token.of("a");
        final Token t3 = Token.ofChar('b');
        assertEquals(t1, t2);
        assertNotSame(t1, t3);

    }
}