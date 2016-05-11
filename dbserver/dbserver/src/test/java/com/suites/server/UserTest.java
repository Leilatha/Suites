package com.suites.server;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Environment;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;

import org.skife.jdbi.v2.DBI;

import com.suites.server.db.SuitesDAO;

public class UserTest {

    private SuitesDAO dao;

    @Before
    public void setup() {
        DataSourceFactory dsFac = new DataSourceFactory();
        dsFac.setDriverClass("org.postgresql.Driver");
        dsFac.setUrl("jdbc:postgresql://localhost/test");
        dsFac.setUser("tester");
        dsFac.setPassword("");
        Environment env = new Environment("test-env", Jackson.newObjectMapper(), null, null, null);

        DBI dbi;
        try {
            dbi = new DBIFactory().build(env, dsFac, "test");
            dao = dbi.onDemand(SuitesDAO.class);
        } catch (Exception e) {}
    }

    @Test
    public void couldConnect() {
        assertNotNull(dao);
    }
    
    @Test
    public void testApp()
    {
        assertTrue( true );
    }
}
