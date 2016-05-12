package com.suites.server;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Environment;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;

import org.skife.jdbi.v2.DBI;

import com.codahale.metrics.MetricRegistry;

import com.suites.server.db.SuitesDAO;
import com.suites.server.core.User;

public class UserTest {

    private static SuitesDAO dao;

    private static String johnEmail = "john@john.com";
    private static String johnName = "John";
    private static String johnPass = "123456";


    @BeforeClass
    public static void setup() {
        DataSourceFactory dsFac = new DataSourceFactory();
        dsFac.setDriverClass("org.postgresql.Driver");
        dsFac.setUrl("jdbc:postgresql://localhost/test");
        dsFac.setUser("tester");
        dsFac.setPassword("");
        Environment env = new Environment("test-env", Jackson.newObjectMapper(), null, new MetricRegistry(), null);

        DBI dbi;
        try {
            dbi = new DBIFactory().build(env, dsFac, "test");
            dao = dbi.onDemand(SuitesDAO.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        dao.createSuiteTable();
        dao.createUserTable();
        dao.createSuiteMembershipTable();

        addJohn();
    }

    @Test
    public void couldConnect() {
        assertNotNull(dao);
    }

    private static void addJohn() {        
        dao.addUser(johnEmail, johnName, johnPass);
    }
    
    @Test
    public void getByEmail() {
        User pJohn = dao.getUserByEmail(johnEmail);

        assertNotNull(pJohn);
        assertEquals(johnEmail, pJohn.getEmail());
        assertEquals(johnName, pJohn.getName());
    }

    @Test
    public void login() {
        assertNotNull(dao.authenticateUser(johnEmail, johnPass));
    }
}
