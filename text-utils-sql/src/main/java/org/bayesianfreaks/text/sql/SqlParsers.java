package org.bayesianfreaks.text.sql;

import org.bayesianfreaks.text.parserc.Parsers;

public class SqlParsers extends Parsers {
    public static final Parser create = string("create");
    public static final Parser delete = string("delete");
    public static final Parser insert = string("insert");
    public static final Parser update = string("update");
    public static final Parser select = string("select");
    public static final Parser with = string("with");
    public static final Parser from = string("from");
    public static final Parser where = string("where");
    public static final Parser innerJoin = string("inner join");
    public static final Parser join = string("join");
    public static final Parser leftJoin = string("left join");
    public static final Parser rightJoin = string("right join");
    public static final Parser on = string("on");
    public static final Parser groupBy = string("group by");
    public static final Parser having = string("having");
    public static final Parser over = string("over");
    public static final Parser partitionBy = string("partition by");
    public static final Parser orderBy = string("order by");
    public static final Parser as = string("as");
    public static final Parser open = string("(");
    public static final Parser close = string(")");

    public static final Parser comparator = br
            .orElse(string("<"))
            .orElse(string(">"))
            .orElse(string("<="))
            .orElse(string(">="))
            .orElse(string("=="))
            .orElse(string("!="))
            .orElse(string("like"))
            .orElse(string("is"));

    public static final Parser operator = br
            .orElse(string("+"))
            .orElse(string("-"))
            .orElse(string("*"))
            .orElse(string("/"))
            .orElse(string("&"))
            .orElse(string("|"));

    public static final Parser bool = string("true").orElse(string("false"));

    public static final Parser name = lower
            .combine(letter.orElse(digit).orElse(underScore).many())
            .combine(lower.orElse(digit));


}
