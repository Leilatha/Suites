package com.suites.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.dropwizard.auth.Auth;

import com.suites.server.api.AddSuiteRequest;
import com.suites.server.api.AddSuiteResult;

import com.suites.server.db.SuiteManager;
import com.suites.server.core.User;

@Path("/suite")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SuiteResource {
    private final SuiteManager sm;

    public SuiteResource(SuiteManager manager) {
        sm = manager;
    }

    @POST
    public AddSuiteResult makeSuite(@Auth User user, AddSuiteRequest req) {
        sm.makeSuite(user, req.getName());
        return new AddSuiteResult(true, "All good");
    }
}