package br.com.kmpx.quarkussocial.rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.kmpx.quarkussocial.rest.dto.CreateUserResquest;
import br.com.kmpx.quarkussocial.rest.dto.ResponseError;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

	@TestHTTPResource("/users")
	URL apiURL;
	
	@Test
	@DisplayName("should create an user successfully")
	@Order(1)
	public void createUserTest() {
		CreateUserResquest user = new CreateUserResquest();
		user.setName("teste");
		user.setAge(30);
		
		Response response = given()
				.contentType(ContentType.JSON)
				.body(user)
			.when()
				.post(apiURL)
			.then()
				.extract().response();
		
		assertEquals(201, response.statusCode());
		assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	@DisplayName("should return error when json is not valid")
	@Order(2)
	public void createUserValidationErrorTest() {
		CreateUserResquest user = new CreateUserResquest();
		user.setName(null);
		user.setAge(null);
		
		Response response = given()
				.contentType(ContentType.JSON)
				.body(user)
			.when()
				.post(apiURL)
			.then()
				.extract().response();
		
		assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.statusCode());
		assertEquals("Validation Error", response.jsonPath().getString("message"));
		
		List<Map<String, String>> errors = response.jsonPath().getList("errors");
		assertNotNull(errors.get(0).get("message"));
		assertNotNull(errors.get(1).get("message"));
	}
	
	@Test
	@DisplayName("should list all users")
	@Order(3)
	public void listAllUsersTest() {
	
		 	given()
				.contentType(ContentType.JSON)
			.when()
				.post(apiURL)
			.then()
				.statusCode(200)
				.body("size()", Matchers.is(1));
		 	 	
	}
}
