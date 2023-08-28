package org.quarkus.samples.petclinic.login;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class LoginResourceTest {

    @Test
    public void testLoginTemplateEndpoint() {
        RestAssured.given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML);
    }

    @Test
    @Transactional
    public void testProcessLoginForm_ValidCredentials() {
        String email = "user@example.com";
        String password = "changeme";

        RestAssured.given()
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post("/")
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body(containsString("Welcome"));
    }

    @Test
    @Transactional // Use this if your tests need transactions
    public void testProcessLoginForm_InvalidCredentials() {
        String email = "user@example.com";
        String password = "invalidpassword";

        RestAssured.given()
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post("/")
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body(containsString("Invalid email or password"));
    }
}