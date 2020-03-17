package org.bayesianfreaks.text.tokenizer.example;

import org.bayesianfreaks.text.parserc.Parsers;
import org.bayesianfreaks.text.parserc.Source;
import org.bayesianfreaks.text.parserc.Token;
import org.bayesianfreaks.text.tokenizer.StateMachine;
import org.bayesianfreaks.text.tokenizer.Tokenizer;

import java.util.Optional;

public class SingleBraceTokenizer implements Tokenizer {

    private static Parsers.Parser a = Parsers.character('a').many().orElse(Parsers.br);
    private static Parsers.Parser b = Parsers.character('b').many().orElse(Parsers.br);
    private static Parsers.Parser openBrace = Parsers.character('(');
    private static Parsers.Parser closeBrace = Parsers.character(')');
    private static Parsers.Parser outerParser = a.orElse(openBrace);
    private static Parsers.Parser innerParser = b.orElse(closeBrace);

    @Override
    public Optional<Token> read(StateMachine.State state, Source source) {
        final SingleBraceStateMachine.SingleBraceState s = (SingleBraceStateMachine.SingleBraceState) state;
        switch (s) {
            case Inner:
                return innerParser.parse(source);
            case Outer:
                return outerParser.parse(source);
            default:
                return Optional.empty();
        }
    }

}
