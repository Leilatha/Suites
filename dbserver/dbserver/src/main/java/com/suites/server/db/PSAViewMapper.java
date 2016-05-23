package com.suites.server.db;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.suites.server.api.PSAView;
import com.suites.server.core.User;

public class PSAViewMapper implements ResultSetMapper<PSAView> {
    public PSAView map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        User author = new User(r.getInt("Member.id"),
                               r.getString("Member.email"),
                               r.getString("Member.name"),
                               r.getString("Member.profilePicture"));
        
        return new PSAView(r.getInt("PSA.Id"),
                           author,
                           r.getString("PSA.title"),
                           r.getString("PSA.description"),
                           r.getTimestamp("PSA.timestamp"));
    }
}
