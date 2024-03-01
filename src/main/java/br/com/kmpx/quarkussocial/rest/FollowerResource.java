package br.com.kmpx.quarkussocial.rest;

import br.com.kmpx.quarkussocial.domain.model.Follower;
import br.com.kmpx.quarkussocial.domain.model.User;
import br.com.kmpx.quarkussocial.domain.repository.FollowerRepository;
import br.com.kmpx.quarkussocial.domain.repository.UserRepository;
import br.com.kmpx.quarkussocial.rest.dto.FollowerRequest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

	private UserRepository userRepository;
	private FollowerRepository repository;

	@Inject
	public FollowerResource(FollowerRepository repository, UserRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
	
	}
	
	@PUT
	@Transactional
	public Response followUser(@PathParam("userId") Long userId, FollowerRequest request) {
		
		if(userId.equals(request.getFollowerId())) 
			return Response.status(Response.Status.CONFLICT).entity("You can't folllow yourself").build();
		
		User user = userRepository.findById(userId);
		
		if(user == null) 
			return Response.status(Response.Status.NOT_FOUND).build();
		
		User follower = userRepository.findById(request.getFollowerId());
		
		boolean follows = repository.follows(follower, user);
		
		if(!follows) {
			Follower entity = new Follower();
			entity.setUser(user);
			entity.setFollower(follower);
			
			repository.persist(entity);		
		}
		
		
		return Response.status(Response.Status.NO_CONTENT).build();
		
	}

}