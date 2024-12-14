package todolist;

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.ws.rs.core.MediaType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.jupiter.api.Test
import todolist.routes.TodoRequest
import todolist.routes.TodoRequest2

@QuarkusTest
class TodoTest {

//    @Test
//    fun testNewTodo() {
//        val totoRequest = TodoRequest("la todo des enfers !")
//        val json = Json.encodeToString(totoRequest)
//        given()
//            .contentType(MediaType.APPLICATION_JSON)
//            .body(json)
//            .post("/users/65f454cd7a17df628a315eb8/todos")
//            .then()
//            .statusCode(200)
//    }
//
//    @Test
//    fun testGetTodoById() {
//        given()
//            .`when`().get("/users/65f454cd7a17df628a315eb8/todos/65f46e952812034f42de9dec")
//            .then()
//            .statusCode(200)
//            .extract()
//            .body()
//    }
//
//    @Test
//    fun testGetTodoByUser() {
//        given()
//            .`when`().get("/users/65f454cd7a17df628a315eb8/todos/")
//            .then()
//            .statusCode(200)
//    }
//
//    @Test
//    fun testDeleteTodoById() {
//        given()
//            .`when`().delete("/users/65f454cd7a17df628a315eb8/todos/65f9dd46e3fbda5354aeb3c7")
//            .then()
//            .statusCode(200)
//    }
//
//    @Test
//    fun testPutDoneTodoById() {
//        val totoRequest = TodoRequest2("la todo des enfers !", true)
//        val json = Json.encodeToString(totoRequest)
//        given()
//            .contentType(MediaType.APPLICATION_JSON)
//            .body(json)
//            .put("/users/65f454cd7a17df628a315eb8/todos/65f9dec0b76e984f28adc963")
//            .then()
//            .statusCode(200)
//    }
}