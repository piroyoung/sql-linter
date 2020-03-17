package org.bayesianfreaks.text.tokenizer.example;

import org.bayesianfreaks.text.parserc.Token;
import org.bayesianfreaks.text.tokenizer.Printer;
import org.bayesianfreaks.text.tokenizer.StateMachine;

public class SingleBracePrinter implements Printer {
    private static final Token openToken = Token.of("(");
    private static final Token closeToken = Token.of(")");

    @Override
    public String write(final StateMachine.State state, final Token token) {
        final SingleBraceStateMachine.SingleBraceState s = (SingleBraceStateMachine.SingleBraceState) state;
        switch (s) {
            case Inner:
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

            case Outer:
                if (token.equals(openToken)) {
                    return Decorator.ofToken(token)
                            .newline()
                            .toString();
                } else {
                    return Decorator.ofToken(token)
                            .toString();
                }

            default:
                return token.getValue();

        }
    }
}
