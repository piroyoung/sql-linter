package org.bayesianfreaks.text.sql;

import org.bayesianfreaks.text.parserc.Token;
import org.bayesianfreaks.text.tokenizer.StateMachine;

public class SqlStateMachine implements StateMachine {
    @Override
    public State getState() {
        return null;
    }

    @Override
    public void receive(Token token) {

    }

    @Override
    public void initialize() {

    }

    enum SqlStatus implements State {
    }
}
