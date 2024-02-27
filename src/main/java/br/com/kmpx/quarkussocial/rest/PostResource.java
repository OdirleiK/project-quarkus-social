package br.com.kmpx.quarkussocial.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

 
	@POST
	public Response savePost() {
		return Response.status(Response.Status.CREATED).build();
	}
	
	@GET
	public Response listPosts() {
		return Response.ok().build();
	}
}
