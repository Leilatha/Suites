package com.suites.server.resources;

import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.suites.server.db.UserManager;
import com.suites.server.db.UserException;
import com.suites.server.core.User;
import com.suites.server.api.RegistrationRequest;
import com.suites.server.api.GenericResult;

import io.dropwizard.auth.Auth;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
    private final UserManager um;

    public AccountResource(UserManager manager) {
        um = manager;
    }

    @GET // Change to return a User once it is JSON-encodable.
    public User getUserInfo(@Auth User user) {
        return user;
    }

    @POST
    public GenericResult registerUser(RegistrationRequest request) {
        try {
            um.registerUser(request.getEmail(), request.getName(), request.getPassword());
            return new GenericResult(true, "Ok");
        } catch (UserException e) {
            return new GenericResult(false, e.getMessage());
        }
    }
}
