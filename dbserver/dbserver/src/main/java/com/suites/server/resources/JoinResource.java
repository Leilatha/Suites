package com.suites.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.dropwizard.auth.Auth;

import com.suites.server.api.GenericResult;

import com.suites.server.db.SuiteManager;
import com.suites.server.core.User;
import com.suites.server.core.Suite;

@Path("/join")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JoinResource {
    private final SuiteManager sm;

    public JoinResource(SuiteManager manager) {
        sm = manager;
    }

    @POST
    public GenericResult joinSuite(@Auth User user, int suiteId) {
        if (sm.isUserInvited(user, suiteId)) {
            sm.addUserToSuite(user, suiteId);
            return new GenericResult(true, "All good");
        } else {
            return new GenericResult(false, "You have not been invited to that suite..");
        }
    }
}
