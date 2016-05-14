package com.suites.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.dropwizard.auth.Auth;

import com.suites.server.api.Invitation;
import com.suites.server.api.GenericResult;

import com.suites.server.db.SuiteManager;
import com.suites.server.core.User;
import com.suites.server.core.Suite;

import java.util.List;

@Path("/invite")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InviteResource {
    private final SuiteManager sm;

    public InviteResource(SuiteManager manager) {
        sm = manager;
    }

    @GET
    public List<Suite> getUserInvites(@Auth User user) {
        return sm.getUserInvites(user);
    }

    @POST
    public GenericResult inviteUser(@Auth User user, Invitation invite) {
        int suiteId = invite.getSuiteId();
        
        if (sm.isUserInSuite(user, suiteId)) {
            sm.inviteUser(invite.getInvitee(), suiteId);
            return new GenericResult(true, "Happy Invitations");
        } else {
            return new GenericResult(false, "You are not in that suite.");
        }
    }
}
