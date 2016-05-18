package com.suites.server.db;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.suites.server.core.Chore;

public class ChoreMapper implements ResultSetMapper<Chore> {
    public Chore map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Grocery(r.getInt("Id"),
                        r.getString("Name"),
                        r.getString("Description"));
    }
}
