package org.bayesianfreaks.text.parserc;

public class Source {
    private final String s;
    private final int length;
    private int pos;

    private Source(final String s, final int length, final int pos) {
        this.s = s;
        this.length = length;
        this.pos = pos;
    }

    public static Source of(final String s) {
        return new Source(s, s.length(), 0);
    }

    public char peak() {
        return s.charAt(this.pos);
    }

    public void next() {
        this.pos++;
    }

    public int getPos() {
        return this.pos;
    }

    public void setPos(final int pos) {
        this.pos = pos;
    }

    public boolean isEof() {
        return this.length == this.pos;
    }

}
