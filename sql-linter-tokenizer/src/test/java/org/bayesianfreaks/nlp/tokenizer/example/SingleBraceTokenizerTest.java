package org.bayesianfreaks.nlp.tokenizer.example;

import org.bayesianfreaks.nlp.parserc.Source;
import org.bayesianfreaks.nlp.parserc.Token;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SingleBraceTokenizerTest {

    @Test
    void read() {
        final Source source = Source.of("aa    aa(bbb bbb)aaaa");
        final SingleBraceStateMachine m = SingleBraceStateMachine.ofInitialState();
        final SingleBraceTokenizer t = new SingleBraceTokenizer();

        while (!source.isEof()) {
            final Optional<Token> res = t.read(m.getState(), source);
            if (res.isEmpty()) {
                break;
            } else {
                m.receive(res.get());
            }
        }
        assertTrue(source.isEof());

    }

    @Test
    void tokenize() {
        final List<Token> expected = Arrays.asList(
                Token.of("aa"),
                Token.of("aa"),
                Token.of("("),
                Token.of("bbb"),
                Token.of("bbb"),
                Token.of(")"),
                Token.of("aaaa")
        );

        final Source source = Source.of("aa    aa(bbb bbb)aaaa");
        final SingleBraceStateMachine m = SingleBraceStateMachine.ofInitialState();
        final SingleBraceTokenizer t = new SingleBraceTokenizer();

        final Optional<List<Token>> result = t.tokenize(m, source);
        assertTrue(result.isPresent());
        result.ifPresent(tokens -> {
            assertEquals(expected, tokens);
        });

    }

    @Test
    void tokenizeFail() {
        final Source source = Source.of("aa(bab)aaaa");
        final SingleBraceStateMachine m = SingleBraceStateMachine.ofInitialState();
        final SingleBraceTokenizer t = new SingleBraceTokenizer();
        final Optional<List<Token>> result = t.tokenize(m, source);

        assertTrue(result.isEmpty());

    }
}