package org.bayesianfreaks.nlp.tokenizer.example;

import org.bayesianfreaks.nlp.parserc.Token;
import org.bayesianfreaks.nlp.tokenizer.Printer;
import org.bayesianfreaks.nlp.tokenizer.StateMachine;

import static org.bayesianfreaks.nlp.tokenizer.example.SingleBraceStateMachine.SingleBraceState.Inner;
import static org.bayesianfreaks.nlp.tokenizer.example.SingleBraceStateMachine.SingleBraceState.Outer;

public class SingleBracePrinter implements Printer {
    private static final Token openToken = Token.of("(");
    private static final Token closeToken = Token.of(")");

    @Override
    public String write(final StateMachine.State state, final Token token) {
        final StateMachine.State s = state.getState();
        if (s.equals(Outer)) {
            if (token.equals(openToken)) {
                return Decorator.ofToken(token)
                        .newline()
                        .toString();
            } else {
                return Decorator.ofToken(token)
                        .toString();
            }

        } else if (s.equals(Inner)) {
            if (token.equals(closeToken)) {
                return Decorator.ofToken(token)
                        .newline()
                        .toString();
            } else {
                return Decorator.ofToken(token)
                        .indent(1)
                        .newline()
                        .toString();
            }
        } else {
            return token.getValue();
        }
    }
}