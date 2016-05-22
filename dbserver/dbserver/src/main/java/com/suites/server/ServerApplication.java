package com.suites.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.auth.basic.BasicAuthProvider;

import org.skife.jdbi.v2.DBI;

import org.postgresql.Driver;

import com.suites.server.db.*;
import com.suites.server.api.*;
import com.suites.server.core.*;
import com.suites.server.resources.*;

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
                    Environment environment) throws ClassNotFoundException {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "postgresql");
        final SuitesDAO dao = jdbi.onDemand(SuitesDAO.class);
        final UserManager um = new UserManager(dao);
        final SuiteManager sm = new SuiteManager(dao);
        final GroceryManager gm = new GroceryManager(dao);

        dao.createSuiteTable();
        dao.createUserTable();
        dao.createSuiteMembershipTable();
        dao.createSuiteInvitationTable();
        dao.createGroceryTable();
        // dao.createSuiteMembershipIndex(); // Maybe move database creation somewhere else

        environment.jersey().register(new BasicAuthProvider<User>(new DBAuthenticator(um),
                                                                  "User Authenticator"));
        environment.jersey().register(new IndexResource());

        final AccountResource accRes = new AccountResource(um);
        environment.jersey().register(accRes);

        final SuiteResource suiteRes = new SuiteResource(sm);
        environment.jersey().register(suiteRes);

        final InviteResource invRes = new InviteResource(sm);
        environment.jersey().register(invRes);

        final JoinResource joinRes = new JoinResource(sm);
        environment.jersey().register(joinRes);

	final SuiteUserResource userListRes = new SuiteUserResource(sm);
	environment.jersey().register(userListRes);

        final GroceryResource groceryRes = new GroceryResource(gm, sm);
        environment.jersey().register(groceryRes);
    }
}
