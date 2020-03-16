package org.bayesianfreaks.nlp.tokenizer.example;

import org.bayesianfreaks.nlp.parserc.Parsers;
import org.bayesianfreaks.nlp.parserc.Source;
import org.bayesianfreaks.nlp.parserc.Token;
import org.bayesianfreaks.nlp.tokenizer.StateMachine;
import org.bayesianfreaks.nlp.tokenizer.Tokenizer;

import java.util.Optional;

import static org.bayesianfreaks.nlp.tokenizer.example.SingleBraceStateMachine.SingleBraceState.Inner;
import static org.bayesianfreaks.nlp.tokenizer.example.SingleBraceStateMachine.SingleBraceState.Outer;

public class SingleBraceTokenizer implements Tokenizer {

    private static Parsers.Parser a = Parsers.character('a').many().orElse(Parsers.br);
    private static Parsers.Parser b = Parsers.character('b').many().orElse(Parsers.br);
    private static Parsers.Parser openBrace = Parsers.character('(');
    private static Parsers.Parser closeBrace = Parsers.character(')');
    private static Parsers.Parser outerParser = a.orElse(openBrace);
    private static Parsers.Parser innerParser = b.orElse(closeBrace);

    @Override
    public Optional<Token> read(StateMachine.State state, Source source) {
        if (state.equals(Outer)) {
            return outerParser.parse(source);
        } else if (state.equals(Inner)) {
            return innerParser.parse(source);
        }
        return Optional.empty();
    }

}
