package br.com.kmpx.quarkussocial.rest;

import br.com.kmpx.quarkussocial.domain.model.User;
import br.com.kmpx.quarkussocial.rest.dto.CreateUserResquest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	@POST
	@Transactional
	public Response createUser(CreateUserResquest userRequest) {
		
		User user = new User();
		user.setAge(userRequest.getAge());
		user.setName(userRequest.getName());
		
		user.persist();
		
		return Response.ok(userRequest).build();
	}
	
	@GET
	public Response listAllUsers() {
		 PanacheQuery<User> query = User.findAll();
		return Response.ok(query.list()).build();
	}
	
	@DELETE
	@Path("{id}")
	@Transactional
	public Response deleteUser(@PathParam("id") Long id) {
		User user = User.findById(id);
		if(user != null) {
			user.delete();
			return Response.ok().build();

		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@PUT
	@Path("{id}")
	@Transactional
	public Response updateUser(@PathParam("id") Long id, CreateUserResquest userData) {
		User user = User.findById(id);
		
		if(user != null) {
			user.setName(userData.getName());
			user.setAge(userData.getAge());
			return Response.ok().build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
}
