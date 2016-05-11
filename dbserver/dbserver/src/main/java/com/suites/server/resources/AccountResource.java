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
import com.suites.server.api.RegistrationResult;

import io.dropwizard.auth.Auth;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
    private final UserManager um;

    public AccountResource(UserManager manager) {
        um = manager;
    }

    @GET
    public User getUserInfo(@Auth User user) {
        return user;
    }

    @POST
    public RegistrationResult registerUser(RegistrationRequest request) {
        try {
            um.registerUser(request.getEmail(), request.getName(), request.getPassword());
            return new RegistrationResult(true, "Ok");
        } catch (UserException e) {
            return new RegistrationResult(false, e.getMessage());
        }
    }
}
