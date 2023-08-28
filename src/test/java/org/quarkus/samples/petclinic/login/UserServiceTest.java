package org.quarkus.samples.petclinic.login;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;

import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class UserServiceTest {

    @Inject
    UserService userService;
    @Test
    public void testGetUserByEmailAndPassword() {
        String email = "user@example.com";

        User user = new User();
        user.email = email;
        user.password = hashPassword(); // Hash the password

        userService.entityManager.persist(user);

        RestAssured.given()
                .when()
                .get("/test") // Replace with the appropriate endpoint
                .then()
                .statusCode(200)
                .body("email", notNullValue()); // Adjust this based on your response format

        // Add more assertions or verifications as needed
    }
    private String hashPassword() {
        return BCrypt.hashpw("changeme", BCrypt.gensalt());
    }

}
