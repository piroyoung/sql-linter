package org.bayesianfreaks.text.sql;

import org.bayesianfreaks.text.parserc.Source;
import org.bayesianfreaks.text.parserc.Token;
import org.bayesianfreaks.text.tokenizer.StateMachine;
import org.bayesianfreaks.text.tokenizer.Tokenizer;

import java.util.Optional;

public class SqlTokenizer implements Tokenizer {
    @Override
    public Optional<Token> read(StateMachine.State state, Source source) {
        return Optional.empty();
    }
}
