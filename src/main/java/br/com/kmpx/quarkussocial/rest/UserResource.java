package br.com.kmpx.quarkussocial.rest;

import java.util.Set;

import br.com.kmpx.quarkussocial.domain.model.User;
import br.com.kmpx.quarkussocial.domain.repository.UserRepository;
import br.com.kmpx.quarkussocial.rest.dto.CreateUserResquest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
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

	
	private UserRepository repository;
	private Validator validator;
	
	@Inject
	public UserResource(UserRepository repository, Validator validator) {
		this.repository = repository;
		this.validator = validator;
	}
	
	@POST
	@Transactional
	public Response createUser(CreateUserResquest userRequest) {
		
		Set<ConstraintViolation<CreateUserResquest>> violations = validator.validate(userRequest);
		if(!violations.isEmpty()) {
			ConstraintViolation<CreateUserResquest> erro = violations.stream().findAny().get();
			String errorMessage = erro.getMessage();
			return Response.status(400).entity(errorMessage).build();
		}
		
		
		User user = new User();
		user.setAge(userRequest.getAge());
		user.setName(userRequest.getName());
		
		repository.persist(user);
		
		return Response.ok(userRequest).build();
	}
	
	@GET
	public Response listAllUsers() {
		 PanacheQuery<User> query = repository.findAll();
		return Response.ok(query.list()).build();
	}
	
	@DELETE
	@Path("{id}")
	@Transactional
	public Response deleteUser(@PathParam("id") Long id) {
		User user = repository.findById(id);
		if(user != null) {
			repository.delete(user);
			return Response.ok().build();

		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@PUT
	@Path("{id}")
	@Transactional
	public Response updateUser(@PathParam("id") Long id, CreateUserResquest userData) {
		User user = repository.findById(id);
		
		if(user != null) {
			user.setName(userData.getName());
			user.setAge(userData.getAge());
			return Response.ok().build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
}
