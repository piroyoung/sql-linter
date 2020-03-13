package org.bayesianfreaks.nlp.parserc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SourceTest {


    @Test
    void peak() {
        final Source source = Source.of("abc");

        assertEquals('a', source.peak());
    }

    @Test
    void next() {
        final Source source = Source.of("abc");

        source.next();
        assertEquals('b', source.peak());
    }

    @Test
    void isEof() {
        final Source source = Source.of("abc");
        assertEquals('a', source.peak());
        source.next();
        assertEquals('b', source.peak());
        source.next();
        assertEquals('c', source.peak());
        source.next();
        assertTrue(source.isEof());
    }
}