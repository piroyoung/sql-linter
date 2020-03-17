package org.bayesianfreaks.text.sql;

import org.bayesianfreaks.text.parserc.Parsers;

public class SqlParsers extends Parsers {
    final Parser create = string("create");
    final Parser delete = string("delete");
    final Parser insert = string("insert");
    final Parser update = string("update");
    final Parser select = string("select");
    final Parser with = string("with");
    final Parser from = string("from");
    final Parser where = string("where");
    final Parser innerJoin = string("inner join");
    final Parser join = string("join");
    final Parser leftJoin = string("left join");
    final Parser rightJoin = string("right join");
    final Parser on = string("on");
    final Parser groupBy = string("group by");
    final Parser having = string("having");
    final Parser over = string("over");
    final Parser partitionBy = string("partition by");
    final Parser orderBy = string("order by");
    final Parser as = string("as");

    final Parser comparator = string("<")
            .orElse(string(">"))
            .orElse(string("<="))
            .orElse(string(">="))
            .orElse(string("=="))
            .orElse(string("!="))
            .orElse(string("like"))
            .orElse(string("is"));

    final Parser operator = string("+")
            .orElse(string("-"))
            .orElse(string("*"))
            .orElse(string("/"))
            .orElse(string("&"))
            .orElse(string("|"));

    final Parser bool = string("true").orElse(string("false"));

    final Parser variable = lower.combine(
            letter.orElse(digit).orElse(underScore).many()
    ).combine(lower.orElse(digit));

    final Parser open = string("(");
    final Parser close = string(")");

}
