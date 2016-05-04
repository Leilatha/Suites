package com.suites.server.resources;

import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.suites.server.db.UserManager;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
    UserManager um;

    public AccountResource(UserManager manager) {
        um = manager;
    }
}
