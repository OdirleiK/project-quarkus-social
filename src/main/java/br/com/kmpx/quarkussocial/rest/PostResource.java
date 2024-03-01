package br.com.kmpx.quarkussocial.rest;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.kmpx.quarkussocial.domain.model.Post;
import br.com.kmpx.quarkussocial.domain.model.User;
import br.com.kmpx.quarkussocial.domain.repository.PostRepository;
import br.com.kmpx.quarkussocial.domain.repository.UserRepository;
import br.com.kmpx.quarkussocial.rest.dto.CreatePostRequest;
import br.com.kmpx.quarkussocial.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

	private UserRepository userRepository;
	private PostRepository repository;
	
	@Inject
	public PostResource(UserRepository userRepository, PostRepository repository) {
		this.userRepository = userRepository;
		this.repository = repository;
	}
 
	@POST
	@Transactional
	public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request) {
		User user = userRepository.findById(userId);
		if(user == null)
				return Response.status(Response.Status.NOT_FOUND).build();
		
		Post post = new Post();
		post.setText(request.getText());
		post.setUser(user);
		
		repository.persist(post);
		
		return Response.status(Response.Status.CREATED).build();
	}
	
	@GET
	public Response listPosts(@PathParam("userId") Long userId) {
		User user = userRepository.findById(userId);
		if(user == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		
		PanacheQuery<Post> query = repository.find("user", Sort.by("dateTime", Direction.Descending), user);
		List<Post> list = query.list();
		
		List<PostResponse> response = list.stream().map(post -> PostResponse.fromEntity(post)).collect(Collectors.toList());
		
		return Response.ok(response).build();
	}
}
