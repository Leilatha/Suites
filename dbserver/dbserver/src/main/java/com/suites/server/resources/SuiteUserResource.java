package com.suites.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.params.IntParam;

import com.suites.server.api.UserListResult;

import com.suites.server.db.SuiteManager;
import com.suites.server.core.User;
import com.suites.server.core.Suite;

import java.util.List;

@Path("/suite/userlist")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SuiteUserResource {
    private final SuiteManager sm;

    public SuiteUserResource(SuiteManager manager) {
        sm = manager;
    }

    @GET
    public UserListResult getUsersInSuite(@Auth User user, @QueryParam("suiteid") IntParam suiteId) {
	if(suiteId == null) {
	    return new UserListResult(false, "You need to specify a suite.", null);
	} else if(sm.isUserInSuite(user, suiteId.get())) {
	    List<User> list = sm.getSuiteUsers(suiteId.get());
	    return new UserListResult(true, "", list);
	} else {
	    return new UserListResult(false, "You are not in that suite.", null);
	}
    }
}
