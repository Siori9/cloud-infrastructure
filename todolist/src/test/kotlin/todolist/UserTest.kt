package todolist;

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import jakarta.ws.rs.core.MediaType
import org.junit.jupiter.api.Test

@QuarkusTest
class UserTest {

//    @Test
//    fun testNewUser() {
//        RestAssured.given()
//            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//            .formParam("name", "Clara")
//            .formParam("login", "Siori")
//            .formParam("password", "1234")
//            .post("/todos/users")
//            .then()
//            .statusCode(204)
//    }
//
//    @Test
//    fun testGetUserById() {
//        given()
//            .`when`().get("/todos/users/65f454cd7a17df628a315eb8")
//            .then()
//            .statusCode(200)
//            .extract()
//            .body()
//    }

}