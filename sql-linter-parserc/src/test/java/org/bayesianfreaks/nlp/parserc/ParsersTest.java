package org.bayesianfreaks.nlp.parserc;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ParsersTest {

    @Test
    void examineSuccess() {
        final Source source = Source.of("abc");
        final Parsers.Parser anyChar = Parsers.examine(c -> true);

        final Optional<Token> token = anyChar.parse(source);
        assertTrue(token.isPresent());
        token.ifPresent(t -> assertEquals("a", t.toString()));
        assertEquals(1, source.getPos());

    }

    @Test
    void examineFail() {
        final Source source = Source.of("abc");
        final Parsers.Parser anyChar = Parsers.examine(c -> false);

        final Optional<Token> token = anyChar.parse(source);
        assertFalse(token.isPresent());
        assertEquals(0, source.getPos());
    }

    @Test
    void characterSuccess() {
        final Source source = Source.of("abc");
        final Parsers.Parser a = Parsers.character('a');

        final Optional<Token> token = a.parse(source);
        assertTrue(token.isPresent());
        token.ifPresent(t -> assertEquals("a", t.toString()));
        assertEquals(1, source.getPos());

    }

    @Test
    void characterFail() {
        final Source source = Source.of("abc");
        final Parsers.Parser a = Parsers.character('b');

        final Optional<Token> token = a.parse(source);
        assertFalse(token.isPresent());
        assertEquals(0, source.getPos());
    }

    @Test
    void sequenceSuccess() {
        final Source source = Source.of("ab1ab2");
        final Parsers.Parser digit = Parsers.examine(Character::isDigit);
        final Parsers.Parser lower = Parsers.examine(Character::isLowerCase);
        final Parsers.Parser phrase = Parsers.sequence(lower, lower, digit);

        var res1 = phrase.parse(source);
        assertTrue(res1.isPresent());
        res1.ifPresent(token -> {
            assertEquals("ab1", token.toString());
            assertEquals(3, source.getPos());
        });

        var res2 = phrase.parse(source);
        assertTrue(res2.isPresent());
        res2.ifPresent(token -> {
            assertEquals("ab2", token.toString());
            assertEquals(6, source.getPos());
        });

        var res3 = phrase.parse(source);
        assertFalse(res3.isPresent());
        assertTrue(source.isEof());

    }

    @Test
    void sequenceFail() {
        final Source source = Source.of("aaa");
        final Parsers.Parser digit = Parsers.examine(Character::isDigit);
        final Parsers.Parser lower = Parsers.examine(Character::isLowerCase);
        final Parsers.Parser phrase = Parsers.sequence(lower, lower, digit);

        var res = phrase.parse(source);
        assertFalse(res.isPresent());
        assertFalse(source.isEof());
    }

    @Test
    void many() {
        final Source source = Source.of("aaa111");
        final Parsers.Parser digits = Parsers.examine(Character::isDigit).many();
        final Parsers.Parser letters = Parsers.examine(Character::isLetter).many();

        assertFalse(digits.parse(source).isPresent());

        var res1 = letters.parse(source);
        assertTrue(res1.isPresent());
        res1.ifPresent(token -> assertEquals("aaa", token.toString()));
        assertFalse(source.isEof());

        var res2 = digits.parse(source);
        assertTrue(res2.isPresent());
        res2.ifPresent(token -> assertEquals("111", token.toString()));
        assertTrue(source.isEof());
    }

    @Test
    void orElse() {
        final Source source = Source.of("abcd");
        final var a = Parsers.character('a');
        final var b = Parsers.character('b');
        final var c = Parsers.character('c');
        final var abc = a.orElse(b).orElse(c);

        var r1 = abc.parse(source);
        assertTrue(r1.isPresent());

        var r2 = abc.parse(source);
        assertTrue(r2.isPresent());

        var r3 = abc.parse(source);
        assertTrue(r3.isPresent());

        var r4 = abc.parse(source);
        assertFalse(r4.isPresent());

    }

    @Test
    void repeat() {
        final Source source = Source.of("aaaaa");
        final var a = Parsers.character('a');

        var r1 = a.repeat(1).parse(source);
        assertTrue(r1.isPresent());

        var r2 = a.repeat(2).parse(source);
        assertTrue(r2.isPresent());

        var r3 = a.repeat(3).parse(source);
        assertFalse(r3.isPresent());

        var r4 = a.repeat(2).parse(source);
        assertTrue(source.isEof());

    }

    @Test
    void string() {
        final Source source = Source.of("hello world");
        final var hello = Parsers.string("hello");
        final var whitespace = Parsers.character(' ');
        final var world = Parsers.string("world");

        var r1 = hello.parse(source);
        assertTrue(r1.isPresent());

        var r2 = whitespace.parse(source);
        assertTrue(r2.isPresent());

        var r3 = world.parse(source);
        assertTrue(r3.isPresent());
        assertTrue(source.isEof());
    }

    @Test
    void withToken() {
        final Token replaced = Token.of("replaced");
        final Source source = Source.of("a");
        final Parsers.Parser parser = Parsers.letter.withToken(Token.of("replaced"));

        var res = parser.parse(source);
        assertTrue(res.isPresent());
        res.ifPresent(t -> assertEquals(t, replaced));
    }
}