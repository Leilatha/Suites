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

import com.suites.server.api.AddPSARequest;
import com.suites.server.api.PSAListResult;

import com.suites.server.api.GenericResult;

import com.suites.server.db.PSAManager;
import com.suites.server.db.SuiteManager;
import com.suites.server.core.User;
import com.suites.server.api.PSAView;

import java.util.List;

@Path("/psa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PSAResource {
    private final PSAManager pm;
    private final SuiteManager sm;

    public PSAResource(PSAManager pManager, SuiteManager sManager) {
        pm = pManager;
        sm = sManager;
    }

    @GET
    public PSAListResult getSuitePSAs(@Auth User user, @QueryParam("suiteid") IntParam suiteId) {
        if(suiteId == null) {
	    return new PSAListResult(false, "You need to specify a suite.", null);
	} else if (sm.isUserInSuite(user, suiteId.get())) {
	    List<PSAView> list = pm.getSuitePSAs(suiteId.get());
	    return new PSAListResult(true, "", list);
	} else {
	    return new PSAListResult(false, "You are not in that suite.", null);
	}
    }

    @POST
    public GenericResult addPSA(@Auth User user,
                                @QueryParam("suiteid") IntParam suiteId,
                                AddPSARequest apr) {
        if(suiteId == null) {
	    return new GenericResult(false, "You need to specify a suite.");
	} else if(sm.isUserInSuite(user, suiteId.get())) {
	    pm.addPSA(suiteId.get(), user, apr.getTitle(), apr.getDescription());
	    return new GenericResult(true, "");
	} else {
	    return new GenericResult(false, "You are not in that suite.");
        }
    }

    @PUT
    public GenericResult editPSA(@Auth User user,
                                 @QueryParam("psaid") IntParam psaId,
                                 AddPSARequest apr) {
        if(psaId == null) {
	    return new GenericResult(false, "You need to specify a grocery item.");
	} else {
	    boolean check = pm.editPSA(psaId.get(),
                                       user,
                                       apr.getTitle(),
                                       apr.getDescription());
            if(!check)
                return new GenericResult(false, "You are not the author of that item.");
            else
	        return new GenericResult(true, "");
        }
    }

    @DELETE
    public GenericResult deletePSA(@Auth User user,
                                   @QueryParam("psaid") IntParam psaId) {
        if(psaId == null) {
	    return new GenericResult(false, "You need to specify a grocery item.");
	} else {
	    boolean check = pm.deletePSA(psaId.get(), user);
            if(!check)
                return new GenericResult(false, "You are not the author of that item.");
            else
	        return new GenericResult(true, "");
        }
    }
}

