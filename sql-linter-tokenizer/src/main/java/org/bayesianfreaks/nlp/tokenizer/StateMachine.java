package org.bayesianfreaks.nlp.tokenizer;


import org.bayesianfreaks.nlp.parserc.Token;

public interface StateMachine {
    public State getState();

    public void receive(final Token token);

    void initialize();

    interface State {
        public State getState();
    }
}
