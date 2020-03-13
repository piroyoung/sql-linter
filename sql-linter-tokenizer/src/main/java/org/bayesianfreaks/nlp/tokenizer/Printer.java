package org.bayesianfreaks.nlp.tokenizer;

import org.bayesianfreaks.nlp.parserc.Token;

import java.util.List;
import java.util.function.Function;

public interface Printer {
    public String write(final StateMachine.State state, final Token token);

    default String print(final StateMachine m, final List<Token> tokens) {
        final StringBuilder sb = new StringBuilder();
        for (final var token : tokens) {
            sb.append(write(m.getState(), token));
            m.receive(token);
        }

        return sb.toString();
    }

    class Decorator {
        private final Token token;
        private int nIndent;
        private String indentString;
        private boolean hasNewline;
        private boolean hasAfterSpace;
        private Function<String, String> mutation;

        private Decorator(final Token token, final int nIndent, final String indentString, final boolean hasNewline, final boolean hasAfterSpace, final Function<String, String> mutation) {
            this.token = token;
            this.nIndent = nIndent;
            this.indentString = indentString;
            this.hasNewline = hasNewline;
            this.hasAfterSpace = hasAfterSpace;
            this.mutation = mutation;
        }

        public static Decorator ofToken(final Token token) {
            return new Decorator(token, 0, "  ", false, false, s -> s);
        }

        public Decorator lower() {
            this.mutation = String::toLowerCase;
            return this;
        }

        public Decorator upper() {
            this.mutation = String::toUpperCase;
            return this;
        }

        public Decorator indent(final int n) {
            this.nIndent = n;
            return this;
        }

        public Decorator newline() {
            this.hasNewline = true;
            return this;
        }

        public Decorator afterSpace() {
            this.hasAfterSpace = true;
            return this;
        }

        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(mutation.apply(this.token.toString()));

            for (int i = 0; i < this.nIndent; i++) {
                sb.insert(0, this.indentString);
            }

            if (hasAfterSpace) {
                sb.append(" ");
            }

            if (hasNewline) {
                sb.append("\n");
            }
            return sb.toString();

        }

    }
}
