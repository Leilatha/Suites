package com.suites.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.params.IntParam;

import com.suites.server.api.AddGroceryRequest;
import com.suites.server.api.ChoreListResult;
import com.suites.server.api.ChoreView;
import com.suites.server.api.GenericResult;
import com.suites.server.api.ChoreListResult;
import com.suites.server.api.AddChoreRequest;

import com.suites.server.db.ChoreManager;
import com.suites.server.db.SuiteManager;
import com.suites.server.core.User;

import java.util.List;

@Path("/chore")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChoreResource {
    private final ChoreManager cm;
    private final SuiteManager sm;

    public ChoreResource(ChoreManager cManager, SuiteManager sManager) {
        cm = cManager;
        sm = sManager;
    }

    @GET
    public ChoreListResult getSuiteChores(@Auth User user, @QueryParam("suiteid") IntParam suiteId) {
        if (suiteId == null) {
            return new ChoreListResult(false, "You need to specify a suite.", null);
        } else if (sm.isUserInSuite(user, suiteId.get())) {
            List<ChoreView> list = cm.getSuiteChores(suiteId.get());
            return new ChoreListResult(true, "", list);
        } else {
            return new ChoreListResult(false, "You are not in that suite.", null);
        }
    }

    @POST
    public GenericResult addChore(@Auth User user,
                                    @QueryParam("suiteid") IntParam suiteId,
                                    AddChoreRequest acr) {
        if (suiteId == null) {
            return new GenericResult(false, "You need to specify a suite.");
        } else if (sm.isUserInSuite(user, suiteId.get())) {
            cm.addChore(suiteId.get(), acr.getName(), acr.getDescription(), acr.getAssignees());
            return new GenericResult(true, "");
        } else {
            return new GenericResult(false, "You are not in that suite.");
        }
    }

    @PUT
    public GenericResult editChore(@Auth User user,
                                   @QueryParam("choreid") IntParam choreId,
                                   AddChoreRequest acr) {
        if(choreId == null) {
            return new GenericResult(false, "You need to specify a chore.");
        } else {
            boolean check = cm.editChore(choreId.get(),
                                         user,
                                         acr.getName(),
                                         acr.getDescription(),
                                         acr.getAssignees());
            if(!check)
                return new GenericResult(false, "Could not edit a chore.");
            else
                return new GenericResult(true, "");
        }
    }

    @DELETE
    public GenericResult deleteChore(@Auth User user,
                                     @QueryParam("choreid") IntParam choreId) {
        if(choreId == null) {
            return new GenericResult(false, "You need to specify a chore.");
        } else {
            boolean check = cm.deleteChore(choreId.get(), user);
            if(!check)
                return new GenericResult(false, "Could not delete a chore.");
            else
                return new GenericResult(true, "");
        }
    }
}
