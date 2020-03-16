package org.bayesianfreaks.nlp.parserc;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Parsers {
    public static final Parser letter = examine(Character::isLetter);
    public static final Parser lower = examine(Character::isLowerCase);
    public static final Parser upper = examine(Character::isUpperCase);
    public static final Parser digit = examine(Character::isDigit);
    public static final Parser whitespace = character(' ');
    public static final Parser comma = character(',');
    public static final Parser singleQuote = character('\'');
    public static final Parser doubleQuote = character('"');
    public static final Parser tab = character('\t');
    public static final Parser lf = character('\n');
    public static final Parser cr = character('\r');
    public static final Parser crlf = cr.combine(lf);
    public static final Parser br = whitespace
            .orElse(tab)
            .orElse(lf)
            .orElse(cr)
            .many()
            .withToken(Token.ofBreak());

    public static Parser examine(final Predicate<Character> predicate) {
        return source -> {
            if (source.isEof()) {
                return Optional.empty();

            }

            final Optional<Token> result = Optional.of(source)
                    .map(Source::peak)
                    .filter(predicate)
                    .map(Token::ofChar);
            result.ifPresent(t -> source.next());
            return result;
        };
    }

    public static Parser character(final char expected) {
        return examine(c -> c == expected);
    }

    public static Parser string(final String s) {
        return source -> {
            final ArrayList<Parser> parsers = new java.util.ArrayList<>();
            for (final char c : s.toCharArray()) {
                parsers.add(character(c));
            }

            return sequence(parsers).parse(source);
        };
    }

    public static Parser sequence(final Parser... parsers) {
        return sequence(List.of(parsers));
    }

    public static Parser sequence(final List<Parser> parsers) {
        return source -> {
            final StringBuilder sb = new StringBuilder();
            final int initialPos = source.getPos();
            for (Parser parser : parsers) {
                Optional<Token> result = parser.parse(source);
                if (result.isPresent()) {
                    result.ifPresent(token -> sb.append(token.toString()));

                } else {
                    source.setPos(initialPos);
                    return Optional.empty();

                }
            }
            final Token token = Token.of(sb.toString());
            return Optional.of(token);
        };
    }

    public static Parser many(final Parser parser) {
        return source -> {
            final StringBuilder sb = new StringBuilder();
            while (true) {
                final Optional<Token> result = parser.parse(source);
                if (result.isPresent()) {
                    result.ifPresent(t -> sb.append(t.toString()));

                } else {
                    return Optional.of(sb)
                            .filter(stringBuilder -> stringBuilder.length() > 0)
                            .map(StringBuilder::toString)
                            .map(Token::of);

                }
            }
        };
    }

    public static Parser orElse(final Parser one, final Parser two) {
        return source -> one.parse(source).or(() -> two.parse(source));
    }

    public static Parser repeat(final Parser parser, final int n) {
        return source -> {
            final StringBuilder sb = new StringBuilder();
            final int initialPos = source.getPos();

            for (int i = 0; i < n; i++) {
                final Optional<Token> result = parser.parse(source);
                if (result.isPresent()) {
                    result.ifPresent(token -> sb.append(token.toString()));

                } else {
                    source.setPos(initialPos);
                    return Optional.empty();

                }
            }
            final Token token = Token.of(sb.toString());
            return Optional.of(token);
        };
    }

    public static Parser withToken(final Parser parser, final Token token) {
        return source -> parser.parse(source).map(t -> token);
    }

    @FunctionalInterface
    public interface Parser {
        Optional<Token> parse(final Source s);

        default Parser orElse(final Parser other) {
            return Parsers.orElse(this, other);
        }

        default Parser many() {
            return Parsers.many(this);
        }

        default Parser repeat(final int n) {
            return Parsers.repeat(this, n);
        }

        default Parser combine(final Parser other) {
            return Parsers.sequence(this, other);
        }

        default Parser withToken(final Token token) {
            return Parsers.withToken(this, token);
        }
    }
}
