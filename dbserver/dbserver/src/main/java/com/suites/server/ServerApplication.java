package com.suites.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;

import org.postgresql.Driver;

import com.suites.server.db.*;

public class ServerApplication extends Application<ServerConfiguration> {
    public static void main(String[] args) throws Exception {
        new ServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "dbserver";
    }

    @Override
    public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
    }

    @Override
    public void run(ServerConfiguration config,
                    Environment environment) {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "postgresql");
        final SuitesDAO dao = jdbi.onDemand(SuitesDAO.class);

        dao.createSuiteTable();
        dao.createUserTable();
        dao.createSuiteMembershipTable();
        // dao.createSuiteMembershipIndex(); // Maybe move database creation somewhere else
    }
}
