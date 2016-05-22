package com.suites.server.db;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.suites.server.core.PSA;

public class PSAMapper implements ResultSetMapper<PSA> {
    public PSA map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new PSA(r.getInt("Id"),
                       r.getInt("suiteId"),
                       r.getInt("authorId"),
                       r.getString("title"),
                       r.getString("description"),
                       r.getTimestamp("timestamp"));
    }
}
