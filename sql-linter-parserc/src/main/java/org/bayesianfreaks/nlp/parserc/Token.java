package org.bayesianfreaks.nlp.parserc;

import java.util.Objects;

public class Token {
    private final String value;

    private Token(final String s) {
        this.value = s;
    }

    public static Token of(final String s) {
        return new Token(s);
    }

    public static Token ofChar(final char s) {
        return Token.of(String.valueOf(s));
    }

    public static Token ofEmpty() {
        return Token.of("");
    }

    public String getValue() {
        return this.value;
    }

    public Token combine(final Token other) {
        return Token.of(this.toString() + other.toString());
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
