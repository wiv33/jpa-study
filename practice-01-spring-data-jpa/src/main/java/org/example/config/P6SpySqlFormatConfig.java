package org.example.config;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.util.Locale;

/**
 * @author pilseong.ko
 */
public class P6SpySqlFormatConfig implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        sql = formatSql(category, sql);
        return now + " | " + elapsed + "ms | " + category + " | connection " + connectionId + " | "
               + P6Util.singleLine(prepared) + sql;
    }

    private String formatSql(String category, String sql) {
        if (sql == null || sql.trim().equals("")) {
            return sql;
        }

        // Only format Statement, distinguish DDL And DML
        if (!Category.STATEMENT.getName().equals(category)) {
            return sql;
        }

        var result = "|\nsql(P6Spy sql,Hibernate format):%s";

        if (isDDL(sql)) {
            return String.format(result, FormatStyle.DDL.getFormatter().format(sql));
        }

        return String.format(result, FormatStyle.BASIC.getFormatter().format(sql));
    }

    private boolean isDDL(String sql) {
        String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
        return tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment");
    }
}
