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
import com.suites.server.api.GroceryListResult;

import com.suites.server.api.GenericResult;

import com.suites.server.db.GroceryManager;
import com.suites.server.db.SuiteManager;
import com.suites.server.core.User;
import com.suites.server.core.Grocery;

import java.util.List;

@Path("/grocery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroceryResource {
    private final GroceryManager gm;
    private final SuiteManager sm;

    public GroceryResource(GroceryManager gManager, SuiteManager sManager) {
        gm = gManager;
        sm = sManager;
    }

    @GET
    public GroceryListResult getSuiteGroceries(@Auth User user, @QueryParam("suiteid") IntParam suiteId) {
        if(suiteId == null) {
	    return new GroceryListResult(false, "You need to specify a suite.", null);
	} else if(sm.isUserInSuite(user, suiteId.get())) {
	    List<Grocery> list = gm.getSuiteGroceries(suiteId.get());
	    return new GroceryListResult(true, "", list);
	} else {
	    return new GroceryListResult(false, "You are not in that suite.", null);
	}
    }

    @POST
    public GenericResult addGrocery(@Auth User user,
                                    @QueryParam("suiteid") IntParam suiteId,
                                    AddGroceryRequest request) {
        if(suiteId == null) {
	    return new GenericResult(false, "You need to specify a suite.");
	} else if(sm.isUserInSuite(user, suiteId.get())) {
	    gm.addGrocery(suiteId.get(), request.getName(), request.getPrice(), request.getQuantity());
	    return new GenericResult(true, "");
	} else {
	    return new GenericResult(false, "You are not in that suite.");
        }
    }

    @PUT
    public GenericResult editGrocery(@Auth User user,
                                     @QueryParam("groceryid") IntParam groceryId,
                                    AddGroceryRequest request) {
        if(groceryId == null) {
	    return new GenericResult(false, "You need to specify a grocery item.");
	} else {
	    boolean check = gm.editGrocery(groceryId.get(),
                                           user,
                                           request.getName(),
                                           request.getPrice(),
                                           request.getQuantity());
            if(!check)
                return new GenericResult(false, "Could not edit grocery item.");
            else
	        return new GenericResult(true, "");
        }
    }

    @DELETE
    public GenericResult deleteGrocery(@Auth User user,
                                       @QueryParam("groceryid") IntParam groceryId) {
        if(groceryId == null) {
	    return new GenericResult(false, "You need to specify a grocery item.");
	} else {
	    boolean check = gm.deleteGrocery(groceryId.get(), user);
            if(!check)
                return new GenericResult(false, "Could not delete grocery item.");
            else
	        return new GenericResult(true, "");
        }
    }
}

