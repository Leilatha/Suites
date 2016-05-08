package com.suites.server.db;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.suites.server.core.Suite;

public class SuiteMapper implements ResultSetMapper<Suite> {
    public Suite map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Suite(r.getInt("Id"), r.getString("Name"));
    }
}
