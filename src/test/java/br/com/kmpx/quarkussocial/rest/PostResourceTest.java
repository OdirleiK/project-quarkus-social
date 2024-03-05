package br.com.kmpx.quarkussocial.rest;

import static io.restassured.RestAssured.given;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.kmpx.quarkussocial.domain.model.User;
import br.com.kmpx.quarkussocial.domain.repository.UserRepository;
import br.com.kmpx.quarkussocial.rest.dto.CreatePostRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {
	
	@Inject
	UserRepository userRepository;
	
	Long userId;
	
	@BeforeEach
	@Transactional
	public void setUp() {
		var user = new User();
		user.setAge(30);
		user.setName("teste");
		userRepository.persist(user);
		userId = user.getId();
	}

	@Test
	@DisplayName("should create a post for a user")
	public void createPostTest() {
		CreatePostRequest postRequest = new CreatePostRequest();
		postRequest.setText("Some text");
				
		given()
			.contentType(ContentType.JSON)
			.body(postRequest)
			.pathParam("userId", userId)
		.when()
			.post()
		.then()
			.statusCode(201);
	}
	
	@Test
	@DisplayName("should return 404 when trying to make a post for an inexistent user")
	public void postForAnInexistentUserTest() {
		CreatePostRequest postRequest = new CreatePostRequest();
		postRequest.setText("Some text");
				
		Long inexistentUserId = 999l;
		
		given()
			.contentType(ContentType.JSON)
			.body(postRequest)
			.pathParam("userId", inexistentUserId)
		.when()
			.post()
		.then()
			.statusCode(404);
	}

}
