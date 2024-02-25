package br.com.kmpx.quarkussocial.rest;

import br.com.kmpx.quarkussocial.rest.dto.CreateUserResquest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

	@POST
	public Response createUser(CreateUserResquest userRequest) {
		return Response.ok().build();
	}
}
