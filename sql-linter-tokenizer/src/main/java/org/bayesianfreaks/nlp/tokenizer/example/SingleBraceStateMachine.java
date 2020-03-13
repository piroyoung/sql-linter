package org.bayesianfreaks.nlp.tokenizer.example;

import org.bayesianfreaks.nlp.parserc.Token;
import org.bayesianfreaks.nlp.tokenizer.StateMachine;

public class SingleBraceStateMachine implements StateMachine {
    private static final Token openToken = Token.of("(");
    private static final Token closeToken = Token.of(")");
    private SingleBraceState state;

    private SingleBraceStateMachine(final SingleBraceState state) {
        this.state = state;
    }

    public static SingleBraceStateMachine ofInitialState() {
        return new SingleBraceStateMachine(SingleBraceState.Outer);
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void receive(final Token token) {
        if (token.equals(openToken)) {
            this.state = SingleBraceState.Inner;
        } else if (token.equals(closeToken)) {
            this.state = SingleBraceState.Outer;
        }
    }

    @Override
    public void initialize() {
        this.state = SingleBraceState.Outer;
    }

    public enum SingleBraceState implements State {
        Inner,
        Outer;

        @Override
        public State getState() {
            return this;
        }
    }
}
