package com.suites.server.resources;

import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class IndexResource {

    public IndexResource() {}

    @GET
    public String hiMessage() {
        return "There's nothing here.";
    }
}
