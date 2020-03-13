package org.bayesianfreaks.parserc;

public class Source {
    private final String s;
    private int pos;

    private Source(final String s, final int pos) {
        this.s = s;
        this.pos = pos;
    }

    public static Source of(String s) {
        return new Source(s, 0);
    }

    public char peak() {
        return s.charAt(pos);
    }

    public void next() {
        this.pos++;
    }

}
