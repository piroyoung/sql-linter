package org.bayesianfreaks.text.tokenizer.example;

import org.bayesianfreaks.text.parserc.Token;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleBracePrinterTest {

    @Test
    void print() {
        final String expected = "aa(\n  bbb\n)\naaaa";
        final List<Token> tokens = Arrays.asList(
                Token.of("aa"),
                Token.of("("),
                Token.of("bbb"),
                Token.of(")"),
                Token.of("aaaa")
        );
        final SingleBraceStateMachine m = SingleBraceStateMachine.ofInitialState();
        final SingleBracePrinter printer = new SingleBracePrinter();

        assertEquals(expected, printer.print(m, tokens));
    }
}