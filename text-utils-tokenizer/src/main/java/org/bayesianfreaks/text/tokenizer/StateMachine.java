package org.bayesianfreaks.text.tokenizer;


import org.bayesianfreaks.text.parserc.Token;

public interface StateMachine {
    public State getState();

    public void receive(final Token token);

    void initialize();

    interface State {
    }
}
