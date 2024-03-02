package br.com.kmpx.quarkussocial.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.kmpx.quarkussocial.rest.dto.CreateUserResquest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@QuarkusTest
class UserResourceTest {

	
	@Test
	@DisplayName("should create an user successfully")
	public void createUserTest() {
		CreateUserResquest user = new CreateUserResquest();
		user.setName("teste");
		user.setAge(30);
		
		Response response = given()
				.contentType(ContentType.JSON)
				.body(user)
			.when()
				.post("/users")
			.then()
				.extract().response();
		
		assertEquals(201, response.statusCode());
		assertNotNull(response.jsonPath().getString("id"));
	}

}
